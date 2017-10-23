package com.tucan.pretoapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HowDoesItWorkActivity extends GenricActivity {

    @Bind(R.id.webView)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_does_it_works);
        ButterKnife.bind(this);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://youtu.be/WViVVzHhGYA");
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
