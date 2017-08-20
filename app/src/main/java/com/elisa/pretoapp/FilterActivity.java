package com.elisa.pretoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FilterActivity extends AppCompatActivity {

    @Bind(R.id.typeOFFoodOptionLayout)
    LinearLayout typeOFFoodOptionLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.backButtonClick, R.id.homeButtonClick})
    public void backButtonClick(View view) {
        this.finish();
    }

    @OnClick(R.id.typeOFFoodLayout)
    public void typeOFFoodClick(View view) {
        if (typeOFFoodOptionLayout.getVisibility() == View.VISIBLE) {
            typeOFFoodOptionLayout.setVisibility(View.GONE);
        } else {
            typeOFFoodOptionLayout.setVisibility(View.VISIBLE);
        }
    }
}
