package com.example.supermarket.Controller;

import com.example.supermarket.Model.Product;
import com.example.supermarket.Model.Sale;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddSaleProductController implements Initializable {

    @FXML
    private ComboBox<String> all_sales;

    @FXML
    private Text selected_products;

    Sale sale;

    int[] ids;


    @FXML
    void add(ActionEvent event) throws IOException {
        if(all_sales.getValue() != null){
            int product_id = ids[all_sales.getSelectionModel().getSelectedIndex()];

            Sale.addProduct(product_id);
//
            RouteController.sale();
        }
    }

    @FXML
    void cancel(ActionEvent event) throws IOException {
        RouteController.sale();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        selected_products.setText("Selected Products = " + String.valueOf(Sale.currentStock()));
        List<String> products = new ArrayList<String>();
        ResultSet allProducts = Product.all();
        int product_count = Product.count();
        try {

            ids = new int[product_count];
            int i = 0;
            while (allProducts.next()){
                ids[i++] = allProducts.getInt("id");
                products.add(allProducts.getString("category") + " - " + allProducts.getString("brand") + " - GHC" + allProducts.getString("price"));
            }
                ObservableList<String> productsObList = FXCollections.observableList(products);
                all_sales.getItems().clear();
                all_sales.setItems(productsObList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
