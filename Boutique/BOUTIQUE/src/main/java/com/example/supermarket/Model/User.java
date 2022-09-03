package com.example.supermarket.Model;

import com.example.supermarket.App;
import com.example.supermarket.Controller.AuthController;
import com.example.supermarket.Controller.HomeController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;

public class User {
    public static int id;
    public static String username;
    public static String staff_id;
    public static String password;
    public static Boolean is_admin;

    public static boolean auth(String id, String password, Text errorText, Text noteTxt){

        Connection connection = null;
        String sql = null;
        
        try {
            connection = DB.getConnection();
        }catch(Exception e){
            noteTxt.setText(null);
            errorText.setText("Error: Unable to connect to DataBase!");
        }

        try{
            if(connection != null){
                sql = "SELECT * FROM `users` WHERE `staff_id` = '" + id + "' AND `password` = '" + password + "' ";

                ResultSet users = connection.createStatement().executeQuery(sql);

                if (users.next()) {
                    User.id = users.getInt(1);
                    User.staff_id = users.getString(2);
                    User.username = users.getString(3);
                    User.password = users.getString("password");
                    User.is_admin = users.getBoolean(4);
                    noteTxt.setText("authenticated");
                  return true;
                } else {
                    noteTxt.setText(null);
                    errorText.setText("Error: Invalid username or password");

                }
            }
        }catch (Exception e){
            System.out.println(sql);
            errorText.setText("Error: couldn't fetch from table");
        }

        return false;

    }

    public static int count(){
        String sql = null;
        try{
            Connection connection = DB.getConnection();
            if(connection != null){
                sql = "SELECT COUNT(*) FROM `users`";

                ResultSet count = connection.createStatement().executeQuery(sql);

                if (count.next())
                    return count.getInt(1);

            }
        }catch (Exception e){
            System.out.println("unable to count users");
        }
        return 0;
    }

    public static void populateTable( TableView<ObservableList> table){
        Connection c ;
        ObservableList<ObservableList> data = FXCollections.observableArrayList();
        try{
            c = DB.getConnection();
            //SQL FOR SELECTING ALL OF CUSTOMER
            String SQL = "SELECT `staff_id`, `username` from users";
            //ResultSet
            ResultSet rs = c.createStatement().executeQuery(SQL);

            /**********************************
             * TABLE COLUMN ADDED DYNAMICALLY *
             **********************************/
            String[] cols = new String[3];
            cols[0] = "#";
            cols[1] = "staff_id";
            cols[2] = "username";
            for(int i=0 ; i< cols.length; i++){
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(cols[i]);
                col.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ObservableList,String>, ObservableValue<String>>(){
                    public ObservableValue<String> call(TableColumn.CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

                table.getColumns().addAll(col);
//                System.out.println("Column ["+i+"] ");
            }

            /********************************
             * Data added to ObservableList *
             ********************************/
            int num = 1;
            while(rs.next()){
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                row.add(String.valueOf(num++));
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    //Iterate Column
                    row.add(rs.getString(i));
                }
//                System.out.println("Row [1] added "+row );
                data.add(row);

            }

            //FINALLY ADDED TO TableView
            table.setItems(data);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }

    public static void add(String staff_id, String username, String password){
        String sql = null;
        try{
            Connection connection = DB.getConnection();
            if(connection != null){
                sql = "INSERT INTO `users` (staff_id, username, password) VALUES ('"+ staff_id +"', '"+ username +"', '" + password + "' )";

                boolean result =  connection.createStatement().execute(sql);

            }
        }catch (Exception e){
            System.out.println(sql);
            e.printStackTrace();
        }
    }

    public static void updateUserName(String username){
        String sql = null;
        try{
            Connection connection = DB.getConnection();
            if(connection != null){
                sql = "UPDATE `users` SET `username` = '"+ username +"' WHERE `id` = " + User.id;

                connection.createStatement().execute(sql);

                User.username = username;


            }
        }catch (Exception e){
            System.out.println(sql);
        }
    }

    public static void updatePassword(String password, String oldPassword){
        if(Objects.equals(oldPassword, User.password)){
            String sql = null;
            try{
                Connection connection = DB.getConnection();
                if(connection != null){
                    sql = "UPDATE `users` SET `password` = '"+ password +"' WHERE `id` = " + User.id;;

                    connection.createStatement().execute(sql);

                    User.password = username;

                }
            }catch (Exception e){
                System.out.println(sql);
            }
        }
    }

}
