package com.example.android.testlogin;

import android.widget.RadioButton;

import java.io.Serializable;

/**
 * Created by Soul on 9/5/2017.
 */

public class Information implements Serializable  {

    String p2Language;
    String p3Category;
    String p5Title;
    String p5StartTime;
    String p5EndTime;
    String p5Tagline;
    String p6Photo;
    String p6ThumbPhoto;
    String p7Activity;
    String p7Place;
    String p8LocationName;
    String p8Country;
    String p8StreetAddress;
    String p8UnitNo;
    String p8City;
    String p8State;
    String p8ZipCode;
    String p9Provide;
    String p10ExtraKnowledge;
    String p12Bio;
    String p13Requirement;
    String p13AddRequirement;
    String p14NoOfGuest;
    int p14EnterPrice;
    String p14TimeRequired;
    String p15GuestContext;
    String p15PackingList;
    RadioButton p16Agree1;
    RadioButton p16Agree2;


    public Information(String p2Language, String p3Category, String p5Title, String p5StartTime,
                       String p5EndTime, String p5Tagline, String p6Photo, String p6ThumbPhoto,
                       String p7Activity, String p7Place, String p8LocationName, String p8Country,
                       String p8StreetAddress, String p8UnitNo, String p8City, String p8State,
                       String p8ZipCode, String p9Provide, String p10ExtraKnowledge, String p12Bio,
                       String p13Requirement, String p13AddRequirement, String p14NoOfGuest,
                       int p14EnterPrice, String p14TimeRequired, String p15GuestContext,
                       String p15PackingList, RadioButton p16Agree1, RadioButton p16Agree2)

    {
        this.p2Language = p2Language;
        this.p3Category = p3Category;
        this.p5Title = p5Title;
        this.p5StartTime = p5StartTime;
        this.p5EndTime = p5EndTime;
        this.p5Tagline = p5Tagline;
        this.p6Photo = p6Photo;
        this.p6ThumbPhoto = p6ThumbPhoto;
        this.p7Activity = p7Activity;
        this.p7Place = p7Place;
        this.p8LocationName = p8LocationName;
        this.p8Country = p8Country;
        this.p8StreetAddress = p8StreetAddress;
        this.p8UnitNo = p8UnitNo;
        this.p8City = p8City;
        this.p8State = p8State;
        this.p8ZipCode = p8ZipCode;
        this.p9Provide = p9Provide;
        this.p10ExtraKnowledge = p10ExtraKnowledge;
        this.p12Bio = p12Bio;
        this.p13Requirement = p13Requirement;
        this.p13AddRequirement = p13AddRequirement;
        this.p14NoOfGuest = p14NoOfGuest;
        this.p14EnterPrice = p14EnterPrice;
        this.p14TimeRequired = p14TimeRequired;
        this.p15GuestContext = p15GuestContext;
        this.p15PackingList = p15PackingList;
        this.p16Agree1 = p16Agree1;
        this.p16Agree2 = p16Agree2;
    }

    public Information() {

    }

    public String getP2Language() {
        return p2Language;
    }

    public void setP2Language(String p2Language) {
        this.p2Language = p2Language;

    }

    public String getP3Category() {
        return p3Category;
    }

    public void setP3Category(String p3Category) {
        this.p3Category = p3Category;
    }

    public String getP5Title() {
        return p5Title;
    }

    public void setP5Title(String p5Title) {
        this.p5Title = p5Title;
    }

    public String getP5StartTime() {
        return p5StartTime;
    }

    public void setP5StartTime(String p5StartTime) {
        this.p5StartTime = p5StartTime;
    }

    public String getP5EndTime() {
        return p5EndTime;
    }

    public void setP5EndTime(String p5EndTime) {
        this.p5EndTime = p5EndTime;
    }

    public String getP5Tagline() {
        return p5Tagline;
    }

    public void setP5Tagline(String p5Tagline) {
        this.p5Tagline = p5Tagline;
    }

    public String getP6Photo() {
        return p6Photo;
    }

