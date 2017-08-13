package com.elisa.pretoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import Adapter.ResturantAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FavouriteListActivity extends AppCompatActivity {


    @Bind(R.id.favoritesRecyclerView)
    RecyclerView favoritesRecyclerView;

    ResturantAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_list);
        ButterKnife.bind(this);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        favoritesRecyclerView.setLayoutManager(mLinearLayoutManager);

        adapter = new ResturantAdapter(this);
        favoritesRecyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.backButtonClick)
    public void backButtonClick(View view){
        this.finish();
    }
}
