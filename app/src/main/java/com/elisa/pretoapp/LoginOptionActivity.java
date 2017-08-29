package com.elisa.pretoapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import infrastructure.AppCommon;

public class LoginOptionActivity extends AppCompatActivity {

    public int REGISTRATION_INTENT = 1000;

    @Bind(R.id.login_button)
    LoginButton loginButton;


    String firstName, lastNamw, email, gender, password, imageUrl;
    String facebookId = "";
    CallbackManager callbackManager;
    private AccessToken accessToken;
    private AccessTokenTracker accessTokenTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callbackManager = CallbackManager.Factory.create();
        setContentView(R.layout.activity_login_option);
        ButterKnife.bind(this);

        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                accessToken = currentAccessToken;
            }
        };
    }

    @OnClick(R.id.facebookBtnLayout)
    public void spanishBtnClick(View view){
        authorizeFaceBook();
    }

    @OnClick(R.id.emailBtnLayout)
    public void emailBtnClick(View view){
        Intent registrationIntent = new Intent(this,RegistrationActivity.class);
        startActivityForResult(registrationIntent,REGISTRATION_INTENT);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode== REGISTRATION_INTENT){
            if(resultCode == Activity.RESULT_OK){
                LoginOptionActivity.this.finish();
            }
        }else{
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void authorizeFaceBook() {
        LoginManager.getInstance().logInWithReadPermissions(LoginOptionActivity.this, Arrays.asList("email", "public_profile"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                accessToken = loginResult.getAccessToken();
                getFacebookData(loginResult);
            }

            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException error) {
            }
        });
    }

    private void getFacebookData(LoginResult loginResult) {
        final GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    Log.i("Response", response.toString());
                    facebookId = object.getString("id");
                    if (response.getJSONObject().has("email")) {
                        email = response.getJSONObject().getString("email");
                        firstName = object.getString("first_name");
                        lastNamw = object.getString("last_name");
                        password = getRandomPassword();
                        imageUrl = "https://graph.facebook.com/" + facebookId + "/picture?type=normal";
                        //progressBar.setVisibility(View.VISIBLE);
                        //callSignUpWebService(firstName, lastNamw, email, password, facebookId, "", imageUrl, "login/register", "facebook", String.valueOf(lat),String.valueOf(lon), AppCommon.getInstance(NewLoginActivity.this).getdeviceToken(), "android");
                        //AppCommon.getInstance(LoginOptionActivity.this).setRegistrationType("socialMedia");
                    } else {
                        AppCommon.getInstance(LoginOptionActivity.this).showDialog(LoginOptionActivity.this, "Your email can't be fetched \n" + "from facebook, Please use sign up");
                    }


                } catch (JSONException ex) {
                    ex.printStackTrace();
                }
            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,first_name,last_name,email");
        request.setParameters(parameters);
        request.executeAsync();
    }

    private String getRandomPassword() {
        char[] charset = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
        int length = 8;
        Random random = new SecureRandom();
        char[] result = new char[length];
        for (int i = 0; i < result.length; i++) {
            int randomCharIndex = random.nextInt(charset.length);
            result[i] = charset[randomCharIndex];
        }
        return new String(result);
    }

}
