package APIEntity;

import com.google.gson.annotations.SerializedName;


public class ContactUs_Entity {
    @SerializedName("name")
    String name;

    @SerializedName("userID")
    String userID;

    @SerializedName("phone")
    String phone;

    @SerializedName("email")
    String emailID;

    @SerializedName("subject")
    String subject;

    @SerializedName("message")
    String message;


    public ContactUs_Entity(String name, String userID, String phone, String emailID, String subject, String message) {
        this.name = name;
        this.userID = userID;
        this.phone = phone;
        this.emailID = emailID;
        this.subject = subject;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
