package APIEntity;

import com.google.gson.annotations.SerializedName;


public class Login_Entity {
    @SerializedName("emailID")
    String emailAddress;


    @SerializedName("firebaseTokenId")
    String deviceToken;

    @SerializedName("deviceType")
    String deviceType;



    public Login_Entity(String emailAddress, String deviceToken, String deviceType) {
        this.emailAddress = emailAddress;
        this.deviceToken = deviceToken;
        this.deviceType = deviceType;
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

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
