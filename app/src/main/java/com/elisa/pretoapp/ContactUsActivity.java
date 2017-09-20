package com.elisa.pretoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import CustomControl.LatoBoldEditText;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import infrastructure.AppCommon;

public class ContactUsActivity extends AppCompatActivity {

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

    String name;
    String phoneNumber;
    String email;
    String subject;
    String message;

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
            nameEditText.setError(getResources().getString(R.string.name));
            isDataValid = false;
        } else if (phoneNumber.length() == 0) {
            phoneNumberEditText.setError(getResources().getString(R.string.phoneNumber));
            isDataValid = false;
        } else if (email.length() == 0) {
            emailEditText.setError(getResources().getString(R.string.email));
            isDataValid = false;
        } else if (!AppCommon.getInstance(this).isValidEmail(email)) {
            emailEditText.setError(getResources().getString(R.string.enterValidEmailText));
            isDataValid = false;
        } else if (subject.length() == 0) {
            subjectEditText.setError(getResources().getString(R.string.subject));
            isDataValid = false;
        } else if (message.length() == 0) {
            messageEditText.setError(getResources().getString(R.string.message));
            isDataValid = false;
        }
        return isDataValid;
    }

    @OnClick(R.id.sendBtnClick)
    public void sendClick() {
        if (isDataValid()) {

        }
    }

}
