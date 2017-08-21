package APIResponse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RegistrationResponse {
    @SerializedName("success")
    String success;

    @SerializedName("userInfo")
    User userEntity;

    @SerializedName("error")
    String error;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public User getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(User userEntity) {
        this.userEntity = userEntity;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }


}
