package com.elisa.pretoapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingActivity extends AppCompatActivity {

    public int CONTACT_US_INTENT = 1000;
    public int HELP_INTENT = 1001;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.privacyPolicyLayout)
    public void privacyPolicyBtnClick(View view) {
        Intent privacyPolicyIntent = new Intent(this, PrivacyPolicy.class);
        startActivity(privacyPolicyIntent);
    }

    @OnClick(R.id.termsAndConditionLayout)
    public void termsAndConditionLayoutClick(View view) {
        Intent termsAndConditionLayout = new Intent(this, TermsAndConditionsActivity.class);
        startActivity(termsAndConditionLayout);
    }

    @OnClick(R.id.helpLayout)
    public void helpClick(View view) {
        Intent helpIntent = new Intent(this, AyudaActivity.class);
        startActivityForResult(helpIntent, HELP_INTENT);
    }

    @OnClick(R.id.contactUsLayout)
    public void contactUsClick(View view) {
        Intent contactUsIntent = new Intent(this, ContactUsActivity.class);
        startActivityForResult(contactUsIntent, CONTACT_US_INTENT);
    }

    @OnClick(R.id.changeLanguageLayout)
    public void changeLanguageClick(View view) {
        Intent languageIntent = new Intent(this, LanguageSelectActivity.class);
        startActivity(languageIntent);
    }

    @OnClick(R.id.backButtonClick)
    public void backButtonClick(View view) {
        this.finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == HELP_INTENT || requestCode == CONTACT_US_INTENT) {
            if (resultCode == Activity.RESULT_OK) {
                SettingActivity.this.finish();
            }
        }
    }
}
