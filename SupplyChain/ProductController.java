package com.example.supplychain;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ProductController implements Initializable {

    @FXML
    private TableView<ProductModel> productTableView;

    @FXML
    private TableColumn<ProductModel, Integer> productID;

    @FXML
    private TableColumn<ProductModel, String> productName;

    @FXML
    private TableColumn<ProductModel, Integer> productPrice;

    @FXML
    private TableColumn<ProductModel, String> productAction;

    @FXML
    private TextField keywordTextField;

    @FXML
    Button logoutButton;

    @FXML
    Button loginButton;
    public int size;

    Button[] button;
    ObservableList<ProductModel> productModelObservableList = FXCollections.observableArrayList();


    String query = "Select productID, ProductName, price FROM product";
    ResultSet res = Main.connection.executeQuery(query);
    //when main java runs and load productpage.fxml this initialize execute code within it.
    @Override
    public void initialize(URL url, ResourceBundle resource){

//        if(!Main.emailId.equals("")){
//            loginButton.setOpacity(0);
//
//            //seting the label in header.fxml as emailid
//
////            email.setText(Main.username);
//
//        }

        size = 0;

//but notice that you'll only get correct ResultSet size after end of the while loop
        while(true)
        {
            try {
                if (!res.next()) break;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            //do your other per row stuff
            size = size + 1;
        }//end while

        res = Main.connection.executeQuery(query);

        button = new Button[size];

        for(int j = 0; j < size; j++){
            button[j] = new Button();
            button[j].setOnAction(this::handleButtonAction);
        }


        int i = 0;
        while(true){
            try {
                if (!res.next()) break;
                Integer resproductID = res.getInt("productID");
                String resName = res.getString("ProductName");
                Integer resPrice = res.getInt("price");

                //populating the observable list.
                productModelObservableList.add(new ProductModel(resproductID, resName, resPrice, button[i++]));

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }


        productID.setCellValueFactory(new PropertyValueFactory<>("productID"));
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        productAction.setCellValueFactory(new PropertyValueFactory<>("button"));

        productTableView.setItems(productModelObservableList);

        //Initial filtered list
        FilteredList<ProductModel> filteredData = new FilteredList<>(productModelObservableList, b -> true);

        keywordTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(productModel -> {

                //if no search value then display all records or whatever records it currently have. no changes/
                if(newValue.isEmpty() || newValue.isBlank() || newValue == null){

                    return true;
                }

                String searchkeyword = newValue.toLowerCase();

                if(productModel.getName().toLowerCase().indexOf(searchkeyword) > -1){
                    return true;
                }else
                    return false;

            });
        });

        SortedList<ProductModel> sortedData = new SortedList<>(filteredData);

        sortedData.comparatorProperty().bind(productTableView.comparatorProperty());

        productTableView.setItems(sortedData);

    }

    private void handleButtonAction(ActionEvent event) {

        for(int i = 0; i < size; i++){
            if(event.getSource() == button[i]){
                if (Main.emailId.equals("")) {

                    Dialog<String> dialog = new Dialog<>();
                    dialog.setTitle("Login");
                    ButtonType type = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);

                    dialog.setContentText("Try Logining In!!");

                    dialog.getDialogPane().getButtonTypes().add(type);
                    dialog.showAndWait();

                } else {


                    try {
                        Order place = new Order();
                        String id = String.valueOf(i + 1);
                        place.placeOrder(i + 1);
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("You clicked buy button");
                }

            }
        }
    }

    public void login(MouseEvent event) throws IOException {

        if(Main.emailId.equals("")){
            AnchorPane loginpage = FXMLLoader.load((getClass().getResource("LoginPage.fxml")));
            Main.root.getChildren().add(loginpage);
        }else{
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("Logged Out");
            ButtonType type = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);

            dialog.setContentText("Already LOGGED IN...!!!");

            dialog.getDialogPane().getButtonTypes().add(type);
            dialog.showAndWait();
        }


    }

    public void logout(MouseEvent event) throws IOException {

        if(!Main.emailId.equals("")){
            Main.emailId = "";
            Product product = new Product();
            //clearing otherpages
            Main.root.getChildren().clear();
            Main.root.getChildren().add(product.root);

            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("Logged Out");
            ButtonType type = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);

            dialog.setContentText("SUCCESSFULLY LOGGED OUT !!");

            dialog.getDialogPane().getButtonTypes().add(type);
            dialog.showAndWait();


        }else{

            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("Logged Out");
            ButtonType type = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);

            dialog.setContentText("Already LOGGED OUT !!");

            dialog.getDialogPane().getButtonTypes().add(type);
            dialog.showAndWait();

        }


    }

}
