package com.elisa.pretoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegistrationActivity extends AppCompatActivity {

    public int LOGIN_INTENT = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.signInClick)
    public void logInClick() {
        Intent loginIntent = new Intent(this, LoginActivity.class);
        startActivityForResult(loginIntent, LOGIN_INTENT);
    }
}
