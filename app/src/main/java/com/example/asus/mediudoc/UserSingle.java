package com.example.asus.mediudoc;

/**
 * Created by asus on 3/6/2018.
 */

public class UserSingle {

    public String name;
    public String image;


    public UserSingle(){

    }

    public UserSingle(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}