package com.tucan.pretoapp;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.facebook.FacebookSdk;
import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by imark on 12/9/2016.
 */

public class PretoApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        FacebookSdk.sdkInitialize(this);
        Fresco.initialize(PretoApplication.this);
    }
}
