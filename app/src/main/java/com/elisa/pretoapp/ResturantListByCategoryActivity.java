package com.elisa.pretoapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import API.PretoAppService;
import API.ServiceGenerator;
import APIEntity.FilterObject;
import APIEntity.GetResturantListEntity;
import APIEntity.Login_Entity;
import APIResponse.GetResturantListResponse;
import APIResponse.LoginResponse;
import APIResponse.ResturantObject;
import Adapter.ResturantAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import infrastructure.AppCommon;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResturantListByCategoryActivity extends AppCompatActivity {


    @Bind(R.id.resturantRecyclerView)
    RecyclerView resturantRecyclerView;

    @Bind(R.id.categoryIconImage)
    ImageView categoryIconImage;

    @Bind(R.id.headerTextView)
    TextView headerTextView;

    @Bind(R.id.progressbar)
    ProgressBar progressBar;

    ResturantAdapter adapter;
    Call call;
    ArrayList<ResturantObject> resturantObjectArrayList = new ArrayList<ResturantObject>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resturant_list_bycategory);
        ButterKnife.bind(this);

        int tag = getIntent().getExtras().getInt("categorySelect");
        setImage(tag);
        progressBar.setVisibility(View.VISIBLE);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        resturantRecyclerView.setLayoutManager(mLinearLayoutManager);

        adapter = new ResturantAdapter(this,resturantObjectArrayList);
        resturantRecyclerView.setAdapter(adapter);
        getResturantList(tag);
    }

    public void setImage(int tag) {
        switch (tag) {
            case 11:
                categoryIconImage.setImageResource(R.drawable.breakfast);
                headerTextView.setText(getResources().getString(R.string.breakfast));
                break;
            case 13:
                categoryIconImage.setImageResource(R.drawable.lunch);
                headerTextView.setText(getResources().getString(R.string.lunch));
                break;
            case 15:
                categoryIconImage.setImageResource(R.drawable.dinner);
                headerTextView.setText(getResources().getString(R.string.dinner));
                break;
            case 19:
                categoryIconImage.setImageResource(R.drawable.custom_made);
                headerTextView.setText(getResources().getString(R.string.preorder));
                break;
            case 17:
                categoryIconImage.setImageResource(R.drawable.cravings);
                headerTextView.setText(getResources().getString(R.string.snacks));
                break;
            case 21:
                categoryIconImage.setImageResource(R.drawable.supplies);
                headerTextView.setText(getResources().getString(R.string.other));
                break;
            case 23:
                categoryIconImage.setImageResource(R.drawable.near_you);
                headerTextView.setText(getResources().getString(R.string.near_you));
                break;
        }
    }

    @OnClick(R.id.backButtonClick)
    public void backButtonClick(View view) {
        this.finish();
    }

    private void getResturantList(int catID) {
        AppCommon.getInstance(this).setNonTouchableFlags(this);
        if (AppCommon.getInstance(this).isConnectingToInternet(this)) {
            GetResturantListEntity listEntity = new GetResturantListEntity(AppCommon.getInstance(this).getUserID(),
                    Integer.toString(catID),
                    AppCommon.getInstance(this).getSelectedLanguage(), "1", "", "", new FilterObject());
            PretoAppService pretoAppService = ServiceGenerator.createService(PretoAppService.class);
            call = pretoAppService.getResturantList(listEntity);
            call.enqueue(new Callback<GetResturantListResponse>() {
                @Override
                public void onResponse(Call call, Response response) {
                    GetResturantListResponse listResponse = (GetResturantListResponse) response.body();
                    progressBar.setVisibility(View.GONE);
                    if (listResponse.getSuccess().equals("1")) {
                        for(ResturantObject object : listResponse.getResturantObjectList()){
                            resturantObjectArrayList.add(object);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        progressBar.setVisibility(View.GONE);
                        AppCommon.getInstance(ResturantListByCategoryActivity.this).showDialog(ResturantListByCategoryActivity.this, listResponse.getError());
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


}
