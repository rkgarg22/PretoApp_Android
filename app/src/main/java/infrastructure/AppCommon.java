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

import com.tucan.pretoapp.R;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import APIResponse.BannerObject;


public class AppCommon {

    public static AppCommon mInstance = null;
    static Context mContext;

    public static final int LOCATION_PERMISSION_REQUEST_CODE = 1100;
    public static final int INTENT_FOR_MAP_DISTANCE = 1000;
    public static final int INTENT_FOR_RESTURANT_DETAIL = 1001;
    public static final int RESTURANT_LIST_INTENT =1002;
    public static final int FILTER_INTENT = 1003;
    public static final int LANGUAGE_SELECT_INTENT = 1004;
    public static final int RESTURANT_LIST_INTENT_FROM_HOME_FOR_SEARCH = 1005;

    public static ArrayList<BannerObject> bannerObjectsArrayList = new ArrayList<BannerObject>();

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
            builder.setNegativeButton(mactivity.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
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
        mEditor.commit();
    }


    public float getUserLatitude() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPREFS_NAME, Context.MODE_PRIVATE);
        return mSharedPreferences.getFloat(MYPerference.USER_LATITUDE, 0.0f);
    }

    public float getUserLongitude() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPREFS_NAME, Context.MODE_PRIVATE);
        return mSharedPreferences.getFloat(MYPerference.USER_LONGITUDE, 0.0f);
    }

    public void setUserID(String userID) {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putString(MYPerference.USER_ID, userID);
        mEditor.apply();
    }

    public String getUserID() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPREFS_NAME, Context.MODE_PRIVATE);
        return mSharedPreferences.getString(MYPerference.USER_ID, "");
    }


    public void setName(String name) {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putString(MYPerference.NAME, name);
        mEditor.apply();
    }

    public String getName() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPREFS_NAME, Context.MODE_PRIVATE);
        return mSharedPreferences.getString(MYPerference.NAME, "");
    }

    public void setUserEmail(String userEmail) {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putString(MYPerference.EMAIL_ID, userEmail);
        mEditor.apply();
    }

    public String getUserEmail() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPREFS_NAME, Context.MODE_PRIVATE);
        return mSharedPreferences.getString(MYPerference.EMAIL_ID, "");
    }

    public void setProfilePicUrl(String profilePicUrl) {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putString(MYPerference.PROFILE_PIC_URL, profilePicUrl);
        mEditor.apply();
    }

    public String getProfilePicUrl() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPREFS_NAME, Context.MODE_PRIVATE);
        return mSharedPreferences.getString(MYPerference.PROFILE_PIC_URL, "");
    }

    public void setLanguage(String language) {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putString(MYPerference.LANGUGAGE_SELECTION, language);
        mEditor.apply();
    }

    public String getSelectedLanguage() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPREFS_NAME, Context.MODE_PRIVATE);
        return mSharedPreferences.getString(MYPerference.LANGUGAGE_SELECTION, "es");
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

    public void clearSharedPreference() {
        SharedPreferences mSharedPreferences = mContext.getSharedPreferences(MYPerference.mPREFS_NAME, mContext.MODE_PRIVATE);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.clear();
        mEditor.commit();
    }


}



