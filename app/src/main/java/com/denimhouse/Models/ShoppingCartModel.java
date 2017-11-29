package com.denimhouse.Models;

/**
 * Created by Android-Dev2 on 8/17/2017.
 */

public class ShoppingCartModel {

    String title, type, productCode, price;
    int image, itemCount, subTotal;


    public ShoppingCartModel(String title, String type, String productCode, String price, int subTotal, int image, int itemCount) {
        this.title = title;
        this.type = type;
        this.productCode = productCode;
        this.price = price;
        this.subTotal = subTotal;
        this.image = image;
        this.itemCount = itemCount;
    }

    public int getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(int subTotal) {
        this.subTotal = subTotal;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

}