package com.example.supermarket;

import com.example.supermarket.Controller.AuthController;
import com.example.supermarket.Controller.HomeController;
import com.example.supermarket.Controller.RouteController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException, SQLException {

        stage.setTitle("BOUTIQUE");
        stage.show();

        RouteController.setStage(stage);

        RouteController.auth();
    }

    public static void main(String[] args) {
        launch();
    }
}