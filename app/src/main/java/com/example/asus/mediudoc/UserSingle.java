package com.example.asus.mediudoc;

/**
 * Created by asus on 3/6/2018.
 */

public class UserSingle {

    public String fullname;
    public String image;


    public UserSingle(){

    }

    public UserSingle(String fullname, String image) {
        this.fullname = fullname;
        this.image = image;
    }

    public String getName() {
        return fullname;
    }

    public void setName(String fullname) {
        this.fullname = fullname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}