package com.tucan.pretoapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import APIResponse.ResturantObject;
import CustomControl.GPSTracker;
import CustomControl.LatoBoldTextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import infrastructure.AppCommon;
import infrastructure.DirectionsJSONParser;

public class MapActivityForDistance extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {


    @Bind(R.id.headerTextView)
    LatoBoldTextView headerTextView;

    @Bind(R.id.markerClickLayout)
    RelativeLayout markerClickLayout;

    private GoogleMap mMap;
    LatLng markerLat;

    ResturantObject resturantObject;
    String currentLat, curretnLon;
    LatLng currentUserLatLon;
    GPSTracker gpsTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_for_distance);

        Locale locale = new Locale(AppCommon.getInstance(this).getSelectedLanguage());
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        ButterKnife.bind(this);
        gpsTracker = new GPSTracker(this);
        if (!gpsTracker.canGetLocation()) {
            gpsTracker.showSettingsAlert();
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Gson gson = new Gson();
        resturantObject = gson.fromJson(getIntent().getExtras().getString("object"), ResturantObject.class);
        headerTextView.setText(resturantObject.getRestName());
    }

    @OnClick(R.id.backButtonClick)
    public void backButtonClick(View view) {
        this.finish();
    }

    @OnClick(R.id.homeBtnClick)
    public void homeBtnClick(View view) {
        Intent i = new Intent();
        setResult(RESULT_OK, i);
        this.finish();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setOnInfoWindowClickListener(this);
        // Add a marker in Sydney and move the camera
        if (AppCommon.getInstance(this).getUserLatitude() != 0.0f && AppCommon.getInstance(this).getUserLongitude() != 0.0f) {
            BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.person_icon);
            currentLat = String.valueOf(AppCommon.getInstance(this).getUserLatitude());
            curretnLon = String.valueOf(AppCommon.getInstance(this).getUserLongitude());
            currentUserLatLon = new LatLng(Double.parseDouble(currentLat), Double.parseDouble(curretnLon));
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(currentUserLatLon)
                    .title("")
                    .icon(icon)
                    .alpha(1.0f));
        } else {

        }
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.small_food_icon);

        markerLat = new LatLng(Double.parseDouble(resturantObject.getLattitude()), Double.parseDouble(resturantObject.getLongitude()));
        Marker marker = mMap.addMarker(new MarkerOptions()
                .position(markerLat)
                .title(resturantObject.getRestName() + "," + resturantObject.getAddress())
                .icon(icon)
                .alpha(1.0f));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(markerLat, 12.0f));

        if (currentUserLatLon != null && markerLat != null) {
            String url = getDirectionsUrl(currentUserLatLon, markerLat);
            DownloadTask downloadTask = new DownloadTask();
            downloadTask.execute(url);
        }
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        markerClickLayout.setVisibility(View.VISIBLE);
    }

    public void googleMapClick() {

        String url = "http://maps.google.com/maps?daddr=" + resturantObject.getLattitude() + "," + resturantObject.getLongitude();
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse(url));
        startActivity(intent);
    }

    public void wazeClick() {
        String uri = "geo:" + resturantObject.getLattitude() + "," + resturantObject.getLongitude();
        //String uri = "waze://?ll=" + object.getLattitude() + "," + object.getLongitude() + "&navigate=yes";
        startActivity(new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse(uri)));
    }


    private class DownloadTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... url) {

            String data = "";

            try {
                data = downloadUrl(url[0]);
            } catch (Exception e) {
                Log.d("Background Task", e.toString());
            }
            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            ParserTask parserTask = new ParserTask();
            parserTask.execute(result);

        }
    }


    /**
     * A class to parse the Google Places in JSON format
     */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String, String>>>> {

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try {
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                routes = parser.parse(jObject);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return routes;
        }

        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            if (result != null && result.size() > 0) {
                ArrayList points = null;
                PolylineOptions lineOptions = null;
                MarkerOptions markerOptions = new MarkerOptions();
                for (int i = 0; i < result.size(); i++) {
                    points = new ArrayList();
                    lineOptions = new PolylineOptions();

                    List<HashMap<String, String>> path = result.get(i);

                    for (int j = 0; j < path.size(); j++) {
                        HashMap<String, String> point = path.get(j);

                        double lat = Double.parseDouble(point.get("lat"));
                        double lng = Double.parseDouble(point.get("lng"));
                        LatLng position = new LatLng(lat, lng);

                        points.add(position);
                    }

                    lineOptions.addAll(points);
                    lineOptions.width(8);
                    lineOptions.color(getResources().getColor(R.color.color_blue));
                    lineOptions.geodesic(true);
                }


                Polyline polyline = mMap.addPolyline(lineOptions);
                List<PatternItem> pattern = Arrays.<PatternItem>asList(
                        new Dash(30), new Gap(20));
                polyline.setPattern(pattern);
            }
        }
    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {

        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;

        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;

        // Sensor enabled
        String sensor = "sensor=false";
        String mode = "mode=driving";
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + sensor + "&" + mode;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters;


        return url;
    }

    /**
     * A method to download json data from url
     */
    private String downloadUrl(String strUrl) throws IOException {
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(strUrl);

            urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();

            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        } catch (Exception e) {
            Log.d("Exception", e.toString());
        } finally {
            iStream.close();
            urlConnection.disconnect();
        }
        return data;
    }

    @OnClick(R.id.googleMapClick)
    public void googleMapClick(View view) {
        googleMapClick();
    }

    @OnClick(R.id.wazeClick)
    public void wazeClick(View view) {
        wazeClick();
    }

    @OnClick(R.id.cancelButton)
    public void cancelButtonClick(View view) {
        markerClickLayout.setVisibility(View.GONE);
    }
}
