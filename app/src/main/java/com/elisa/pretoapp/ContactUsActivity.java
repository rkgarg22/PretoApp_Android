package com.elisa.pretoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ContactUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.backButtonClick)
    public void backButtonClick(View view){
        this.finish();
    }

    @OnClick(R.id.homeButtonClick)
    public void homeBtnClick(View view){
        Intent i = new Intent();
        setResult(RESULT_OK, i);
        this.finish();
    }

}
