package APIEntity;

import com.google.gson.annotations.SerializedName;

public class ResetPasswordEntity {
    @SerializedName("userId")
    String userID;

    @SerializedName("oldPassword")
    String oldPassword;

    @SerializedName("newPassword")
    String newPassword;

    public ResetPasswordEntity(String userID, String oldPassword, String newPassword) {
        this.userID = userID;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
