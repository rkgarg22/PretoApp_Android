package APIEntity;

import com.google.gson.annotations.SerializedName;

public class GetResturantListEntity {
    @SerializedName("userID")
    String userID;

    @SerializedName("catID")
    String catID;

    @SerializedName("language")
    String language;

    @SerializedName("offset")
    String offset;

    @SerializedName("latitude")
    String latitude;

    @SerializedName("longitude")
    String longitude;

    @SerializedName("filters")
    FilterObject filterObj;

    @SerializedName("searchText")
    String searchText;

    public GetResturantListEntity(String userID, String catID, String language, String offset, String latitude, String longitude, FilterObject filterObj, String searchText) {
        this.userID = userID;
        this.catID = catID;
        this.language = language;
        this.offset = offset;
        this.latitude = latitude;
        this.longitude = longitude;
        this.filterObj = filterObj;
        this.searchText = searchText;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getCatID() {
        return catID;
    }

    public void setCatID(String catID) {
        this.catID = catID;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public FilterObject getFilterObj() {
        return filterObj;
    }

    public void setFilterObj(FilterObject filterObj) {
        this.filterObj = filterObj;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }
}
