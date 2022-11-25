package com.example.supplychain;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class Product {
    AnchorPane root;
    Product() throws IOException {
        root = FXMLLoader.load(getClass().getResource("ProductPage.fxml"));

    }
}
