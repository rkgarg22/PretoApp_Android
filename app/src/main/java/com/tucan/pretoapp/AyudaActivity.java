package com.tucan.pretoapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AyudaActivity extends AppCompatActivity {

    public int FAQ_INTENT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayuda);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.faqLayout)
    public void faqClick(View view){
        Intent faqIntent = new Intent(this,FaqActivity.class);
        startActivityForResult(faqIntent,FAQ_INTENT);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == FAQ_INTENT) {
            if (resultCode == Activity.RESULT_OK) {
                Intent i = new Intent();
                setResult(RESULT_OK, i);
                AyudaActivity.this.finish();
            }
        }
    }
}
