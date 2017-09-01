package com.elisa.pretoapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Locale;

import infrastructure.AppCommon;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Locale locale = new Locale(AppCommon.getInstance(this).getSelectedLanguage());
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        Thread t = new Thread() {
            public void run() {
                try {
                    sleep(3000);
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
