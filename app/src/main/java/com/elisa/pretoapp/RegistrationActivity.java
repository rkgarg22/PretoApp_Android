package com.elisa.pretoapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ProgressBar;

import API.PretoAppService;
import API.ServiceGenerator;
import APIEntity.UserInformation_Entity;
import APIResponse.RegistrationResponse;
import CustomControl.LatoHeavyEditText;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import infrastructure.AppCommon;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationActivity extends AppCompatActivity {

    public int LOGIN_INTENT = 100;

    @Bind(R.id.nameEditText)
    LatoHeavyEditText nameEditText;

    @Bind(R.id.emailEditText)
    LatoHeavyEditText emailEditText;

    @Bind(R.id.passwordEditText)
    LatoHeavyEditText passwordEditText;

    @Bind(R.id.confirmEmailEditText)
    LatoHeavyEditText confirmEmailEditText;

    @Bind(R.id.progressbar)
    ProgressBar progressBar;

    @Bind(R.id.acceptCheckBox)
    CheckBox acceptCheckBox;

    String name;
    String email;
    String password;
    String confirmEmail;
    Call call;

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

    @OnClick(R.id.registerBtnClick)
    public void signInButtonClick(View view) {
        if (validations()) {
            progressBar.setVisibility(View.VISIBLE);
            callSignUpWebService();
        }
    }

    public Boolean validations() {
        Boolean validate = true;
        name = nameEditText.getText().toString().trim();
        email = emailEditText.getText().toString().trim();
        password = passwordEditText.getText().toString().trim();
        confirmEmail = confirmEmailEditText.getText().toString().trim();

        if (name.isEmpty()) {
            nameEditText.setError(getResources().getString(R.string.enterName));
            validate = false;
        } else if (email.isEmpty()) {
            emailEditText.setError(getResources().getString(R.string.enterEmailText));
            validate = false;
        } else if (!AppCommon.getInstance(this).isValidEmail(email)) {
            emailEditText.setError(getResources().getString(R.string.enterValidEmailText));
            validate = false;
        } else if (!email.equals(confirmEmail)) {
            confirmEmailEditText.setError(getResources().getString(R.string.email_not_confimed));
            validate = false;
        } else if (!acceptCheckBox.isChecked()) {
            AppCommon.showDialog(this, this.getResources().getString(R.string.accept_terms_and_conditon));
            validate = false;
        }
        return validate;
    }

    @OnClick(R.id.privacyPolicyText)
    public void privacyPolicyClick() {
        Intent privacyPolicyIntent = new Intent(this, PrivacyPolicy.class);
        startActivity(privacyPolicyIntent);
    }

    private void callSignUpWebService() {
        AppCommon.getInstance(this).setNonTouchableFlags(this);
        if (AppCommon.getInstance(this).isConnectingToInternet(this)) {
            progressBar.setVisibility(View.VISIBLE);
            UserInformation_Entity mUserInformation_entity = new UserInformation_Entity(name, email, "", "", "", "android");
            PretoAppService pretoService = ServiceGenerator.createService(PretoAppService.class);
            call = pretoService.userRegistration(mUserInformation_entity);
            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    AppCommon.getInstance(RegistrationActivity.this).clearNonTouchableFlags(RegistrationActivity.this);
                    RegistrationResponse registrationResponse = (RegistrationResponse) response.body();
                    progressBar.setVisibility(View.GONE);
                    if (registrationResponse.getSuccess().equals("1")) {
                        showSuccessfulDialog(getResources().getString(R.string.registerSuccessfully));
                        AppCommon.getInstance(RegistrationActivity.this).setIsUserLogIn(true);
                        AppCommon.getInstance(RegistrationActivity.this).setUserID(registrationResponse.getUserEntity().getUserID());
                        AppCommon.getInstance(RegistrationActivity.this).setName(registrationResponse.getUserEntity().getName());
                        AppCommon.getInstance(RegistrationActivity.this).setUserEmail(registrationResponse.getUserEntity().getEmailAddress());
                        AppCommon.getInstance(RegistrationActivity.this).setProfilePicUrl(registrationResponse.getUserEntity().getImageUrl());
                    } else {
                        progressBar.setVisibility(View.GONE);
                        AppCommon.showDialog(RegistrationActivity.this, registrationResponse.getError());
                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    AppCommon.getInstance(RegistrationActivity.this).clearNonTouchableFlags(RegistrationActivity.this);
                    progressBar.setVisibility(View.GONE);
                    AppCommon.showDialog(RegistrationActivity.this, getString(R.string.serverError));
                }
            });
        } else {
            AppCommon.getInstance(RegistrationActivity.this).clearNonTouchableFlags(RegistrationActivity.this);
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
                Intent intent = new Intent(RegistrationActivity.this, HomeActivity.class);
                startActivity(intent);
                Intent backIntent = new Intent();
                setResult(RESULT_OK, backIntent);
                RegistrationActivity.this.finish();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == LOGIN_INTENT) {
            if (resultCode == Activity.RESULT_OK) {
                Intent i = new Intent();
                setResult(RESULT_OK, i);
                RegistrationActivity.this.finish();
            }
        }
    }
}
