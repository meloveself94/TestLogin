package com.example.android.testlogin;

/**
 * Created by Soul on 10/15/2017.
 */

public class CardItem {

    String thumbPhoto;
    String title;
    String country;
    String pricePerGuest;

    public CardItem() {

    }

    public CardItem(String thumbPhoto, String title, String country, String pricePerGuest) {
        this.thumbPhoto = thumbPhoto;
        this.title = title;
        this.country = country;
        this.pricePerGuest = pricePerGuest;
    }

    public String getThumbPhoto() {
        return thumbPhoto;
    }

    public void setThumbPhoto(String thumbPhoto) {
        this.thumbPhoto = thumbPhoto;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPricePerGuest() {
        return pricePerGuest;
    }

    public void setPricePerGuest(String pricePerGuest) {
        this.pricePerGuest = pricePerGuest;
    }
}
