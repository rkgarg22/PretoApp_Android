package infrastructure;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Base64;
import android.util.Patterns;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class AppCommon {

    public static AppCommon mInstance = null;
    static Context mContext;
    public String favIconStatus = null;


    public static AppCommon getInstance(Context _Context) {
        if (mInstance == null) {
            mInstance = new AppCommon();
        }
        mContext = _Context;
        return mInstance;
    }

    public static boolean isValidEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    public static boolean isPasswordValid(String password) {
        return password.length() > 6;
    }

    public static boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }

    public static void showDialog(Activity mactivity, String title) {
        if (!mactivity.isFinishing()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mactivity);
            builder.setCancelable(false);
            builder.setMessage(title);
            builder.setCancelable(false);
            builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.show();
        }
    }

    public static boolean isUserLogIn() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPREFS_NAME, Context.MODE_PRIVATE);
        return mSharedPreferences.getBoolean(MYPerference.IS_USER_LOGIN, false);
    }

    public static void setIsUserLogIn(boolean isUserLogIn) {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putBoolean(MYPerference.IS_USER_LOGIN, isUserLogIn);
        mEditor.commit();
    }

    public void setUserId(String userID) {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putString(MYPerference.USER_ID, userID);
        mEditor.apply();
    }

    public String getUserId() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPREFS_NAME, Context.MODE_PRIVATE);
        return mSharedPreferences.getString(MYPerference.USER_ID, "");
    }


    public void setUserLatitude(double latitude) {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putFloat(MYPerference.USER_LATITUDE, (float) latitude);
        mEditor.commit();
    }

    public void setUserLongitude(double longitude) {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putFloat(MYPerference.USER_LONGITUDE, (float) longitude);

    }


    public float getUserLatitude() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPREFS_NAME, Context.MODE_PRIVATE);
        return mSharedPreferences.getFloat(MYPerference.USER_LATITUDE, 0.0f);
    }

    public float getUserLongitude() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPREFS_NAME, Context.MODE_PRIVATE);
        return mSharedPreferences.getFloat(MYPerference.USER_LONGITUDE, 0.0f);
    }

    public void setFirstName(String firstName) {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putString(MYPerference.FIRST_NAME, firstName);
        mEditor.apply();
    }

    public void setLastName(String lastName) {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putString(MYPerference.LAST_NAME, lastName);
        mEditor.apply();
    }

    public String getUserFirstName() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPREFS_NAME, Context.MODE_PRIVATE);
        return mSharedPreferences.getString(MYPerference.FIRST_NAME, "");
    }

    public String getUserLastName() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPREFS_NAME, Context.MODE_PRIVATE);
        return mSharedPreferences.getString(MYPerference.LAST_NAME, "");
    }


    public void setUserEmail(String emailAddress) {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putString(MYPerference.USER_EMAIL, emailAddress);
        mEditor.apply();
    }

    public String getUserEmail() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPREFS_NAME, Context.MODE_PRIVATE);
        return mSharedPreferences.getString(MYPerference.USER_EMAIL, "");
    }

    public void setNewsletterSubscritpionCheckboxState(boolean b) {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putBoolean(MYPerference.CHECKBOX_STATE, b);
        mEditor.apply();
    }

    public boolean getNewsletterSubscriptionCheckboxState() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPREFS_NAME, Context.MODE_PRIVATE);
        return mSharedPreferences.getBoolean(MYPerference.CHECKBOX_STATE, false);
    }

    public static void setDoneButtonState(boolean b) {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putBoolean(MYPerference.DONE_BUTTON_STATE, b);
        mEditor.apply();
    }

    public boolean getDoneButtonState() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPREFS_NAME, Context.MODE_PRIVATE);
        return mSharedPreferences.getBoolean(MYPerference.DONE_BUTTON_STATE, false);
    }

    public void setPushButtonState(String status) {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putString(MYPerference.PUSH_BUTTON_STATE, status);
        mEditor.apply();
    }

    public String getPushButtonState() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPREFS_NAME, Context.MODE_PRIVATE);
        return mSharedPreferences.getString(MYPerference.PUSH_BUTTON_STATE, "");
    }

    public void setFetchedLocation(boolean b) {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putBoolean(MYPerference.IS_LOCATION_FETCHED, b);
        mEditor.apply();
    }

    public boolean isFetchedLocation() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPREFS_NAME, Context.MODE_PRIVATE);
        return mSharedPreferences.getBoolean(MYPerference.IS_LOCATION_FETCHED, false);
    }

    public String getBase64ImageString(Bitmap photo) {
        String imgString;
        if (photo != null) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            byte[] profileImage = outputStream.toByteArray();
            imgString = Base64.encodeToString(profileImage, Base64.NO_WRAP);
        } else {
            imgString = "";
        }
        return imgString;
    }

    public void setAutoRenewButtonState(String status) {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putString(MYPerference.BUTTON_STATE, status);
        mEditor.apply();
    }

    public String getAutoRenewButtonState() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPREFS_NAME, Context.MODE_PRIVATE);
        return mSharedPreferences.getString(MYPerference.BUTTON_STATE, "");
    }

    public String getdeviceToken() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPREFS_NAME, mContext.MODE_PRIVATE);
        return mSharedPreferences.getString(MYPerference.DEVICE_TOKEN, "");
    }

    public void setDeviceToken(String status) {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPREFS_NAME, mContext.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putString(MYPerference.DEVICE_TOKEN, status);
        mEditor.commit();

    }

    public void setBrainTreeCustID(String custID) {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putString(MYPerference.CUST_ID, custID);
        mEditor.apply();
    }

    public String getBrainTreeCustID() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPREFS_NAME, Context.MODE_PRIVATE);
        return mSharedPreferences.getString(MYPerference.CUST_ID, "");
    }

    public void setRegistrationType(String type) {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putString(MYPerference.REG_TYPE, type);
        mEditor.apply();
    }

    public String getRegistrationType() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPREFS_NAME, Context.MODE_PRIVATE);
        return mSharedPreferences.getString(MYPerference.REG_TYPE, "");
    }

    public void setZipcode(String zipcode) {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putString(MYPerference.ZIP_CODE, zipcode);
        mEditor.apply();
    }

    public String getZipCode() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPREFS_NAME, Context.MODE_PRIVATE);
        return mSharedPreferences.getString(MYPerference.ZIP_CODE, "");
    }


    public void setImage(String image) {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putString(MYPerference.IMAGE, image);
        mEditor.apply();
    }

    public String getImage() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPREFS_NAME, Context.MODE_PRIVATE);
        return mSharedPreferences.getString(MYPerference.IMAGE, "");
    }

    public void setNonTouchableFlags(Activity activity) {
        if (activity != null) {
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

    public void clearNonTouchableFlags(Activity mActivity) {

        if (mActivity != null) {
            mActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }


    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }

    private void uriToBitmap(Uri selectedFileUri) {
        try {
            ParcelFileDescriptor parcelFileDescriptor =
                    mContext.getContentResolver().openFileDescriptor(selectedFileUri, "r");
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);


            parcelFileDescriptor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }


}



