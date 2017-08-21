package APIEntity;

import com.google.gson.annotations.SerializedName;


public class ForgotPasswordEntity {
    @SerializedName("emailAddress")
    String emailAddress;

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public ForgotPasswordEntity(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}
