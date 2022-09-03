package com.example.supermarket.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DB {
    private static Connection connection;


    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        String DATABASE_URL = "jdbc:mysql://localhost:3306/";
        String DATABASE_NAME = "sm";
        String DATABASE_USERNAME = "root";
        String DATABASE_PASSWORD = "";


            Class.forName("com.mysql.jdbc.Driver");

            connection = DriverManager.getConnection(DATABASE_URL + DATABASE_NAME , DATABASE_USERNAME , DATABASE_PASSWORD);



        return connection;
    }

    public static void populateTable(String table_name, TableView<ObservableList> table){
        Connection c ;
        ObservableList<ObservableList> data = FXCollections.observableArrayList();
        try{
            c = new DB().getConnection();
            //SQL FOR SELECTING ALL OF CUSTOMER
            String SQL = "SELECT * from " + table_name;
            //ResultSet
            ResultSet rs = c.createStatement().executeQuery(SQL);

            /********************************
             * Data added to ObservableList *
             ********************************/
            while(rs.next()){
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<= rs.getMetaData().getColumnCount(); i++){
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                System.out.println("Row [1] added "+row );
                data.add(row);

            }

            // FINALLY ADDED TO TableView
            table.setItems(data);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }
}
