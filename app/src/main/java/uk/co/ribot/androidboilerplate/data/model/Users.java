package uk.co.ribot.androidboilerplate.data.model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Users {

    @SerializedName("user")
    @Expose
    private User user;

    public Users(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "CreateUser{" +
                "user=" + user +
                '}';
    }
}
