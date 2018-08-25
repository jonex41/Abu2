package com.john.www.abu.ShoppingStuffs.Models;

import android.support.annotation.NonNull;

import com.google.firebase.storage.StorageReference;

import java.io.Serializable;

public class ListOfShop implements Serializable {

    private String nameOfItem,costOfItem,minimum_for_delivery,imageUrl,quantity_demanded,total_quantity_demanded, description, sellerid;


    public ListOfShop() {
    }


    public String getSellerid() {
        return sellerid;
    }

    public void setSellerid(String sellerid) {
        this.sellerid = sellerid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTotal_quantity_demanded() {
        return total_quantity_demanded;
    }

    public void setTotal_quantity_demanded(String total_quantity_demanded) {
        this.total_quantity_demanded = total_quantity_demanded;
    }

    public String getQuantity_demanded() {
        return quantity_demanded;
    }

    public void setQuantity_demanded(String quantity_demanded) {
        this.quantity_demanded = quantity_demanded;
    }

    public String getNameOfItem() {
        return nameOfItem;
    }

    public void setNameOfItem(String nameOfItem) {
        this.nameOfItem = nameOfItem;
    }

    public String getCostOfItem() {
        return costOfItem;
    }

    public void setCostOfItem(String costOfItem) {
        this.costOfItem = costOfItem;
    }

    public String getMinimum_for_delivery() {
        return minimum_for_delivery;
    }

    public void setMinimum_for_delivery(String minimum_for_delivery) {
        this.minimum_for_delivery = minimum_for_delivery;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}




