package com.example.supermarket.Controller;

import com.example.supermarket.Model.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ProductController {

    @FXML
    private TextField brand;

    @FXML
    private TextField category;

    @FXML
    private TextField color;

    @FXML
    private TextField price;

    @FXML
    private TextField size;

    @FXML
    void cancel(ActionEvent event) throws IOException {
        RouteController.home();
    }

    @FXML
    void add(ActionEvent event) throws IOException {
        Product.add(category.getText(), brand.getText(), size.getText(), price.getText(), color.getText());

        category.setText("");
        brand.setText("");
        size.setText("");
        price.setText("");
        color.setText("");
    }

}