    public void setP6Photo(String p6Photo) {
        this.p6Photo = p6Photo;
    }


    public String getP6ThumbPhoto() {
        return p6Photo;
    }

    public void setP6ThumbPhoto(String p6ThumbPhoto) {
        this.p6ThumbPhoto = p6ThumbPhoto;
    }

    public String getP7Activity() {
        return p7Activity;
    }

    public void setP7Activity(String p7Activity) {
        this.p7Activity = p7Activity;
    }

    public String getP7Place() {
        return p7Place;
    }

    public void setP7Place(String p7Place) {
        this.p7Place = p7Place;
    }

    public String getP8LocationName() {
        return p8LocationName;
    }

    public void setP8LocationName(String p8LocationName) {
        this.p8LocationName = p8LocationName;
    }

    public String getP8Country() {
        return p8Country;
    }

    public void setP8Country(String p8Country) {
        this.p8Country = p8Country;
    }

    public String getP8StreetAddress() {
        return p8StreetAddress;
    }

    public void setP8StreetAddress(String p8StreetAddress) {
        this.p8StreetAddress = p8StreetAddress;
    }

    public String getP8UnitNo() {
        return p8UnitNo;
    }

    public void setP8UnitNo(String p8UnitNo) {
        this.p8UnitNo = p8UnitNo;
    }

    public String getP8City() {
        return p8City;
    }

    public void setP8City(String p8City) {
        this.p8City = p8City;
    }

    public String getP8State() {
        return p8State;
    }

    public void setP8State(String p8State) {
        this.p8State = p8State;
    }

    public String getP8ZipCode() {
        return p8ZipCode;
    }

    public void setP8ZipCode(String p8ZipCode) {
        this.p8ZipCode = p8ZipCode;
    }

    public String getP9Provide() {
        return p9Provide;
    }

    public void setP9Provide(String p9Provide) {
        this.p9Provide = p9Provide;
    }

    public String getP10ExtraKnowledge() {
        return p10ExtraKnowledge;
    }

    public void setP10ExtraKnowledge(String p10ExtraKnowledge) {
        this.p10ExtraKnowledge = p10ExtraKnowledge;
    }

    public String getP12Bio() {
        return p12Bio;
    }

    public void setP12Bio(String p12Bio) {
        this.p12Bio = p12Bio;
    }

    public String getP13Requirement() {
        return p13Requirement;
    }

    public void setP13Requirement(String p13Requirement) {
        this.p13Requirement = p13Requirement;
    }

    public String getP13AddRequirement() {
        return p13AddRequirement;
    }

    public void setP13AddRequirement(String p13AddRequirement) {
        this.p13AddRequirement = p13AddRequirement;
    }

    public String getP14NoOfGuest() {
        return p14NoOfGuest;
    }

    public void setP14NoOfGuest(String p14NoOfGuest) {
        this.p14NoOfGuest = p14NoOfGuest;
    }

    public int getP14EnterPrice() {
        return p14EnterPrice;
    }

    public void setP14EnterPrice(int p14EnterPrice) {
        this.p14EnterPrice = p14EnterPrice;
    }

    public String getP14TimeRequired() {
        return p14TimeRequired;
    }

    public void setP14TimeRequired(String p14TimeRequired) {
        this.p14TimeRequired = p14TimeRequired;
    }

    public String getP15GuestContext() {
        return p15GuestContext;
    }

    public void setP15GuestContext(String p15GuestContext) {
        this.p15GuestContext = p15GuestContext;
    }

    public String getP15PackingList() {
        return p15PackingList;
    }

    public void setP15PackingList(String p15PackingList) {
        this.p15PackingList = p15PackingList;
    }

    public RadioButton getP16Agree1() {
        return p16Agree1;
    }

    public void setP16Agree1(RadioButton p16Agree1) {
        this.p16Agree1 = p16Agree1;
    }

    public RadioButton getP16Agree2() {
        return p16Agree2;
    }

    public void setP16Agree2(RadioButton p16Agree2) {
        this.p16Agree2 = p16Agree2;
    }

}
