package com.example.supplychain;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


import java.io.IOException;
import java.sql.SQLException;

public class Main extends Application {

//    public-> so that we can access anywhere :static-> allocated memory only once for every object.
//    also we made databaseconneection public is that we can access from anywhere so that we dont want to establish
//    a new connection evertime
    public static DatabaseConnection connection;
    public static Group root;
    public static String emailId;
    public static String username;
    @Override

    public void start(Stage stage) throws IOException, SQLException {

        emailId = "";
        connection = new DatabaseConnection();

        root = new Group();

        Product product = new Product();

        root.getChildren().add(product.root);

        Scene scene = new Scene(root, 500, 500);
        stage.setTitle("supply");
        stage.setScene(scene);
        stage.show();

        stage.setOnCloseRequest(e ->{
            try {
                connection.con.close();
                System.out.println("Connection is closed..");
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }
}