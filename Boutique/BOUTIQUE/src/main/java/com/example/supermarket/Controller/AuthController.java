package com.example.supermarket.Controller;

import com.example.supermarket.App;
import com.example.supermarket.Model.DB;
import com.example.supermarket.Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class AuthController {

    @FXML
    private Text errorTxt;
    @FXML
    private Text noteTxt;

    @FXML
    private PasswordField password_textField;

    @FXML
    private TextField staff_id_textField;

    private  Stage _stage;


    public void login(ActionEvent actionEvent) throws IOException, SQLException {
        errorTxt.setText(null);
        noteTxt.setText("Loading...");
        Boolean user = User.auth(staff_id_textField.getText(), password_textField.getText(), errorTxt, noteTxt);

        if(user) {
            RouteController.home();
        }

    }
}
