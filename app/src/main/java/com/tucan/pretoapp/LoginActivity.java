package com.tucan.pretoapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import API.PretoAppService;
import API.ServiceGenerator;
import APIEntity.Login_Entity;
import APIResponse.LoginResponse;
import CustomControl.LatoHeavyEditText;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import infrastructure.AppCommon;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends GenricActivity {

    @Bind(R.id.emailEditText)
    LatoHeavyEditText emailEditText;

    @Bind(R.id.passwordEditText)
    LatoHeavyEditText passwordEditText;

    @Bind(R.id.progressbar)
    ProgressBar progressBar;

    String mEmailString;
    String mPasswordString;
    Call call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.signInButton)
    public void signInButtonClick(View view) {
        if (validations()) {
            progressBar.setVisibility(View.VISIBLE);
            callLoginWebservice();
        }
    }

    public Boolean validations() {
        Boolean validate = true;
        mEmailString = emailEditText.getText().toString().trim();
        mPasswordString = passwordEditText.getText().toString().trim();
        if (mEmailString.isEmpty()) {
            emailEditText.setError(getResources().getString(R.string.enterEmailText));
            validate = false;
        } else if (!AppCommon.getInstance(this).isValidEmail(mEmailString)) {
            emailEditText.setError(getResources().getString(R.string.enterValidEmailText));
            validate = false;
        }
//        else if (mPasswordString.isEmpty()) {
//            passwordEditText.setError(getResources().getString(R.string.enterPassword));
//            validate = false;
//        }
        return validate;
    }

    private void callLoginWebservice() {
        AppCommon.getInstance(this).setNonTouchableFlags(this);
        if (AppCommon.getInstance(this).isConnectingToInternet(this)) {
            final Login_Entity loginEntity = new Login_Entity(mEmailString, "", "android");
            PretoAppService pretoAppService = ServiceGenerator.createService(PretoAppService.class);
            call = pretoAppService.userLogin(loginEntity);
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call call, Response response) {
                    AppCommon.getInstance(LoginActivity.this).clearNonTouchableFlags(LoginActivity.this);
                    LoginResponse loginResponse = (LoginResponse) response.body();
                    progressBar.setVisibility(View.GONE);
                    if (loginResponse.getSuccess().equals("1")) {
                        showSuccessfulDialog(getResources().getString(R.string.loginSuccessfully));
                        AppCommon.getInstance(LoginActivity.this).setIsUserLogIn(true);
                        AppCommon.getInstance(LoginActivity.this).setUserID(loginResponse.getUserEntity().getUserID());
                        AppCommon.getInstance(LoginActivity.this).setName(loginResponse.getUserEntity().getName());
                        AppCommon.getInstance(LoginActivity.this).setUserEmail(loginResponse.getUserEntity().getEmailAddress());
                        AppCommon.getInstance(LoginActivity.this).setProfilePicUrl(loginResponse.getUserEntity().getImageUrl());
                    } else {
                        progressBar.setVisibility(View.GONE);
                        AppCommon.getInstance(LoginActivity.this).showDialog(LoginActivity.this, loginResponse.getError());
                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    AppCommon.getInstance(LoginActivity.this).clearNonTouchableFlags(LoginActivity.this);
                    progressBar.setVisibility(View.GONE);
                    AppCommon.showDialog(LoginActivity.this, getString(R.string.serverError));
                }
            });
        } else {
            AppCommon.getInstance(LoginActivity.this).clearNonTouchableFlags(LoginActivity.this);
            progressBar.setVisibility(View.GONE);
            AppCommon.showDialog(this, this.getResources().getString(R.string.networkTitle));
        }
    }

    public void showSuccessfulDialog(String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setCancelable(false);
        builder.setNegativeButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                Intent loginIntent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(loginIntent);

                Intent backIntent = new Intent();
                setResult(RESULT_OK, backIntent);
                LoginActivity.this.finish();
            }
        });
        builder.show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (call != null) {
            call.cancel();
        }
    }
}
