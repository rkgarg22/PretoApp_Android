package APIEntity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by navneet on 11/30/2016.
 */
public class UserInformation_Entity {
    @SerializedName("name")
    String name;

    @SerializedName("emailID")
    String emailID;

    @SerializedName("password")
    String password;

    @SerializedName("facebookID")
    String facebookID;

    @SerializedName("profilePicUrl")
    String profilePicUrl;

    @SerializedName("firebaseTokenId")
    String firebaseTokenId;

    @SerializedName("deviceType")
    String deviceType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFacebookID() {
        return facebookID;
    }

    public void setFacebookID(String facebookID) {
        this.facebookID = facebookID;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }

    public String getFirebaseTokenId() {
        return firebaseTokenId;
    }

    public void setFirebaseTokenId(String firebaseTokenId) {
        this.firebaseTokenId = firebaseTokenId;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public UserInformation_Entity(String name, String emailID, String password, String facebookID, String profilePicUrl, String firebaseTokenId, String deviceType) {
        this.name = name;
        this.emailID = emailID;
        this.password = password;
        this.facebookID = facebookID;
        this.profilePicUrl = profilePicUrl;
        this.firebaseTokenId = firebaseTokenId;
        this.deviceType = deviceType;
    }
}
