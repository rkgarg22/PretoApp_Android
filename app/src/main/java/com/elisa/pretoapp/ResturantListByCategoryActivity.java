package com.elisa.pretoapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import Adapter.ResturantAdapter;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ResturantListByCategoryActivity extends AppCompatActivity {


    @Bind(R.id.resturantRecyclerView)
    RecyclerView resturantRecyclerView;

    @Bind(R.id.categoryIconImage)
    ImageView categoryIconImage;

    @Bind(R.id.headerTextView)
    TextView headerTextView;

    ResturantAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resturant_list_bycategory);
        ButterKnife.bind(this);

        int tag = getIntent().getExtras().getInt("categorySelect");
        setImage(tag);

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        resturantRecyclerView.setLayoutManager(mLinearLayoutManager);

        adapter = new ResturantAdapter(this);
        resturantRecyclerView.setAdapter(adapter);
    }

    public void setImage(int tag) {
        switch (tag) {
            case 1:
                categoryIconImage.setImageResource(R.drawable.breakfast);
                headerTextView.setText(getResources().getString(R.string.breakfast));
                break;
            case 2:
                categoryIconImage.setImageResource(R.drawable.lunch);
                headerTextView.setText(getResources().getString(R.string.lunch));
                break;
            case 3:
                categoryIconImage.setImageResource(R.drawable.dinner);
                headerTextView.setText(getResources().getString(R.string.dinner));
                break;
            case 4:
                categoryIconImage.setImageResource(R.drawable.custom_made);
                headerTextView.setText(getResources().getString(R.string.preorder));
                break;
            case 5:
                categoryIconImage.setImageResource(R.drawable.cravings);
                headerTextView.setText(getResources().getString(R.string.snacks));
                break;
            case 6:
                categoryIconImage.setImageResource(R.drawable.supplies);
                headerTextView.setText(getResources().getString(R.string.other));
                break;
            case 7:
                categoryIconImage.setImageResource(R.drawable.near_you);
                headerTextView.setText(getResources().getString(R.string.near_you));
                break;
        }
    }

    @OnClick(R.id.backButtonClick)
    public void backButtonClick(View view) {
        this.finish();
    }
}
