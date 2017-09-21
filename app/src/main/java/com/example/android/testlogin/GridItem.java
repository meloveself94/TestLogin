package com.example.android.testlogin;

import java.io.Serializable;


public class GridItem implements Serializable {

    String photo_url;
    String tripTagline;
    String price;
    String country;
    int mama;

    public GridItem( String imageView, String titleHere, String countryHere , String priceHere) {
        photo_url = imageView;
        tripTagline = titleHere;
        price = priceHere;
        country = countryHere;

    }
    public GridItem(){}

    public String getmImageView() {
        return photo_url;
    }

    public void setmImageView(String imageView) {
        photo_url = imageView;
    }

    public String getmTitleHere() {
        return tripTagline;
    }

    public void setmTitleHere(String titleHere) {
        tripTagline = titleHere;
    }

    public String getmPriceHere() {
        return price;
    }

    public void setmPriceHere(String priceHere) {
       price = priceHere;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


}
