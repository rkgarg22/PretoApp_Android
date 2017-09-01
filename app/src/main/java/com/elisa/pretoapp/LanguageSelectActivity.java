package com.elisa.pretoapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.OnClick;
import infrastructure.AppCommon;

public class LanguageSelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_select);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.spanishLanguageBtn)
    public void spanishBtnClick(View view) {
        String languageToLoad = "es"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        AppCommon.getInstance(this).setLanguage(languageToLoad);

        if (!getIntent().getExtras().getBoolean("isComingFromSetting")) {
            Intent loginOptionScreen = new Intent(this, LoginOptionActivity.class);
            startActivity(loginOptionScreen);
        }else{
            Intent a = new Intent(this,HomeActivity.class);
            a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(a);
        }
        this.finish();
    }

    @OnClick(R.id.englishLanguageBtn)
    public void englighBtnClick(View view) {

        String languageToLoad = "en"; // your language
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        AppCommon.getInstance(this).setLanguage(languageToLoad);
        if (!getIntent().getExtras().getBoolean("isComingFromSetting")) {
            Intent loginOptionScreen = new Intent(this, LoginOptionActivity.class);
            startActivity(loginOptionScreen);
        }else{
            Intent a = new Intent(this,HomeActivity.class);
            a.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(a);
        }
        this.finish();
    }
}
