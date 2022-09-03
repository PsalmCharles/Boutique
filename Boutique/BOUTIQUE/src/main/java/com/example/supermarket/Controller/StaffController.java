package com.example.supermarket.Controller;

import com.example.supermarket.Model.Product;
import com.example.supermarket.Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.Objects;

public class StaffController {

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField re_password;

    @FXML
    private TextField staff_id;

    @FXML
    private TextField user_name;

    @FXML
    void cancel(ActionEvent event) throws IOException {
        RouteController.home();
    }

    @FXML
    void add(ActionEvent event) throws IOException {
        if(!password.getText().trim().equals("") && Objects.equals(password.getText(), re_password.getText())) {
            User.add(staff_id.getText(), user_name.getText(), password.getText());
//
                staff_id.setText("");
                user_name.setText("");
                password.setText("");
                re_password.setText("");

                RouteController.home();
        }
    }



}
