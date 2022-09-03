package com.example.supermarket.Controller;

import com.example.supermarket.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class RouteController {
    private static Stage stage;

    public static void setStage(Stage stage) {
        RouteController.stage = stage;
    }

    public static void auth() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 600);
        stage.setScene(scene);
    }

    public static void home() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("home.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 600);
        stage.setScene(scene);
    }

    public static void sale() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("sale/show.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 600);
        stage.setScene(scene);
    }

    public static void addSaleProduct() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("sale/addP.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 600);
        stage.setScene(scene);
    }

    public static void addProduct() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("product/add.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 600);
        stage.setScene(scene);
    }

    public static void addStaff() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("staff/add.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 600);
        stage.setScene(scene);
    }

    public static void logout() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 600);
        stage.setScene(scene);
    }



}
