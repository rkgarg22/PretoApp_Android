package com.tucan.pretoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WebViewActivity extends AppCompatActivity {
    @Bind(R.id.webView)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        ButterKnife.bind(this);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(getIntent().getExtras().getString("url"));
    }
}
