package com.tucan.pretoapp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import CustomControl.GPSTracker;
import CustomControl.LatoBoldEditText;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import infrastructure.AppCommon;

public class HomeActivity extends GenricActivity {

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
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ButterKnife.bind(this);

        bannerImageView = (SimpleDraweeView) findViewById(R.id.bannerImage);
        gpsTracker = new GPSTracker(this);
        if (!gpsTracker.canGetLocation()) {
            gpsTracker.showSettingsAlert();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        showBanner();
    }

    @OnClick(R.id.searchBtnClick)
    public void searchBtnClick(View view) {
        searchEditText.setText("");
        searchlayout.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.searchLayoutBackgroundBtn)
    public void searchLayoutBackgroundBtn(View view) {
        searchlayout.setVisibility(View.GONE);
    }

    @OnClick(R.id.verticalDotsBtn)
    public void verticalDotsBtnClick(View view) {
        Intent settingIntent = new Intent(this, SettingActivity.class);
        startActivityForResult(settingIntent,AppCommon.SETTING_INTENT);
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
        searchlayout.setVisibility(View.GONE);
        Intent resturantIntent = new Intent(this, ResturantListByCategoryActivity.class);
        resturantIntent.putExtra("categorySelect", 0);
        resturantIntent.putExtra("searchText", searchText);
        resturantIntent.putExtra("addressText", "");
        startActivityForResult(resturantIntent,AppCommon.RESTURANT_LIST_INTENT_FROM_HOME_FOR_SEARCH);
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

    @OnClick(R.id.bannerImage)
    public void bannerImageClick(View v) {
        if(v.getTag()!= null) {
            Intent webViewIntent = new Intent(this, WebViewActivity.class);
            webViewIntent.putExtra("url", v.getTag().toString());
            startActivity(webViewIntent);
        }
//        else{
//            Intent webViewIntent = new Intent(this, WebViewActivity.class);
//            webViewIntent.putExtra("url", getResources().getString(R.string.jungle_box_link));
//            startActivity(webViewIntent);
//        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==AppCommon.RESTURANT_LIST_INTENT_FROM_HOME_FOR_SEARCH){
            if(resultCode== Activity.RESULT_OK){
                searchlayout.setVisibility(View.VISIBLE);
            }
        }else if(requestCode == AppCommon.SETTING_INTENT){
            if(resultCode== Activity.RESULT_OK){
                this.finish();
            }
        }
    }
}
