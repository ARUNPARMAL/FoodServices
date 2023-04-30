package com.arunparmal.androidassignment.model;

import android.net.Uri;

import com.arunparmal.androidassignment.R;

public class RestrauntModel {
    String imageurl;
    String restaurant_name;
    String rating;
    String discount_percent;
    String[] category;
    String restaurant_description;

    public RestrauntModel(String imageurl, String restaurant_name, String rating, String discount_percent, String[] category, String restaurant_description) {
        this.imageurl = imageurl;
        this.restaurant_name = restaurant_name;
        this.rating = rating;
        this.discount_percent = discount_percent;
        this.category = category;
        this.restaurant_description = restaurant_description;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getRestaurant_name() {
        return restaurant_name;
    }

    public void setRestaurant_name(String restaurant_name) {
        this.restaurant_name = restaurant_name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getDiscount_percent() {
        return discount_percent;
    }

    public void setDiscount_percent(String discount_percent) {
        this.discount_percent = discount_percent;
    }

    public String[] getCategory() {
        return category;
    }

    public void setCategory(String[] category) {
        this.category = category;
    }

    public String getRestaurant_description() {
        return restaurant_description;
    }

    public void setRestaurant_description(String restaurant_description) {
        this.restaurant_description = restaurant_description;
    }
}

