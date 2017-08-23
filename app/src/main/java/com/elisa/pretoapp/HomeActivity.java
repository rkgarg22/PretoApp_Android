package com.elisa.pretoapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import CustomControl.GPSTracker;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import infrastructure.AppCommon;

public class HomeActivity extends AppCompatActivity {

    @Bind(R.id.searchLayout)
    RelativeLayout searchlayout;
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

        int tag= Integer.parseInt(view.getTag().toString());
        Intent resturantIntent = new Intent(this, ResturantListByCategoryActivity.class);
        resturantIntent.putExtra("categorySelect",tag);
        startActivity(resturantIntent);
    }

    @OnClick(R.id.filterBtnClick)
    public void filterBtnClick(View view){
        Intent filterIntent = new Intent(this,FilterActivity.class);
        startActivity(filterIntent);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case AppCommon.LOCATION_PERMISSION_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                        grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Log.i("locationPermission", "Result OK");
                    gpsTracker = new GPSTracker( this);
                }
        }

    }
}
