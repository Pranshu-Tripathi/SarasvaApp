package com.example.sarasva;

public class BlogItem  {

    String id;
    String title;
    String description;
    String imgUrlBlog;
    String imgUrlUser;
    String Views;


    public BlogItem(String id,String title, String description, String imgUrlBlog, String imgUrlUser,String Views){
        this.id = id;
        this.title = title;
        this.description = description;
        this.imgUrlBlog = imgUrlBlog;
        this.imgUrlUser = imgUrlUser;
        this.Views = Views;
    }

    public String getViews() {
        return Views;
    }

    public void setViews(String views) {
        Views = views;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgUrlBlog() {
        return imgUrlBlog;
    }

    public void setImgUrlBlog(String imgUrlBlog) {
        this.imgUrlBlog = imgUrlBlog;
    }

    public String getImgUrlUser() {
        return imgUrlUser;
    }

    public void setImgUrlUser(String imgUrlUser) {
        this.imgUrlUser = imgUrlUser;
    }
}
