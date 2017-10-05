package com.tucan.pretoapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.Locale;
import java.util.Random;

import API.PretoAppService;
import API.ServiceGenerator;
import APIEntity.GetResturantListEntity;
import APIResponse.BannerResponse;
import APIResponse.GetResturantListResponse;
import APIResponse.ResturantObject;
import butterknife.Bind;
import infrastructure.AppCommon;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GenricActivity extends AppCompatActivity {

    public SimpleDraweeView bannerImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Locale locale = new Locale(AppCommon.getInstance(this).getSelectedLanguage());
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        if (AppCommon.bannerObjectsArrayList.size() == 0) {
            getBannerList();
        }
    }

    public void getBannerList() {
        if (AppCommon.getInstance(this).isConnectingToInternet(this)) {
            PretoAppService pretoAppService = ServiceGenerator.createService(PretoAppService.class);
            Call call = pretoAppService.fetchBanner();
            call.enqueue(new Callback<BannerResponse>() {
                @Override
                public void onResponse(Call call, Response response) {
                    BannerResponse listResponse = (BannerResponse) response.body();
                    if (listResponse.getSuccess().equals("1")) {
                        AppCommon.bannerObjectsArrayList = listResponse.getBannerObjectsArrayList();
                        showBanner();
                    }
                }
                @Override
                public void onFailure(Call call, Throwable t) {

                }
            });
        }
    }

    public void showBanner() {
        if (bannerImageView != null && AppCommon.bannerObjectsArrayList.size()>0) {
            Random r = new Random();
            int randomNumber = r.nextInt(AppCommon.bannerObjectsArrayList.size());
            bannerImageView.setTag(AppCommon.bannerObjectsArrayList.get(randomNumber).getUrl());
            bannerImageView.setImageURI(Uri.parse(AppCommon.bannerObjectsArrayList.get(randomNumber).getImgUrl()));
        }
    }
}
