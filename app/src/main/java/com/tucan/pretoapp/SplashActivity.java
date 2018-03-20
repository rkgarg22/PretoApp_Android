package com.tucan.pretoapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.Locale;

import CustomControl.GPSTracker;
import infrastructure.AppCommon;

public class SplashActivity extends GenricActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppCommon.getInstance(this).setUserLatitude(0.0f);
        AppCommon.getInstance(this).setUserLongitude(0.0f);
        GPSTracker gpsTracker = new GPSTracker(this);
        Locale locale = new Locale(AppCommon.getInstance(this).getSelectedLanguage());
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

//        byte[] sha1 = {
//                (byte) 0xCE, (byte)0xF6, (byte)0xC1, 0x14, 0x1A, 0x30, 0x0C, (byte) 0xFB, 0x09, 0x79, (byte)0x69,
//                (byte)0xF4, (byte)0xFE, 0x2E, (byte) 0xA9, (byte)0x61, (byte)0x63, (byte)0xD2, (byte)0xC5, 0x62,
//        };
//        Log.e("keyhash", Base64.encodeToString(sha1, Base64.NO_WRAP));

        Thread t = new Thread() {
            public void run() {
                try {
                    sleep(4000);
                    if (AppCommon.getInstance(SplashActivity.this).isUserLogIn()) {
                        Intent i = new Intent(SplashActivity.this, HomeActivity.class);
                        startActivity(i);
                    } else {
                        Intent i = new Intent(SplashActivity.this, LanguageSelectActivity.class);
                        i.putExtra("isComingFromSetting", false);
                        startActivity(i);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    finish();
                }
            }
        };
        t.start();
    }
}
