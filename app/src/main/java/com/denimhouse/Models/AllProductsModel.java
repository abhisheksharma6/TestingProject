package com.denimhouse.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Android-Dev2 on 8/30/2017.
 */

public class AllProductsModel implements Parcelable {

    private String id, productName, categoryId, productPrice, discountType, discountvalue, discountPrice,
            productDescription, productImage, status, addedDate, todayDeal, discount, categoryName, parent_id,size;
    private int itemCount;
    private int shoppingCartItemCount;
    private Double subTotal, totalCount;

    public AllProductsModel(String id, String productName, String productDescription, String productPrice, String parent_id, String productImage, int itemCount, int shoppingCartItemCount) {
        this.id = id;
        this.itemCount = itemCount;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productImage = productImage;
        this.parent_id = parent_id;
        this.productPrice = productPrice;
        this.shoppingCartItemCount = shoppingCartItemCount;
    }
    public AllProductsModel(String id, String productName, String productDescription, String productPrice, String productImage, int itemCount, int shoppingCartItemCount,String size) {
        this.id = id;
        this.itemCount = itemCount;
        this.productName = productName;
        this.productDescription = productDescription;
        this.productImage = productImage;
        this.productPrice = productPrice;
        this.shoppingCartItemCount = shoppingCartItemCount;
        this.size = size;
    }

    public AllProductsModel(){

    }

    protected AllProductsModel(Parcel in) {
        id = in.readString();
        productName = in.readString();
        categoryId = in.readString();
        productPrice = in.readString();
        discountType = in.readString();
        discountvalue = in.readString();
        discountPrice = in.readString();
        productDescription = in.readString();
        productImage = in.readString();
        status = in.readString();
        addedDate = in.readString();
        todayDeal = in.readString();
        discount = in.readString();
        categoryName = in.readString();
        parent_id = in.readString();
        size = in.readString();
        itemCount = in.readInt();
        subTotal = in.readDouble();
        shoppingCartItemCount = in.readInt();
    }

    public static final Creator<AllProductsModel> CREATOR = new Creator<AllProductsModel>() {
        @Override
        public AllProductsModel createFromParcel(Parcel in) {
            return new AllProductsModel(in);
        }

        @Override
        public AllProductsModel[] newArray(int size) {
            return new AllProductsModel[size];
        }
    };


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public String getDiscountvalue() {
        return discountvalue;
    }

    public void setDiscountvalue(String discountvalue) {
        this.discountvalue = discountvalue;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAddedDate() {
        return addedDate;
    }

    public void setAddedDate(String addedDate) {
        this.addedDate = addedDate;
    }

    public String getTodayDeal() {
        return todayDeal;
    }

    public void setTodayDeal(String todayDeal) {
        this.todayDeal = todayDeal;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public int getShoppingCartItemCount() {
        return shoppingCartItemCount;
    }

    public void setShoppingCartItemCount(int shoppingCartItemCount) {
        this.shoppingCartItemCount = shoppingCartItemCount;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Double subTotal) {
        this.subTotal = subTotal;
    }

    public Double getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Double totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(productName);
        dest.writeString(categoryId);
        dest.writeString(productPrice);
        dest.writeString(discountType);
        dest.writeString(discountvalue);
        dest.writeString(discountPrice);
        dest.writeString(productDescription);
        dest.writeString(productImage);
        dest.writeString(status);
        dest.writeString(addedDate);
        dest.writeString(todayDeal);
        dest.writeString(discount);
        dest.writeString(categoryName);
        dest.writeString(parent_id);
        dest.writeString(size);
        dest.writeInt(itemCount);
        dest.writeDouble(subTotal);
        dest.writeInt(shoppingCartItemCount);
    }
}