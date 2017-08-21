package APIEntity;

import com.google.gson.annotations.SerializedName;


public class Login_Entity {
    @SerializedName("emailID")
    String emailAddress;

    @SerializedName("password")
    String password;

    @SerializedName("firebaseTokenId")
    String deviceToken;

    @SerializedName("deviceType")
    String deviceType;



    public Login_Entity(String emailAddress, String password, String deviceToken, String deviceType) {
        this.emailAddress = emailAddress;
        this.password = password;
        this.deviceToken = deviceToken;
        this.deviceType = deviceType;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
