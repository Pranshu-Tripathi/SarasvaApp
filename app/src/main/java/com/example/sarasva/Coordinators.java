package com.example.sarasva;

public class Coordinators {

    String contact,enroll,imgUrl;

    public Coordinators(String contact, String enroll, String imgUrl) {
        this.contact = contact;
        this.enroll = enroll;
        this.imgUrl = imgUrl;
    }

    public Coordinators() {

    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setEnroll(String enroll) {
        this.enroll = enroll;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getContact() {
        return contact;
    }

    public String getEnroll() {
        return enroll;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
