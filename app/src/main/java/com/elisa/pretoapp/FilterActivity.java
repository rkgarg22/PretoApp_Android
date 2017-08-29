package com.elisa.pretoapp;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.StringTokenizer;

import APIEntity.FilterObject;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import infrastructure.AppCommon;

public class FilterActivity extends AppCompatActivity {

    @Bind(R.id.typeOFFoodOptionLayout)
    LinearLayout typeOFFoodOptionLayout;

    @Bind(R.id.openNowCheckBox)
    CheckBox openNowCheckBox;

    @Bind(R.id.fromEditText)
    EditText fromPriceEditText;

    @Bind(R.id.toEditText)
    EditText toPriceEditText;

    @Bind(R.id.breakfastCheckBox)
    CheckBox breakfastCheckBox;

    @Bind(R.id.LunchCheckBox)
    CheckBox LunchCheckBox;

    @Bind(R.id.dinnerCheckBox)
    CheckBox dinnerCheckBox;

    @Bind(R.id.snacksCheckBox)
    CheckBox snacksCheckBox;

    @Bind(R.id.preorderCheckBox)
    CheckBox preorderCheckBox;

    @Bind(R.id.suppliesCheckBox)
    CheckBox suppliesCheckBox;

    @Bind(R.id.typeOfFoodCheckBox)
    CheckBox typeOfFoodCheckBox;

    @Bind(R.id.meatCheckBox)
    CheckBox meatCheckBox;

    @Bind(R.id.signatureCheckBox)
    CheckBox signatureCheckBox;

    @Bind(R.id.seaFoodCheckBox)
    CheckBox seaFoodCheckBox;

    @Bind(R.id.healthyCheckBox)
    CheckBox healthyCheckBox;

    @Bind(R.id.vegetarianCheckBox)
    CheckBox vegetarianCheckBox;

    @Bind(R.id.hamburguersCheckBox)
    CheckBox hamburguersCheckBox;

    @Bind(R.id.fusionCheckBox)
    CheckBox fusionCheckBox;

    @Bind(R.id.typicalCheckBox)
    CheckBox typicalCheckBox;

    @Bind(R.id.fastFoodCheckBox)
    CheckBox fastFoodCheckBox;

    @Bind(R.id.peruvianCheckBox)
    CheckBox peruvianCheckBox;

    @Bind(R.id.internationalCheckBox)
    CheckBox internationalCheckBox;

    @Bind(R.id.mediterraneanCheckBox)
    CheckBox mediterraneanCheckBox;

    @Bind(R.id.noOFLikesCheckBox)
    CheckBox noOFLikesCheckBox;

    @Bind(R.id.deliveryCheckBox)
    CheckBox deliveryCheckBox;

    @Bind(R.id.togoCheckBox)
    CheckBox togoCheckBox;

    @Bind(R.id.cashCheckBox)
    CheckBox cashCheckBox;

    @Bind(R.id.sodexoCheckBox)
    CheckBox sodexoCheckBox;

    @Bind(R.id.debitCardCheckBox)
    CheckBox debitCardCheckBox;

    @Bind(R.id.bigPassCheckBox)
    CheckBox bigPassCheckBox;

    @Bind(R.id.creditCardCheckBox)
    CheckBox creditCardCheckBox;

    ArrayList<String> selectedPaymentMethod = new ArrayList<String>();
    ArrayList<String> selectedFoodType = new ArrayList<String>();
    ArrayList<String> selectedCategories = new ArrayList<String>();

