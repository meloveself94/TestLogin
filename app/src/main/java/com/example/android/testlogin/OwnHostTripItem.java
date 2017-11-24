package com.example.android.testlogin;

/**
 * Created by Soul on 11/22/2017.
 */

public class OwnHostTripItem {

    String thumbPhoto;
    String title;
    String country;
    String pricePerGuest;
    String maxGroupSize;

    public OwnHostTripItem() {

    }

    public OwnHostTripItem(String thumbPhoto, String title, String country, String pricePerGuest
                            , String maxGroupSize) {
        this.thumbPhoto = thumbPhoto;
        this.title = title;
        this.country = country;
        this.pricePerGuest = pricePerGuest;
        this.maxGroupSize = maxGroupSize;
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

    public String getMaxGroupSize() {
        return maxGroupSize;
    }

    public void setMaxGroupSize(String maxGroupSize) {
        this.maxGroupSize = maxGroupSize;
    }
}
