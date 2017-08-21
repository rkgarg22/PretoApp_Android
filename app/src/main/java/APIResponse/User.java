package APIResponse;

import com.google.gson.annotations.SerializedName;


public class User {
    @SerializedName("userID")
    String userID;

    @SerializedName("name")
    String name;

    @SerializedName("emailID")
    String emailAddress;

    @SerializedName("profilePicUrl")
    String imageUrl;


    public User(String userID, String name) {
        this.userID = userID;
        this.name = name;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
