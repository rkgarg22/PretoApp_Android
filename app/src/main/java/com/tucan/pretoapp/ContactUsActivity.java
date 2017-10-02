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
import APIEntity.ContactUs_Entity;
import APIEntity.UserInformation_Entity;
import APIResponse.CommonStringResponse;
import APIResponse.RegistrationResponse;
import CustomControl.LatoBoldEditText;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import infrastructure.AppCommon;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactUsActivity extends GenricActivity {

    @Bind(R.id.nameEditText)
    LatoBoldEditText nameEditText;

    @Bind(R.id.phoneNumberEditText)
    LatoBoldEditText phoneNumberEditText;

    @Bind(R.id.emailEditText)
    LatoBoldEditText emailEditText;

    @Bind(R.id.subjectEditText)
    LatoBoldEditText subjectEditText;

    @Bind(R.id.messageEditText)
    LatoBoldEditText messageEditText;

    @Bind(R.id.progressbar)
    ProgressBar progressBar;

    String name;
    String phoneNumber;
    String email;
    String subject;
    String message;
    Call call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.backButtonClick)
    public void backButtonClick(View view) {
        this.finish();
    }

    @OnClick(R.id.homeButtonClick)
    public void homeBtnClick(View view) {
        Intent i = new Intent();
        setResult(RESULT_OK, i);
        this.finish();
    }

    public boolean isDataValid() {
        boolean isDataValid = true;

        name = nameEditText.getText().toString().trim();
        phoneNumber = phoneNumberEditText.getText().toString().trim();
        email = emailEditText.getText().toString().trim();
        subject = subjectEditText.getText().toString().trim();
        message = messageEditText.getText().toString().trim();

        if (name.length() == 0) {
            nameEditText.setError(getResources().getString(R.string.name_enter));
            isDataValid = false;
        } else if (phoneNumber.length() == 0) {
            phoneNumberEditText.setError(getResources().getString(R.string.phoneNumber_enter));
            isDataValid = false;
        } else if (email.length() == 0) {
            emailEditText.setError(getResources().getString(R.string.email_enter));
            isDataValid = false;
        } else if (!AppCommon.getInstance(this).isValidEmail(email)) {
            emailEditText.setError(getResources().getString(R.string.enterValidEmailText));
            isDataValid = false;
        } else if (subject.length() == 0) {
            subjectEditText.setError(getResources().getString(R.string.subject));
            isDataValid = false;
        } else if (message.length() == 0) {
            messageEditText.setError(getResources().getString(R.string.message_enter));
            isDataValid = false;
        }
        return isDataValid;
    }

    @OnClick(R.id.sendBtnClick)
    public void sendClick() {
        if (isDataValid()) {
            callContactUSWebService();
        }
    }

    private void callContactUSWebService() {
        AppCommon.getInstance(this).setNonTouchableFlags(this);
        if (AppCommon.getInstance(this).isConnectingToInternet(this)) {
            progressBar.setVisibility(View.VISIBLE);
            ContactUs_Entity entity = new ContactUs_Entity(name,AppCommon.getInstance(this).getUserID(),phoneNumber,email,subject,message);
            PretoAppService pretoService = ServiceGenerator.createService(PretoAppService.class);
            call = pretoService.contactUs(entity);
            call.enqueue(new Callback() {
                @Override
                public void onResponse(Call call, Response response) {
                    AppCommon.getInstance(ContactUsActivity.this).clearNonTouchableFlags(ContactUsActivity.this);
                    CommonStringResponse registrationResponse = (CommonStringResponse) response.body();
                    progressBar.setVisibility(View.GONE);
                    if (registrationResponse.getSuccess().equals("1")) {
                        showSuccessfulDialog(getString(R.string.contact_us_popup));
                    } else {
                        progressBar.setVisibility(View.GONE);
                        AppCommon.showDialog(ContactUsActivity.this, registrationResponse.getError());
                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    AppCommon.getInstance(ContactUsActivity.this).clearNonTouchableFlags(ContactUsActivity.this);
                    progressBar.setVisibility(View.GONE);
                    AppCommon.showDialog(ContactUsActivity.this, getString(R.string.serverError));
                }
            });
        } else {
            AppCommon.getInstance(ContactUsActivity.this).clearNonTouchableFlags(ContactUsActivity.this);
            progressBar.setVisibility(View.GONE);
            AppCommon.showDialog(this, this.getResources().getString(R.string.networkTitle));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (call != null) {
            call.cancel();
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
                ContactUsActivity.this.finish();
            }
        });
        builder.show();
    }

}
