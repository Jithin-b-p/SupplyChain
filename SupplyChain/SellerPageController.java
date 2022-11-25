package com.example.supplychain;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;


public class SellerPageController {
    @FXML
    TextField name;

    @FXML
    TextField price;

    @FXML
    TextField email;

    @FXML
    Button logoutButton;
    @FXML
    public void add(MouseEvent event) throws SQLException {

        //productid is given by the program it must be unique we fetch the tast productid from database
        ResultSet res = Main.connection.executeQuery("Select max(productID) from product");
        int productID = 0;
        if(res.next()){

            //after the execution of query the column name is of max(productID) with only the maximum id value.
            productID = res.getInt("max(productID)") + 1;

        }

        String query = String.format("Insert into product values(%s, '%s', %s, '%s')" , productID, name.getText(), price.getText(), email.getText());

        int response = Main.connection.executeUpdate(query);
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Product Add");
        ButtonType type = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        if(response > 0){

            dialog.setContentText("Product added successfully");

        }else{

            dialog.setContentText("Product cannot be added");

        }

        dialog.getDialogPane().getButtonTypes().add(type);
        dialog.showAndWait();

    }

    public void logout(MouseEvent event) throws IOException {

        if(!Main.emailId.equals("")){
            Main.emailId = "";

            Product product = new Product();

            Main.root.getChildren().clear();
            Main.root.getChildren().add(product.root);
        }
    }
}
