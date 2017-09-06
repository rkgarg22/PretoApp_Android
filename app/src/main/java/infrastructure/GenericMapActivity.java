package infrastructure;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;

import com.elisa.pretoapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import APIResponse.ResturantObject;


public class GenericMapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    public GoogleMap mMap;
    public LatLng currentUserLatLon;
    public Map<Marker, String> markersOrderNumbers = new HashMap<>();
    public ArrayList<ResturantObject> resturantObjectArrayList = new ArrayList<ResturantObject>();
    public boolean isMapActive = false;

    public RelativeLayout markerClickLayout;
    public int selectedIndex = 0;

    public void setMapView() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.clear();
        mMap.setOnInfoWindowClickListener(this);
        markersOrderNumbers.clear();
        if (AppCommon.getInstance(this).getUserLatitude() != 0.0f && AppCommon.getInstance(this).getUserLongitude() != 0.0f) {
            BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.person_icon);
            String currentLat = String.valueOf(AppCommon.getInstance(this).getUserLatitude());
            String curretnLon = String.valueOf(AppCommon.getInstance(this).getUserLongitude());
            currentUserLatLon = new LatLng(Double.parseDouble(currentLat), Double.parseDouble(curretnLon));
            Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(currentUserLatLon)
                    .title("")
                    .icon(icon)
                    .alpha(1.0f));
        }
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.small_food_icon);
        if (resturantObjectArrayList.size() > 0) {
            for (int i = 0; i < resturantObjectArrayList.size(); i++) {
                ResturantObject resturantObject = resturantObjectArrayList.get(i);
                LatLng markerLat = new LatLng(Double.parseDouble(resturantObject.getLattitude()), Double.parseDouble(resturantObject.getLongitude()));
                Marker marker = mMap.addMarker(new MarkerOptions()
                        .position(markerLat)
                        .title(resturantObject.getRestName() + "\n" + resturantObject.getAddress())
                        .icon(icon)
                        .alpha(1.0f));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(markerLat, 10.0f));
                markersOrderNumbers.put(marker, Integer.toString(i));
            }
        }
    }

    public void googleMapClick() {
        ResturantObject object = resturantObjectArrayList.get(selectedIndex);
        String url = "http://maps.google.com/maps?daddr=" + object.getLattitude() + "," + object.getLongitude();
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse(url));
        startActivity(intent);
    }

    public void wazeClick() {
        ResturantObject object = resturantObjectArrayList.get(selectedIndex);
        String uri = "geo:" + object.getLattitude() + "," + object.getLongitude();
        //String uri = "waze://?ll=" + object.getLattitude() + "," + object.getLongitude() + "&navigate=yes";
        startActivity(new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse(uri)));
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        markerClickLayout.setVisibility(View.VISIBLE);
        selectedIndex = Integer.parseInt(markersOrderNumbers.get(marker));
    }
}



