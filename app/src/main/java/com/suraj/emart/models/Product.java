package com.suraj.emart.models;

import com.hishd.tinycart.model.Item;
import java.io.Serializable;
import java.math.BigDecimal;

public class Product implements Item, Serializable
{
    private String name, image, status, price, productDesc;
    private int id,stock;
    private int quantity;


    public Product(String name, String image, String status, String price, String productDesc, int stock, int id) {
        this.name = name;
        this.image = image;
        this.status = status;
        this.price = price;
        this.productDesc = productDesc;
        this.stock = stock;
        this.id = id;
    }

    public Product(String name, String image, String status, String price, String productDesc, int stock, int id,int quantity) {
        this.name = name;
        this.image = image;
        this.status = status;
        this.price = price;
        this.productDesc = productDesc;
        this.stock = stock;
        this.id = id;
        this.quantity = quantity;
    }

    public Product(String name, String image, String status, String price, String productDesc, int stock) {
        this.name = name;
        this.image = image;
        this.status = status;
        this.price = price;
        this.productDesc = productDesc;
        this.stock = stock;
    }
    public Product(){}

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPrice() {
        return price;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public BigDecimal getItemPrice() {
        return BigDecimal.valueOf(Double.parseDouble(price));
    }

    @Override
    public String getItemName() {
        return name;
    }
}

