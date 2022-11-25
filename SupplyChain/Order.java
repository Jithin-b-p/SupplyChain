package com.example.supplychain;

import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Order {

    void placeOrder(int productID) throws SQLException {

        ResultSet res = Main.connection.executeQuery("Select max(orderID) from orders");

        int orderID = 0;

        if(res.next()){
            //max(orderID) will be the result shown.
            orderID = res.getInt("max(orderID)") + 1;
        }

        String query = String.format("Insert Into orders values(%s, %s, '%s')", orderID, productID, Main.emailId);

        int response = Main.connection.executeUpdate(query);

        if(response > 0){
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("Order");
            ButtonType type = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);

            dialog.setContentText("Your Order is Placed..!!");

            dialog.getDialogPane().getButtonTypes().add(type);
            dialog.showAndWait();

            System.out.println("order is Placed");
        }

    }

}
