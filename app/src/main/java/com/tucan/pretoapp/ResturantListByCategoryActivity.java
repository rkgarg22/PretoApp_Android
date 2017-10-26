package com.tucan.pretoapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;

import java.util.ArrayList;

import API.PretoAppService;
import API.ServiceGenerator;
import APIEntity.FilterObject;
import APIEntity.GetResturantListEntity;
import APIResponse.CommonStringResponse;
import APIResponse.GetResturantListResponse;
import APIResponse.ResturantObject;
import Adapter.ResturantAdapter;
import CustomControl.LatoBoldEditText;
import Database.DbHelper;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import infrastructure.AppCommon;
import infrastructure.GenericMapActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResturantListByCategoryActivity extends GenericMapActivity {


    @Bind(R.id.resturantRecyclerView)
    RecyclerView resturantRecyclerView;

    @Bind(R.id.categoryIconImage)
    ImageView categoryIconImage;

    @Bind(R.id.headerTextView)
    TextView headerTextView;

    @Bind(R.id.progressbar)
    ProgressBar progressBar;

    @Bind(R.id.searchLayout)
    RelativeLayout searchlayout;

    @Bind(R.id.searchEditText)
    LatoBoldEditText searchEditText;

    @Bind(R.id.swipeContainer)
    SwipeRefreshLayout swipeContainer;

    @Bind(R.id.imageLayout)
    RelativeLayout imageLayout;

    @Bind(R.id.buttonLayout)
    LinearLayout buttonLayout;

    @Bind(R.id.adLayoutParentView)
    RelativeLayout adLayoutParentView;

    @Bind(R.id.mapFragmentLayout)
    LinearLayout mapFragmentLayout;

    @Bind(R.id.transparentBanner)
    LinearLayout transparentBanner;

    @Bind(R.id.bannerImage)
    ImageView upperBannerImageView;

    ResturantAdapter adapter;
    Call call;
    boolean isSwipeRefresh = false;
    String searchText = "";
    String catID = "";
    String addressText = "";
    FilterObject filterObject;
    Gson gson;
    boolean isApplyInternalFilter = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resturant_list_bycategory);
        ButterKnife.bind(this);
        bannerImageView = (SimpleDraweeView) findViewById(R.id.footerBannerImage);
        markerClickLayout = (RelativeLayout) findViewById(R.id.markerClickLayout);
        getDataFromIntent();
        progressBar.setVisibility(View.VISIBLE);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        resturantRecyclerView.setLayoutManager(mLinearLayoutManager);

        adapter = new ResturantAdapter(this, resturantObjectArrayList);
        resturantRecyclerView.setAdapter(adapter);
        callApi(1);

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isSwipeRefresh = true;
                callApi(1);
            }
        });
        setMapView();
        showBanner();
    }

    public void getDataFromIntent() {
        final int categoryID = getIntent().getExtras().getInt("categorySelect");
        gson = new Gson();
        if (categoryID == 1) {
            filterObject = gson.fromJson(getIntent().getExtras().getString("filterObject"), FilterObject.class);
        } else {
            filterObject = new FilterObject();
        }

        if (categoryID == 0 || categoryID == 1) {
            catID = "";
        } else {
            catID = Integer.toString(categoryID);
        }
        searchText = getIntent().getExtras().getString("searchText");
        addressText = getIntent().getExtras().getString("addressText");
        setImage(categoryID);
    }

    public void callApi(int offset) {
        getResturantList(offset);
    }


    public void setImage(int tag) {
        switch (tag) {
            case 11:
                categoryIconImage.setImageResource(R.drawable.breakfast);
                headerTextView.setText(getResources().getString(R.string.breakfast));
                upperBannerImageView.setBackgroundResource(R.drawable.breakfast_banner);
                break;
            case 13:
                categoryIconImage.setImageResource(R.drawable.lunch);
                headerTextView.setText(getResources().getString(R.string.lunch));
                upperBannerImageView.setBackgroundResource(R.drawable.lunch_banner);
                break;
            case 15:
                categoryIconImage.setImageResource(R.drawable.dinner);
                headerTextView.setText(getResources().getString(R.string.dinner));
                upperBannerImageView.setBackgroundResource(R.drawable.dinner_banner);
                break;
            case 19:
                categoryIconImage.setImageResource(R.drawable.custom_made);
                headerTextView.setText(getResources().getString(R.string.preorder));
                upperBannerImageView.setBackgroundResource(R.drawable.pre_order_banner);
                break;
            case 17:
                categoryIconImage.setImageResource(R.drawable.cravings);
                headerTextView.setText(getResources().getString(R.string.snacks));
                upperBannerImageView.setBackgroundResource(R.drawable.snacks_banner);
                break;
            case 21:
                categoryIconImage.setImageResource(R.drawable.supplies);
                headerTextView.setText(getResources().getString(R.string.other));
                upperBannerImageView.setBackgroundResource(R.drawable.supplies_banner);
                break;
            case 23:
                categoryIconImage.setImageResource(R.drawable.near_you);
                headerTextView.setText(getResources().getString(R.string.near_you));
                upperBannerImageView.setBackgroundResource(R.drawable.nearby_banner);
                transparentBanner.setVisibility(View.GONE);
                break;
            default:
                categoryIconImage.setImageResource(R.drawable.search);
                headerTextView.setText(getIntent().getExtras().getString("searchText"));
                if (searchText.equals("")) {
                    headerTextView.setText(addressText);
                }
                upperBannerImageView.setBackgroundResource(R.drawable.search_banner);
                break;
        }
    }

    @OnClick(R.id.backButtonClick)
    public void backButtonClick(View view) {
        if (isApplyInternalFilter) {
            isApplyInternalFilter = false;
            resturantObjectArrayList.clear();
            getDataFromIntent();
            for (ResturantObject object : originalResturantObjectArrayList) {
                resturantObjectArrayList.add(object);
            }
            adapter.notifyDataSetChanged();
            onMapReady(mMap);
        } else {
            this.finish();
        }
    }

    @OnClick(R.id.homeBtn)
    public void homeBtnClick(View view) {
        Intent i = new Intent();
        setResult(RESULT_OK, i);
        this.finish();
    }

    @OnClick(R.id.listButton)
    public void listButtonClick() {
        isMapActive = false;
        imageLayout.setVisibility(View.VISIBLE);
        adLayoutParentView.setVisibility(View.VISIBLE);
        swipeContainer.setVisibility(View.VISIBLE);
        mapFragmentLayout.setVisibility(View.GONE);
    }

    @OnClick(R.id.mapButton)
    public void mapButtonClick() {
        isMapActive = true;
        progressBar.setVisibility(View.VISIBLE);
        resturantObjectArrayList.clear();
        onMapReady(mMap);
        callApi(0);
        imageLayout.setVisibility(View.GONE);
        adLayoutParentView.setVisibility(View.GONE);
        swipeContainer.setVisibility(View.GONE);
        mapFragmentLayout.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.searchBtnClick)
    public void searchBtnClick(View view) {
        searchlayout.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.searchLayoutBackgroundBtn)
    public void searchLayoutBackgroundBtn(View view) {
        searchlayout.setVisibility(View.GONE);
    }

    @OnClick(R.id.cancelButton)
    public void cancelButtonClick(View view) {
        markerClickLayout.setVisibility(View.GONE);
    }

    @OnClick(R.id.filterBtnClick)
    public void filterClick() {
        Intent filterIntent = new Intent(this, FilterActivity.class);
        filterIntent.putExtra("isComingFromHome", false);
        startActivityForResult(filterIntent, AppCommon.FILTER_INTENT);
    }


    @OnClick(R.id.searchActionPerformed)
    public void searchActionPerformed(View view) {
        searchPerform();
    }

    public void searchPerform() {
        searchText = searchEditText.getText().toString().trim();
        hideKeyBoard();
        searchlayout.setVisibility(View.GONE);
        saveDefaultResturantList();
        resturantObjectArrayList.clear();
        adapter.notifyDataSetChanged();
        progressBar.setVisibility(View.VISIBLE);
        if (isMapActive)
            callApi(0);
        else {
            callApi(1);
        }
    }

    public void saveDefaultResturantList() {
        if (!isApplyInternalFilter) {
            isApplyInternalFilter = true;
            for (ResturantObject object : resturantObjectArrayList) {
                originalResturantObjectArrayList.add(object);
            }
        }
    }

    @OnEditorAction(R.id.searchEditText)
    public boolean onSearchClick(TextView textView, int i, KeyEvent keyEvent) {
        if (i == EditorInfo.IME_ACTION_SEARCH) {
            searchPerform();
            return true;
        }
        return false;
    }

    public void getResturantList(int offset) {
        AppCommon.getInstance(this).setNonTouchableFlags(this);
        if (AppCommon.getInstance(this).isConnectingToInternet(this)) {
            GetResturantListEntity listEntity = new GetResturantListEntity(AppCommon.getInstance(this).getUserID(),
                    catID,
                    AppCommon.getInstance(this).getSelectedLanguage(), Integer.toString(offset), Float.toString(AppCommon.getInstance(this).getUserLatitude()),
                    Float.toString(AppCommon.getInstance(this).getUserLongitude()), filterObject, searchText, addressText);
            PretoAppService pretoAppService = ServiceGenerator.createService(PretoAppService.class);
            call = pretoAppService.getResturantList(listEntity);
            call.enqueue(new Callback<GetResturantListResponse>() {
                @Override
                public void onResponse(Call call, Response response) {
                    AppCommon.getInstance(ResturantListByCategoryActivity.this).clearNonTouchableFlags(ResturantListByCategoryActivity.this);
                    GetResturantListResponse listResponse = (GetResturantListResponse) response.body();
                    progressBar.setVisibility(View.GONE);
                    swipeContainer.setRefreshing(false);
                    if (listResponse.getSuccess().equals("1")) {
                        if (isSwipeRefresh) {
                            resturantObjectArrayList.clear();
                            isSwipeRefresh = false;
                        }
                        for (ResturantObject object : listResponse.getResturantObjectList()) {
                            resturantObjectArrayList.add(object);
                            saveDataToLocalDataBase(object);
                        }
                        adapter.notifyDataSetChanged();
                        onMapReady(mMap);
                    } else {
                        progressBar.setVisibility(View.GONE);
                        noResturantPopup(listResponse.getError());
                        //AppCommon.getInstance(ResturantListByCategoryActivity.this).showDialog(ResturantListByCategoryActivity.this, listResponse.getError());
                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    getDataFromLocalDataBase();
                    swipeContainer.setRefreshing(false);
                    AppCommon.getInstance(ResturantListByCategoryActivity.this).clearNonTouchableFlags(ResturantListByCategoryActivity.this);
                    progressBar.setVisibility(View.GONE);
                    AppCommon.showDialog(ResturantListByCategoryActivity.this, getString(R.string.serverError));
                }
            });
        } else {
            getDataFromLocalDataBase();
            swipeContainer.setRefreshing(false);
            AppCommon.getInstance(ResturantListByCategoryActivity.this).clearNonTouchableFlags(ResturantListByCategoryActivity.this);
            progressBar.setVisibility(View.GONE);
            AppCommon.showDialog(this, this.getResources().getString(R.string.networkTitle));
        }
    }

    public void markLike(int position) {
        progressBar.setVisibility(View.VISIBLE);
        AppCommon.getInstance(this).setNonTouchableFlags(this);
        if (AppCommon.getInstance(this).isConnectingToInternet(this)) {
            final ResturantObject object = resturantObjectArrayList.get(position);
            PretoAppService pretoAppService = ServiceGenerator.createService(PretoAppService.class);
            call = pretoAppService.markLike(AppCommon.getInstance(this).getUserID(), object.getRestID());
            call.enqueue(new Callback<CommonStringResponse>() {
                @Override
                public void onResponse(Call call, Response response) {
                    AppCommon.getInstance(ResturantListByCategoryActivity.this).clearNonTouchableFlags(ResturantListByCategoryActivity.this);
                    CommonStringResponse commonStringResponse = (CommonStringResponse) response.body();
                    progressBar.setVisibility(View.GONE);
                    if (commonStringResponse.getSuccess().equals("1")) {
                        int likeCount = Integer.parseInt(object.getLikesCount());
                        if (object.getIsLiked().equals("1")) {
                            object.setIsLiked("0");
                            likeCount = likeCount - 1;
                        } else {
                            object.setIsLiked("1");
                            likeCount = likeCount + 1;
                        }
                        object.setLikesCount(Integer.toString(likeCount));
                        saveDataToLocalDataBase(object);
                        adapter.notifyDataSetChanged();
                    } else {
                        progressBar.setVisibility(View.GONE);
                        AppCommon.showDialog(ResturantListByCategoryActivity.this, commonStringResponse.getError());
                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    AppCommon.getInstance(ResturantListByCategoryActivity.this).clearNonTouchableFlags(ResturantListByCategoryActivity.this);
                    progressBar.setVisibility(View.GONE);
                    AppCommon.showDialog(ResturantListByCategoryActivity.this, getString(R.string.serverError));
                }
            });
        } else {
            AppCommon.getInstance(ResturantListByCategoryActivity.this).clearNonTouchableFlags(ResturantListByCategoryActivity.this);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppCommon.INTENT_FOR_MAP_DISTANCE) {
            if (resultCode == Activity.RESULT_OK) {
                this.finish();
            }
        } else if (requestCode == AppCommon.INTENT_FOR_RESTURANT_DETAIL) {
            if (resultCode == Activity.RESULT_OK) {
                this.finish();
            }
        } else if (requestCode == AppCommon.FILTER_INTENT) {
            if (resultCode == Activity.RESULT_OK) {
                filterObject = gson.fromJson(data.getExtras().getString("filterObject"), FilterObject.class);
                saveDefaultResturantList();
                resturantObjectArrayList.clear();
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.VISIBLE);
                callApi(1);
            }
        }
    }

    public void hideKeyBoard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @OnClick(R.id.googleMapClick)
    public void googleMapClick(View view) {
        googleMapClick();
    }

    @OnClick(R.id.wazeClick)
    public void wazeClick(View view) {
        wazeClick();
    }


    @OnClick(R.id.footerBannerImage)
    public void bannerImageClick(View v) {
        if (v.getTag() != null) {
            Intent webViewIntent = new Intent(this, WebViewActivity.class);
            webViewIntent.putExtra("url", v.getTag().toString());
            startActivity(webViewIntent);
        }
//        else {
//            Intent webViewIntent = new Intent(this, WebViewActivity.class);
//            webViewIntent.putExtra("url", getResources().getString(R.string.jungle_box_link));
//            startActivity(webViewIntent);
//        }
    }

    public void saveDataToLocalDataBase(ResturantObject object) {
        DbHelper dbHelper = DbHelper.getInstance(this);
        if (dbHelper.isResturantExist(object.getRestID())) {
            dbHelper.updateRest(object);
        } else {
            dbHelper.insertResturant(object);
        }
    }

    public void getDataFromLocalDataBase() {
        if (resturantObjectArrayList.size() == 0) {
            DbHelper dbHelper = DbHelper.getInstance(this);
            ArrayList<ResturantObject> restObjArrayList = dbHelper.getResturantsListForCategory(headerTextView.getText().toString().trim());
            for (ResturantObject object : restObjArrayList) {
                resturantObjectArrayList.add(object);
            }
            adapter.notifyDataSetChanged();
            onMapReady(mMap);
        }
    }

    public void noResturantPopup(String title) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setCancelable(false);
        builder.setNegativeButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                if (resturantObjectArrayList.size() == 0) {
                    Intent backIntent = new Intent();
                    setResult(RESULT_OK, backIntent);
                    ResturantListByCategoryActivity.this.finish();
                }
            }
        });
        builder.show();
    }
}
