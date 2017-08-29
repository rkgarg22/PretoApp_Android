package com.elisa.pretoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LanguageSelectActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_select);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.spanishLanguageBtn, R.id.englishLanguageBtn})
    public void spanishBtnClick(View view){
        Intent loginOptionScreen = new Intent(this,LoginOptionActivity.class);
        startActivity(loginOptionScreen);
        this.finish();
    }
}
