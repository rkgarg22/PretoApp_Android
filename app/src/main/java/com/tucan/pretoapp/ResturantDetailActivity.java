package com.tucan.pretoapp;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import java.util.ArrayList;

import API.PretoAppService;
import API.ServiceGenerator;
import APIResponse.CommonStringResponse;
import APIResponse.GetResturantDetailResponse;
import APIResponse.ResturantObject;
import Adapter.OpeningHoursAdapter;
import CustomControl.LatoBoldTextView;
import CustomControl.LatoHeavyTextView;
import CustomControl.LatoLightTextView;
import CustomControl.MyLinearLayoutManager;
import Database.DbHelper;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import infrastructure.AppCommon;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResturantDetailActivity extends GenricActivity {

    @Bind(R.id.resturantName)
    LatoBoldTextView resturantNameTextView;

    @Bind(R.id.registerDate)
    LatoLightTextView registerDateTextView;

    @Bind(R.id.likeCount)
    LatoBoldTextView likeCountTextView;

    @Bind(R.id.typeOFFood)
    LatoLightTextView typeOFFoodTextView;

    @Bind(R.id.category)
    LatoLightTextView categoryTextView;

    @Bind(R.id.address)
    LatoHeavyTextView addressTextView;

    @Bind(R.id.distance)
    LatoLightTextView distanceTextView;

    @Bind(R.id.openingHoursRecyclerView)
    RecyclerView openingHoursRecyclerView;

    @Bind(R.id.averageCost)
    LatoLightTextView averageCostTextView;

    @Bind(R.id.description)
    LatoLightTextView descriptionTextView;

    @Bind(R.id.history)
    LatoLightTextView historyTextView;

    @Bind(R.id.paymentMethodTextView)
    LatoLightTextView paymentMethodTextView;

    @Bind(R.id.otherTextView)
    LatoLightTextView otherTextView;

    @Bind(R.id.websiteTextView)
    LatoLightTextView websiteTextView;

    @Bind(R.id.menuTextView)
    LatoLightTextView menuTextView;

    @Bind(R.id.progressbar)
    ProgressBar progressBar;

    @Bind(R.id.dealImage)
    SimpleDraweeView dealImage;

    @Bind(R.id.likeImage)
    ImageView likeImageView;

    @Bind(R.id.favImage)
    ImageView favImageView;

    @Bind(R.id.circleTextView)
    TextView circleTextView;

    // @Bind(R.id.timingView)
    // LinearLayout timingView;

    @Bind(R.id.timingStatusTextView)
    LatoLightTextView timingStatusTextView;

    @Bind(R.id.infoImageView)
    ImageView infoImageView;

    @Bind(R.id.menuImageView)
    ImageView menuImageView;

    @Bind(R.id.infoContentLayout)
    LinearLayout infoContentLayout;

    @Bind(R.id.menuContentLayout)
    LinearLayout menuContentLayout;

    @Bind(R.id.deliveryPopUp)
    LinearLayout deliveryPopUp;

    @Bind(R.id.deliveryLayout)
    LinearLayout deliveryLayout;

    @Bind(R.id.decsriptionShowBtn)
    LatoHeavyTextView descriptionShowBtn;

    @Bind(R.id.historyShowBtn)
    LatoHeavyTextView historyShowBtn;

    Call call;
    String restID;
    ResturantObject resturantObject;
    private static final int REQUESTCODE_CALL_PERMISSION = 130;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resturant_detail);
        ButterKnife.bind(this);
        restID = getIntent().getExtras().getString("restID");

        infoImageView.setSelected(true);
        menuImageView.setSelected(false);
        MyLinearLayoutManager mLinearLayoutManager = new MyLinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        openingHoursRecyclerView.setLayoutManager(mLinearLayoutManager);
        getResturantDetail();
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

    @OnClick(R.id.mapClick)
    public void mapClick(View view) {
        Gson gson = new Gson();
        String objectStr = gson.toJson(resturantObject);
        Intent mapIntent = new Intent(this, MapActivityForDistance.class);
        mapIntent.putExtra("object", objectStr);
        startActivityForResult(mapIntent, AppCommon.INTENT_FOR_MAP_DISTANCE);
    }

    public void setData() {
        resturantNameTextView.setText(Html.fromHtml(resturantObject.getRestName()));
        registerDateTextView.setText(Html.fromHtml(getResources().getString(R.string.registered_since) + " " + resturantObject.getRegistered_date()));
        likeCountTextView.setText(Html.fromHtml(resturantObject.getLikesCount()));
        typeOFFoodTextView.setText(Html.fromHtml(getTypeOFFood(resturantObject.getTypeOfFood())));
        categoryTextView.setText(Html.fromHtml(resturantObject.getCategory()));
        addressTextView.setText(Html.fromHtml(getAddress(resturantObject.getAddress())));
        distanceTextView.setText(Html.fromHtml(resturantObject.getDistance()+" Km"));
        averageCostTextView.setText(Html.fromHtml(getResources().getString(R.string.from) + " $" + resturantObject.getPriceFrom() + " - " + getResources().getString(R.string.to) + " $" + resturantObject.getPriceTo()));
        descriptionTextView.setText(Html.fromHtml(resturantObject.getDescription()));
        historyTextView.setText(Html.fromHtml(resturantObject.getHistroy()));
        paymentMethodTextView.setText(Html.fromHtml(resturantObject.getPaymentMethod()));
        otherTextView.setText(Html.fromHtml(getOtherString(resturantObject.getOther())));
        websiteTextView.setText(Html.fromHtml(resturantObject.getWebUrl()));
        menuTextView.setText(Html.fromHtml(resturantObject.getMenu()));
        dealImage.setImageURI(Uri.parse(resturantObject.getImages()));

        OpeningHoursAdapter adapter = new OpeningHoursAdapter(this, resturantObject.getOperatingHourArrayList());
        openingHoursRecyclerView.setAdapter(adapter);

        if (resturantObject.getIsLiked().equals("1")) {
            likeImageView.setSelected(true);
        } else {
            likeImageView.setSelected(false);
        }

        if (resturantObject.getIsFavourite().equals("1")) {
            favImageView.setSelected(true);
        } else {
            favImageView.setSelected(false);
        }

        if (resturantObject.getColor().equals("0")) {
            //red
            circleTextView.setBackgroundResource(R.drawable.red_circle);
            openingHoursRecyclerView.setVisibility(View.VISIBLE);
            timingStatusTextView.setVisibility(View.GONE);
        } else if (resturantObject.getColor().equals("1")) {
            //green
            circleTextView.setBackgroundResource(R.drawable.green_circle);
            openingHoursRecyclerView.setVisibility(View.VISIBLE);
            timingStatusTextView.setVisibility(View.GONE);
        } else {
            //grey
            circleTextView.setBackgroundResource(R.drawable.grey_circle);
            openingHoursRecyclerView.setVisibility(View.GONE);
            timingStatusTextView.setVisibility(View.VISIBLE);
            deliveryPopUp.setVisibility(View.VISIBLE);
        }

        if (resturantObject.getServiceStatus().equals("no")) {
            deliveryLayout.setVisibility(View.GONE);
        } else {
            deliveryLayout.setVisibility(View.VISIBLE);
        }
    }

    public String getTypeOFFood(ArrayList<String> typeOFFood) {
        String typeofFoodString = "";
        for (String str : typeOFFood) {
            typeofFoodString = typeofFoodString + str.trim() + ", ";
        }
        typeofFoodString = typeofFoodString.substring(0, typeofFoodString.length() - 1);
        return typeofFoodString;
    }

    public String getAddress(String address) {
        String[] addressArray = address.split(",");
        String addressStr = "";
        for (String str : addressArray) {
            addressStr = addressStr + str.trim() + "\n";
        }
        addressStr = addressStr.substring(0, addressStr.length() - 1);
        return addressStr;
    }

    public String getOtherString(ArrayList<String> otherArray) {
        String otherStr = "";
        if (otherArray.size() > 0) {
            for (String str : otherArray) {
                otherStr = otherStr + str + "\n";
            }
            otherStr = otherStr.substring(0, otherStr.length() - 1);
        }
        return otherStr;
    }

    @OnClick(R.id.favoritesLayout)
    public void favoriteClick() {
        markFavourite();
    }

    @OnClick(R.id.likeLayout)
    public void likeLayoutClick() {
        markLike();
    }

    @OnClick(R.id.callLayout)
    public void callLayoutClick() {
        requestPermission(resturantObject.getPhoneNumber());
    }

    @OnClick(R.id.instagramLayout)
    public void instagramClick() {
        if (resturantObject.getInstagramAccount() != null) {
            Intent webViewIntent = new Intent(this, WebViewActivity.class);
            webViewIntent.putExtra("url", resturantObject.getInstagramAccount());
            startActivity(webViewIntent);
        }
    }

    @OnClick(R.id.commentsLayout)
    public void commentClick(View view) {
        String[] recipients = {"contacto@preto.co"};
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_EMAIL, recipients);
        intent.putExtra(Intent.EXTRA_SUBJECT, resturantObject.getRestName());
        intent.putExtra(Intent.EXTRA_TEXT, "");
        startActivity(Intent.createChooser(intent, "Send Email"));
    }

    @OnClick(R.id.deliveryLayout)
    public void deliveryClick() {
        if (!resturantObject.getServicePhone().equals("")) {
            requestPermission(resturantObject.getServicePhone());
        }
    }

    @OnClick(R.id.crossImageView)
    public void crossImageViewClick() {
        deliveryPopUp.setVisibility(View.GONE);
    }

    @OnClick(R.id.infoLayout)
    public void infoLayout(View view) {
        infoImageView.setSelected(true);
        menuImageView.setSelected(false);

        infoContentLayout.setVisibility(View.VISIBLE);
        menuContentLayout.setVisibility(View.GONE);
    }

    @OnClick(R.id.menuLayout)
    public void menuLayout(View view) {
        menuImageView.setSelected(true);
        infoImageView.setSelected(false);
        infoContentLayout.setVisibility(View.GONE);
        menuContentLayout.setVisibility(View.VISIBLE);
    }


    @OnClick(R.id.decsriptionShowBtn)
    public void descriptioShowBtn(View view) {
        if (descriptionTextView.getVisibility() == View.VISIBLE) {
            descriptionShowBtn.setText(getResources().getString(R.string.show));
            descriptionTextView.setVisibility(View.GONE);
        } else {
            descriptionShowBtn.setText(getResources().getString(R.string.hide));
            descriptionTextView.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.historyShowBtn)
    public void historyShowBtnClick(View view) {
        if (historyTextView.getVisibility() == View.VISIBLE) {
            historyShowBtn.setText(getResources().getString(R.string.show));
            historyTextView.setVisibility(View.GONE);
        } else {
            historyShowBtn.setText(getResources().getString(R.string.hide));
            historyTextView.setVisibility(View.VISIBLE);
        }
    }

    private void requestPermission(String phoneNumber) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.CALL_PHONE},
                    REQUESTCODE_CALL_PERMISSION);
        } else {
            fireCallIntent(phoneNumber);
        }
    }

    private void fireCallIntent(String mDealPhoneNo) {
        if (mDealPhoneNo != null) {
            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mDealPhoneNo));
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            startActivity(intent);
        }
    }

    private void getResturantDetail() {
        progressBar.setVisibility(View.VISIBLE);
        AppCommon.getInstance(this).setNonTouchableFlags(this);
        if (AppCommon.getInstance(this).isConnectingToInternet(this)) {
            PretoAppService pretoAppService = ServiceGenerator.createService(PretoAppService.class);
            call = pretoAppService.getResturantDetail(AppCommon.getInstance(this).getUserID(), restID,
                    AppCommon.getInstance(this).getSelectedLanguage(), Float.toString(AppCommon.getInstance(this).getUserLatitude()), Float.toString(AppCommon.getInstance(this).getUserLongitude()));
            call.enqueue(new Callback<GetResturantDetailResponse>() {
                @Override
                public void onResponse(Call call, Response response) {
                    AppCommon.getInstance(ResturantDetailActivity.this).clearNonTouchableFlags(ResturantDetailActivity.this);
                    GetResturantDetailResponse detailResponse = (GetResturantDetailResponse) response.body();
                    resturantObject = detailResponse.getRsturantObject();
                    progressBar.setVisibility(View.GONE);
                    if (detailResponse.getSuccess().equals("1")) {
                        setData();
                    } else {
                        progressBar.setVisibility(View.GONE);
                        AppCommon.getInstance(ResturantDetailActivity.this).showDialog(ResturantDetailActivity.this, detailResponse.getError());
                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    AppCommon.getInstance(ResturantDetailActivity.this).clearNonTouchableFlags(ResturantDetailActivity.this);
                    progressBar.setVisibility(View.GONE);
                    AppCommon.showDialog(ResturantDetailActivity.this, getString(R.string.serverError));
                }
            });
        } else {
            AppCommon.getInstance(ResturantDetailActivity.this).clearNonTouchableFlags(ResturantDetailActivity.this);
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


    public void markLike() {
        progressBar.setVisibility(View.VISIBLE);
        AppCommon.getInstance(this).setNonTouchableFlags(this);
        if (AppCommon.getInstance(this).isConnectingToInternet(this)) {
            PretoAppService pretoAppService = ServiceGenerator.createService(PretoAppService.class);
            call = pretoAppService.markLike(AppCommon.getInstance(this).getUserID(), restID);
            call.enqueue(new Callback<CommonStringResponse>() {
                @Override
                public void onResponse(Call call, Response response) {
                    AppCommon.getInstance(ResturantDetailActivity.this).clearNonTouchableFlags(ResturantDetailActivity.this);
                    CommonStringResponse commonStringResponse = (CommonStringResponse) response.body();
                    progressBar.setVisibility(View.GONE);
                    if (commonStringResponse.getSuccess().equals("1")) {
                        int likeCount = Integer.parseInt(resturantObject.getLikesCount());
                        if (resturantObject.getIsLiked().equals("1")) {
                            resturantObject.setIsLiked("0");
                            likeCount = likeCount - 1;
                            likeImageView.setSelected(false);
                        } else {
                            resturantObject.setIsLiked("1");
                            likeCount = likeCount + 1;
                            likeImageView.setSelected(true);
                        }
                        resturantObject.setLikesCount(Integer.toString(likeCount));
                        likeCountTextView.setText(resturantObject.getLikesCount());
                        saveDataToLocalDataBase(resturantObject);
                    } else {
                        progressBar.setVisibility(View.GONE);
                        AppCommon.getInstance(ResturantDetailActivity.this).showDialog(ResturantDetailActivity.this, commonStringResponse.getError());
                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    AppCommon.getInstance(ResturantDetailActivity.this).clearNonTouchableFlags(ResturantDetailActivity.this);
                    progressBar.setVisibility(View.GONE);
                    AppCommon.showDialog(ResturantDetailActivity.this, getString(R.string.serverError));
                }
            });
        } else {
            AppCommon.getInstance(ResturantDetailActivity.this).clearNonTouchableFlags(ResturantDetailActivity.this);
            progressBar.setVisibility(View.GONE);
            AppCommon.showDialog(this, this.getResources().getString(R.string.networkTitle));
        }
    }


    public void markFavourite() {
        progressBar.setVisibility(View.VISIBLE);
        AppCommon.getInstance(this).setNonTouchableFlags(this);
        if (AppCommon.getInstance(this).isConnectingToInternet(this)) {
            PretoAppService pretoAppService = ServiceGenerator.createService(PretoAppService.class);
            call = pretoAppService.markFavourite(AppCommon.getInstance(this).getUserID(), restID);
            call.enqueue(new Callback<CommonStringResponse>() {
                @Override
                public void onResponse(Call call, Response response) {
                    AppCommon.getInstance(ResturantDetailActivity.this).clearNonTouchableFlags(ResturantDetailActivity.this);
                    CommonStringResponse commonStringResponse = (CommonStringResponse) response.body();
                    progressBar.setVisibility(View.GONE);
                    if (commonStringResponse.getSuccess().equals("1")) {
                        if (resturantObject.getIsFavourite().equals("1")) {
                            resturantObject.setIsFavourite("0");
                            favImageView.setSelected(false);
                        } else {
                            resturantObject.setIsFavourite("1");
                            favImageView.setSelected(true);
                        }
                        saveDataToLocalDataBase(resturantObject);
                    } else {
                        progressBar.setVisibility(View.GONE);
                        AppCommon.getInstance(ResturantDetailActivity.this).showDialog(ResturantDetailActivity.this, commonStringResponse.getError());
                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    AppCommon.getInstance(ResturantDetailActivity.this).clearNonTouchableFlags(ResturantDetailActivity.this);
                    progressBar.setVisibility(View.GONE);
                    AppCommon.showDialog(ResturantDetailActivity.this, getString(R.string.serverError));
                }
            });
        } else {
            AppCommon.getInstance(ResturantDetailActivity.this).clearNonTouchableFlags(ResturantDetailActivity.this);
            progressBar.setVisibility(View.GONE);
            AppCommon.showDialog(this, this.getResources().getString(R.string.networkTitle));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppCommon.INTENT_FOR_MAP_DISTANCE) {
            if (resultCode == Activity.RESULT_OK) {
                Intent i = new Intent();
                setResult(RESULT_OK, i);
                this.finish();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUESTCODE_CALL_PERMISSION:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fireCallIntent(resturantObject.getPhoneNumber());
                }
        }
    }

    public void saveDataToLocalDataBase(ResturantObject object) {
        DbHelper dbHelper = DbHelper.getInstance(this);
        if (dbHelper.isResturantExist(object.getRestID())) {
            dbHelper.updateRest(object);
        } else {
            dbHelper.insertResturant(object);
        }
    }
}
