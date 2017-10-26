package com.tucan.pretoapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

import API.PretoAppService;
import API.ServiceGenerator;
import APIResponse.CommonStringResponse;
import APIResponse.GetResturantListResponse;
import APIResponse.ResturantObject;
import Adapter.ResturantAdapter;
import Database.DbHelper;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import infrastructure.AppCommon;
import infrastructure.GenericMapActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavouriteListActivity extends GenericMapActivity {


    @Bind(R.id.favoritesRecyclerView)
    RecyclerView favoritesRecyclerView;

    @Bind(R.id.progressbar)
    ProgressBar progressBar;

    @Bind(R.id.swipeContainer)
    SwipeRefreshLayout swipeContainer;

    @Bind(R.id.imageLayout)
    LinearLayout imageLayout;

    @Bind(R.id.buttonLayout)
    LinearLayout buttonLayout;

    @Bind(R.id.adLayout)
    RelativeLayout adLayout;

    @Bind(R.id.mapFragmentLayout)
    LinearLayout mapFragmentLayout;

    ResturantAdapter adapter;
    Call call;
    boolean isSwipeRefresh = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_list);
        ButterKnife.bind(this);

        markerClickLayout = (RelativeLayout) findViewById(R.id.markerClickLayout);
        bannerImageView = (SimpleDraweeView) findViewById(R.id.footerBannerImage);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        favoritesRecyclerView.setLayoutManager(mLinearLayoutManager);
        adapter = new ResturantAdapter(this, resturantObjectArrayList);
        favoritesRecyclerView.setAdapter(adapter);

        progressBar.setVisibility(View.VISIBLE);
        getFavouriteResturantList();

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isSwipeRefresh = true;
                getFavouriteResturantList();
            }
        });

        setMapView();
        showBanner();
    }

    @OnClick(R.id.backButtonClick)
    public void backButtonClick(View view) {
        this.finish();
    }

    @OnClick(R.id.listButton)
    public void listButtonClick() {
        imageLayout.setVisibility(View.VISIBLE);
        adLayout.setVisibility(View.VISIBLE);
        swipeContainer.setVisibility(View.VISIBLE);
        mapFragmentLayout.setVisibility(View.GONE);
    }

    @OnClick(R.id.mapButton)
    public void mapButtonClick() {
        resturantObjectArrayList.clear();
        imageLayout.setVisibility(View.GONE);
        adLayout.setVisibility(View.GONE);
        swipeContainer.setVisibility(View.GONE);
        mapFragmentLayout.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.cancelButton)
    public void cancelButtonClick(View view) {
        markerClickLayout.setVisibility(View.GONE);
    }

    private void getFavouriteResturantList() {
        AppCommon.getInstance(this).setNonTouchableFlags(this);
        if (AppCommon.getInstance(this).isConnectingToInternet(this)) {
            PretoAppService pretoAppService = ServiceGenerator.createService(PretoAppService.class);
            call = pretoAppService.getFavouriteResturantList(AppCommon.getInstance(this).getUserID(),
                    AppCommon.getInstance(this).getSelectedLanguage(),
                    Float.toString(AppCommon.getInstance(this).getUserLatitude()), Float.toString(AppCommon.getInstance(this).getUserLongitude()));
            call.enqueue(new Callback<GetResturantListResponse>() {
                @Override
                public void onResponse(Call call, Response response) {
                    AppCommon.getInstance(FavouriteListActivity.this).clearNonTouchableFlags(FavouriteListActivity.this);
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
                        AppCommon.getInstance(FavouriteListActivity.this).showDialog(FavouriteListActivity.this, listResponse.getError());
                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    getDataFromLocalDataBase();
                    swipeContainer.setRefreshing(false);
                    AppCommon.getInstance(FavouriteListActivity.this).clearNonTouchableFlags(FavouriteListActivity.this);
                    progressBar.setVisibility(View.GONE);
                    AppCommon.showDialog(FavouriteListActivity.this, getString(R.string.serverError));
                }
            });
        } else {
            getDataFromLocalDataBase();
            swipeContainer.setRefreshing(false);
            AppCommon.getInstance(FavouriteListActivity.this).clearNonTouchableFlags(FavouriteListActivity.this);
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
                    AppCommon.getInstance(FavouriteListActivity.this).clearNonTouchableFlags(FavouriteListActivity.this);
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
                        adapter.notifyDataSetChanged();
                    } else {
                        progressBar.setVisibility(View.GONE);
                        AppCommon.getInstance(FavouriteListActivity.this).showDialog(FavouriteListActivity.this, commonStringResponse.getError());
                    }
                }

                @Override
                public void onFailure(Call call, Throwable t) {
                    AppCommon.getInstance(FavouriteListActivity.this).clearNonTouchableFlags(FavouriteListActivity.this);
                    progressBar.setVisibility(View.GONE);
                    AppCommon.showDialog(FavouriteListActivity.this, getString(R.string.serverError));
                }
            });
        } else {
            AppCommon.getInstance(FavouriteListActivity.this).clearNonTouchableFlags(FavouriteListActivity.this);
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
    public void adLayoutClick(View v) {
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
            ArrayList<ResturantObject> restObjArrayList = dbHelper.getResturantsListForFavourite();
            for (ResturantObject object : restObjArrayList) {
                resturantObjectArrayList.add(object);
            }
            adapter.notifyDataSetChanged();
            onMapReady(mMap);
        }
    }
}
