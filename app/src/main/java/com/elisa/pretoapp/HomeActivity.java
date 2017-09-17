package com.elisa.pretoapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.RelativeLayout;
import android.widget.TextView;

import CustomControl.GPSTracker;
import CustomControl.LatoBoldEditText;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import infrastructure.AppCommon;

public class HomeActivity extends AppCompatActivity {

    @Bind(R.id.searchLayout)
    RelativeLayout searchlayout;

    @Bind(R.id.searchEditText)
    LatoBoldEditText searchEditText;

    @Bind(R.id.addressEditText)
    LatoBoldEditText addressEditText;

    GPSTracker gpsTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);

        gpsTracker = new GPSTracker(this);
        if (!gpsTracker.canGetLocation()) {
            gpsTracker.showSettingsAlert();
        }

    }

    @OnClick(R.id.searchBtnClick)
    public void searchBtnClick(View view) {
        searchlayout.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.searchLayoutBackgroundBtn)
    public void searchLayoutBackgroundBtn(View view) {
        searchlayout.setVisibility(View.GONE);
    }

    @OnClick(R.id.verticalDotsBtn)
    public void verticalDotsBtnClick(View view) {
        Intent settingIntent = new Intent(this, SettingActivity.class);
        startActivity(settingIntent);
    }

    @OnClick(R.id.favouritesBtnClick)
    public void favourirtesBtnClick(View view) {
        Intent favouritesIntent = new Intent(this, FavouriteListActivity.class);
        startActivity(favouritesIntent);
    }

    @OnClick({R.id.breakfastCategory, R.id.lunchCategory, R.id.dinnerCategory, R.id.preorderCategory, R.id.snacksCategory, R.id.otherCategory, R.id.nearYouCategory})
    public void categoryClick(View view) {
        int tag = Integer.parseInt(view.getTag().toString());
        Intent resturantIntent = new Intent(this, ResturantListByCategoryActivity.class);
        resturantIntent.putExtra("categorySelect", tag);
        resturantIntent.putExtra("searchText", "");
        resturantIntent.putExtra("addressText", "");
        startActivity(resturantIntent);
    }

    @OnClick(R.id.filterBtnClick)
    public void filterBtnClick(View view) {
        Intent filterIntent = new Intent(this, FilterActivity.class);
        filterIntent.putExtra("isComingFromHome", true);
        startActivity(filterIntent);
    }

    @OnClick(R.id.searchActionPerformed)
    public void searchActionPerformed(View view) {
        performSearch();
    }

    public void performSearch() {
        String searchText = searchEditText.getText().toString().trim();
        if (searchText.equals("")) {
            searchEditText.setError(getResources().getString(R.string.search_text_enter));
            return;
        }
        searchEditText.setText("");
        searchlayout.setVisibility(View.GONE);
        Intent resturantIntent = new Intent(this, ResturantListByCategoryActivity.class);
        resturantIntent.putExtra("categorySelect", 0);
        resturantIntent.putExtra("searchText", searchText);
        resturantIntent.putExtra("addressText", "");
        startActivity(resturantIntent);
    }

    public void performAddressSearch() {
        String addressText = addressEditText.getText().toString().trim();
        if (addressText.equals("")) {
            addressEditText.setError(getResources().getString(R.string.search_text_enter));
            return;
        }
        addressEditText.setText("");
        Intent resturantIntent = new Intent(this, ResturantListByCategoryActivity.class);
        resturantIntent.putExtra("categorySelect", 0);
        resturantIntent.putExtra("searchText", "");
        resturantIntent.putExtra("addressText", addressText);
        startActivity(resturantIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case AppCommon.LOCATION_PERMISSION_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Log.i("locationPermission", "Result OK");
                    gpsTracker = new GPSTracker(this);
                }
        }

    }

    @OnClick(R.id.adLayout)
    public void adLayoutClick(View view) {
        Intent webViewIntent = new Intent(this, WebViewActivity.class);
        webViewIntent.putExtra("url", getResources().getString(R.string.jungle_box_link));
        startActivity(webViewIntent);
    }

    @OnEditorAction(R.id.searchEditText)
    public boolean onSearchClick(TextView textView, int i, KeyEvent keyEvent) {
        if (i == EditorInfo.IME_ACTION_SEARCH) {
            performSearch();
            return true;
        }
        return false;
    }

    @OnEditorAction(R.id.addressEditText)
    public boolean onAddressClick(TextView textView, int i, KeyEvent keyEvent) {
        if (i == EditorInfo.IME_ACTION_DONE) {
            performAddressSearch();
            return true;
        }
        return false;
    }

}
