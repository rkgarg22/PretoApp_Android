package com.tucan.pretoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class TermsAndConditionsActivity extends GenricActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_conditions);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.backButtonClick)
    public void backButtonClick(View view){
        this.finish();
    }
}
