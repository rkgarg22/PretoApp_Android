package com.tucan.pretoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;

import CustomControl.MyriadProRegularTextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PrivacyPolicy extends GenricActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);
        ButterKnife.bind(this);


    }

    @OnClick(R.id.backButtonClick)
    public void backButtonClick(View view){
        this.finish();
    }
}
