package com.elisa.pretoapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginOptionActivity extends AppCompatActivity {

    public int REGISTRATION_INTENT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_option);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.facebookBtnLayout)
    public void spanishBtnClick(View view){
        Intent loginOptionScreen = new Intent(this,HomeActivity.class);
        startActivity(loginOptionScreen);
        this.finish();
    }

    @OnClick(R.id.emailBtnLayout)
    public void emailBtnClick(View view){
        Intent registrationIntent = new Intent(this,RegistrationActivity.class);
        startActivityForResult(registrationIntent,REGISTRATION_INTENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== REGISTRATION_INTENT){
            if(resultCode == Activity.RESULT_OK){
                LoginOptionActivity.this.finish();
            }
        }

    }
}
