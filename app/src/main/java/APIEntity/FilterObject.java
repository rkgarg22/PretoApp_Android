package APIEntity;

import com.google.gson.annotations.SerializedName;


public class FilterObject {
    @SerializedName("isOpen")
    String isOpen = "";

    @SerializedName("fromPrice")
    String fromPrice = "";

    @SerializedName("toPrice")
    String toPrice = "";

    @SerializedName("categories")
    String categories = "";

    @SerializedName("typeOfFood")
    String typeOfFood = "";

    @SerializedName("isMostLikedOne")
    String isMostLikedOne = "";

    @SerializedName("isDeliveryOn")
    String isDeliveryOn = "";

    @SerializedName("toTakeAway")
    String toTakeAway = "";

    @SerializedName("paymentMethods")
    String paymentMethods = "";

    public FilterObject() {
    }

    public FilterObject(String isOpen, String fromPrice, String toPrice, String categories, String typeOfFood, String isMostLikedOne, String isDeliveryOn, String toTakeAway, String paymentMethods) {
        this.isOpen = isOpen;
        this.fromPrice = fromPrice;
        this.toPrice = toPrice;
        this.categories = categories;
        this.typeOfFood = typeOfFood;
        this.isMostLikedOne = isMostLikedOne;
        this.isDeliveryOn = isDeliveryOn;
        this.toTakeAway = toTakeAway;
        this.paymentMethods = paymentMethods;
    }



    public String getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(String isOpen) {
        this.isOpen = isOpen;
    }

    public String getFromPrice() {
        return fromPrice;
    }

    public void setFromPrice(String fromPrice) {
        this.fromPrice = fromPrice;
    }

    public String getToPrice() {
        return toPrice;
    }

    public void setToPrice(String toPrice) {
        this.toPrice = toPrice;
    }

    public String getCategories() {
        return categories;
    }

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getTypeOfFood() {
        return typeOfFood;
    }

    public void setTypeOfFood(String typeOfFood) {
        this.typeOfFood = typeOfFood;
    }

    public String getIsMostLikedOne() {
        return isMostLikedOne;
    }

    public void setIsMostLikedOne(String isMostLikedOne) {
        this.isMostLikedOne = isMostLikedOne;
    }

    public String getIsDeliveryOn() {
        return isDeliveryOn;
    }

    public void setIsDeliveryOn(String isDeliveryOn) {
        this.isDeliveryOn = isDeliveryOn;
    }

    public String getToTakeAway() {
        return toTakeAway;
    }

    public void setToTakeAway(String toTakeAway) {
        this.toTakeAway = toTakeAway;
    }

    public String getPaymentMethods() {
        return paymentMethods;
    }

    public void setPaymentMethods(String paymentMethods) {
        this.paymentMethods = paymentMethods;
    }
}