    FilterObject filterObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        ButterKnife.bind(this);
        filterObject = new FilterObject();
    }

    @OnClick({R.id.backButtonClick, R.id.homeButtonClick})
    public void backButtonClick(View view) {
        this.finish();
    }

    @OnClick(R.id.typeOFFoodLayout)
    public void typeOFFoodClick(View view) {
        if (typeOFFoodOptionLayout.getVisibility() == View.VISIBLE) {
            typeOFFoodOptionLayout.setVisibility(View.GONE);
        } else {
            typeOFFoodOptionLayout.setVisibility(View.VISIBLE);
        }
    }

    @OnCheckedChanged({R.id.creditCardCheckBox, R.id.bigPassCheckBox, R.id.debitCardCheckBox, R.id.sodexoCheckBox, R.id.cashCheckBox})
    public void paymentMethod(CompoundButton compoundButton, boolean b) {
        CheckBox checkBox = (CheckBox) compoundButton;
        String tag = checkBox.getTag().toString();
        if (checkBox.isChecked()) {
            if (!selectedPaymentMethod.contains(tag)) {
                selectedPaymentMethod.add(tag);
            }
        } else {
            if (selectedPaymentMethod.contains(tag)) {
                selectedPaymentMethod.remove(tag);
            }
        }
    }

    @OnCheckedChanged({R.id.meatCheckBox, R.id.signatureCheckBox, R.id.seaFoodCheckBox, R.id.healthyCheckBox, R.id.vegetarianCheckBox,
            R.id.hamburguersCheckBox, R.id.fusionCheckBox, R.id.typicalCheckBox, R.id.fastFoodCheckBox, R.id.peruvianCheckBox, R.id.internationalCheckBox, R.id.mediterraneanCheckBox})
    public void foodTypeCheck(CompoundButton compoundButton, boolean b) {
        CheckBox checkBox = (CheckBox) compoundButton;
        String tag = checkBox.getTag().toString();
        if (checkBox.isChecked()) {
            if (!selectedFoodType.contains(tag)) {
                selectedFoodType.add(tag);
            }
        } else {
            if (selectedFoodType.contains(tag)) {
                selectedFoodType.remove(tag);
            }
        }
    }

    @OnCheckedChanged({R.id.breakfastCheckBox, R.id.LunchCheckBox, R.id.dinnerCheckBox, R.id.snacksCheckBox, R.id.preorderCheckBox,
            R.id.suppliesCheckBox})
    public void categoryCheck(CompoundButton compoundButton, boolean b) {
        CheckBox checkBox = (CheckBox) compoundButton;
        String tag = checkBox.getTag().toString();
        if (checkBox.isChecked()) {
            if (!selectedCategories.contains(tag)) {
                selectedCategories.add(tag);
            }
        } else {
            if (selectedCategories.contains(tag)) {
                selectedCategories.remove(tag);
            }
        }
    }

    @OnClick(R.id.acceptBtn)
    public void acceptBtnClick(View view) {
        if (openNowCheckBox.isChecked()) {
            filterObject.setIsOpen("1");
        }

        filterObject.setFromPrice(fromPriceEditText.getText().toString().trim());
        filterObject.setToPrice(toPriceEditText.getText().toString().trim());

        if (noOFLikesCheckBox.isChecked()) {
            filterObject.setIsMostLikedOne("1");
        }

        if (deliveryCheckBox.isChecked()) {
            filterObject.setIsDeliveryOn("1");
        }

        if (togoCheckBox.isChecked()) {
            filterObject.setIsPreOrder("1");
        }

        if (selectedCategories.size() > 0) {
            filterObject.setCategories(makeStringFromArray(selectedCategories));
        }

        if (selectedFoodType.size() > 0) {
            filterObject.setTypeOfFood(makeStringFromArray(selectedFoodType));
        }

        if (selectedPaymentMethod.size() > 0) {
            filterObject.setPaymentMethods(makeStringFromArray(selectedPaymentMethod));
        }

        Gson gson = new Gson();
        String objStr = gson.toJson(filterObject);

        if (getIntent().getExtras().getBoolean("isComingFromHome")) {
            Intent resturantIntent = new Intent(this, ResturantListByCategoryActivity.class);
            resturantIntent.putExtra("categorySelect", 1);
            resturantIntent.putExtra("searchText", "");
            resturantIntent.putExtra("filterObject", objStr);
            startActivityForResult(resturantIntent, AppCommon.RESTURANT_LIST_INTENT);
        } else {
            Intent i = new Intent();
            i.putExtra("filterObject", objStr);
            setResult(RESULT_OK, i);
            this.finish();
        }

    }

    public String makeStringFromArray(ArrayList<String> arrayList) {
        String str = "";
        for (String obj : arrayList) {
            str = str + obj + "|";
        }
        str = str.substring(0, str.length() - 1);
        return str;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppCommon.RESTURANT_LIST_INTENT) {
            if (resultCode == Activity.RESULT_OK) {
                this.finish();
            }
        }
    }
}
