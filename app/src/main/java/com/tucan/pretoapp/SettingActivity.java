package com.tucan.pretoapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import butterknife.ButterKnife;
import butterknife.OnClick;
import infrastructure.AppCommon;

public class SettingActivity extends GenricActivity {

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
        languageIntent.putExtra("isComingFromSetting", true);
        startActivity(languageIntent);
    }

    @OnClick(R.id.backButtonClick)
    public void backButtonClick(View view) {
        this.finish();
    }

    @OnClick(R.id.signOutLayout)
    public void signOutLayoutClick(View view) {
        showLogoutDialog(getResources().getString(R.string.logout_popup));
    }

    public void showLogoutDialog(String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setCancelable(true);
        builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                AppCommon.getInstance(SettingActivity.this).clearSharedPreference();
                Intent loginIntent = new Intent(SettingActivity.this, LoginOptionActivity.class);
                loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(loginIntent);
                SettingActivity.this.finish();
            }
        });
        builder.show();
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
