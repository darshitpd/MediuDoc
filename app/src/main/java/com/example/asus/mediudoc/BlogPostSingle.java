package com.example.asus.mediudoc;

/**
 * Created by asus on 3/7/2018.
 */

public class BlogPostSingle {

    private String author,title,image,date;


    public BlogPostSingle() {
    }

    public BlogPostSingle(String author,String title,String image,String date) {
        this.author = author;
        this.title=title;
        this.image=image;
        this.date=date;
    }

    public String getDate() {

        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {

        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    public String getImage() {

        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {

        return title;
    }
}

