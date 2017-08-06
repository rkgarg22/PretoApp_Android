package com.elisa.pretoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import infrastructure.AppCommon;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Thread t = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                    if (AppCommon.getInstance(SplashActivity.this).isUserLogIn()) {


                    } else {
                        //Intent i = new Intent(SplashActivity.this, NewLoginSignupActivity.class);
                        //startActivity(i);
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
