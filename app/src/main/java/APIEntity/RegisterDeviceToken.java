package APIEntity;

import com.google.gson.annotations.SerializedName;

public class RegisterDeviceToken {
    @SerializedName("userId")
    String userID;

    @SerializedName("firebaseTokenId")
    String deviceToken;

    @SerializedName("deviceType")
    String deviceType;


    public RegisterDeviceToken(String userID, String deviceToken, String deviceType) {
        this.userID = userID;
        this.deviceToken = deviceToken;
        this.deviceType = deviceType;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }
}
