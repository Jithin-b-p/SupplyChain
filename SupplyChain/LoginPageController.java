package com.example.supplychain;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginPageController {
    @FXML
    TextField email;

    @FXML
    PasswordField password;

    @FXML
    public void backward(MouseEvent event) throws IOException {

        Product product = new Product();
        Main.root.getChildren().clear();
        Main.root.getChildren().add(product.root);

    }
    @FXML
    public void login(MouseEvent event) throws SQLException, IOException {

        String query = String.format("Select * from user where emailId = '%s' AND pass = '%s'", email.getText(), password.getText());
        ResultSet res = Main.connection.executeQuery(query);

        if(res.next()){
            String userType = res.getString("userType");

            Main.emailId = res.getString("emailId");
            Main.username = res.getString("userName");
            if(userType.equals("Buyer")){

                System.out.println("Logged in as Buyer");

                Product product = new Product();

                Main.root.getChildren().clear();
                Main.root.getChildren().addAll(product.root);

            }else{

                System.out.println("Logged in as Seller");
                AnchorPane sellerpage = FXMLLoader.load((getClass().getResource("SellerPage.fxml")));

                Main.root.getChildren().clear();
                Main.root.getChildren().add(sellerpage);

            }

        }else{

            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("Login");
            ButtonType type = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);

            dialog.setContentText("ALERT !! Failed LOGIN");

            dialog.getDialogPane().getButtonTypes().add(type);
            dialog.showAndWait();

        }

    }
}
