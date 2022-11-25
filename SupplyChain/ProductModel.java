package com.example.supplychain;

import javafx.scene.control.Button;

public class ProductModel {
    Integer productID;
    String name;
    Integer price;

    Button button;

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    public ProductModel(Integer productID, String name, Integer price, Button button) {
        this.productID = productID;
        this.name = name;
        this.price = price;
        this.button = button;
        this.button.setText("BUY");

    }

    //Generating Setter
    public Integer getProductID() {
        return productID;
    }

    public String getName() {
        return name;
    }

    public Integer getPrice() {
        return price;
    }

    //Generating Getter
    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
