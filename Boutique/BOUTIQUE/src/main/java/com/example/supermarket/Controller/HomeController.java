package com.example.supermarket.Controller;

import com.example.supermarket.App;
import com.example.supermarket.Model.DB;
import com.example.supermarket.Model.Product;
import com.example.supermarket.Model.Sale;
import com.example.supermarket.Model.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class HomeController implements Initializable {


    @FXML
    private PasswordField new_password;

    @FXML
    private PasswordField old_password;

    @FXML
    private PasswordField password;

    @FXML
    private Text product_count;

    @FXML
    private PasswordField re_new_password;

    @FXML
    private Text sale_amount;

    @FXML
    private Text sale_count;

    @FXML
    private Text staff_count;

    @FXML
    private Text title;

    @FXML
    private TextField user_name;

    @FXML
    private Text username;

    @FXML
    private TableView<ObservableList> products_table;

    @FXML
    private TableView<ObservableList> sales_table;

    @FXML
    private TableView<ObservableList> staffs_table;





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sale_count.setText(String.valueOf(Sale.count()) + " Sale(s)");
//        sale_amount.setText(String.valueOf(Sale.saleAmount()));
        username.setText("Hi, " + User.username);
        product_count.setText(String.valueOf(Product.count()) + " Product(s)");
        staff_count.setText(String.valueOf(User.count()) + " Staff");
        sale_amount.setText("GHC " + String.valueOf(Sale.totalSaleAmount()));

        Sale.populateTable(sales_table);
        Product.populateTable(products_table);
        User.populateTable(staffs_table);

        title.setText("BOUTIQUE");

    }

    @FXML
    void addProduct(ActionEvent event) throws IOException {

        RouteController.addProduct();
    }

    @FXML
    void addSale(ActionEvent event) throws IOException {
        RouteController.sale();

    }

    @FXML
    void addStaff(ActionEvent event) throws IOException {
        RouteController.addStaff();
    }



    @FXML
    void logout(ActionEvent event) throws IOException {
        RouteController.logout();
    }



    @FXML
    void updDetails(ActionEvent event) throws IOException {
        System.out.println("upd Details");
        if (!user_name.getText().equals("") && !password.getText().equals("") && Objects.equals(password.getText(), User.password)){
            User.updateUserName(user_name.getText());
            RouteController.home();
        }else{
            user_name.setText("");
            password.setText("");
        }
    }

    @FXML
    void updPassword(ActionEvent event) throws IOException {
        if (!old_password.getText().equals("") && !re_new_password.getText().equals("") && Objects.equals(new_password.getText(), re_new_password.getText())){
            User.updatePassword(new_password.getText(), old_password.getText());
            RouteController.home();
        }else{
            old_password.setText("");
            re_new_password.setText("");
            new_password.setText("");
        }
    }


}



