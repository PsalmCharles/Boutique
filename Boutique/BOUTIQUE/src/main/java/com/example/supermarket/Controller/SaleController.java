package com.example.supermarket.Controller;

import com.example.supermarket.App;
import com.example.supermarket.Model.Product;
import com.example.supermarket.Model.Sale;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SaleController implements Initializable {

    Stage _stage;

    @FXML
    private Text client_error_text;

    @FXML
    private TextField client_name;

    @FXML
    private Text total_price;

    @FXML
    private Text total_stock;

    @FXML
    private TableView<ObservableList> sold_products_table;

    @FXML
    void addProduct(ActionEvent event) throws IOException {
        if (!client_name.getText().trim().equals("")) {
            if(Sale.sale_id == 0) {
                Sale.create(client_name.getText());
            }
            RouteController.addSaleProduct();
        }else{
            client_error_text.setText("Please Enter Client Name");
            client_name.setPromptText("Please Enter Client Name");
        }
    }

    @FXML
    void cancel(ActionEvent event) throws IOException {
        Sale.destroy();
        RouteController.home();
    }
    @FXML
    void save(ActionEvent event) throws IOException {
        RouteController.home();
        Sale.sale_id = 0;
        Sale.customer = "";
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(!Sale.customer.equals("")) {
            client_name.setText(Sale.customer);
            client_name.setEditable(false);
        }
        Product.populateTable_sold(sold_products_table);

        total_price.setText(String.valueOf(Sale.currentSaleAmount()));
        total_stock.setText(String.valueOf(Sale.currentStock()));
    }
}
