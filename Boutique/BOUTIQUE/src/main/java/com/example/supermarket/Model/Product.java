package com.example.supermarket.Model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.sql.Connection;
import java.sql.ResultSet;

public class Product {



    public static ResultSet all(){
        String sql = null;
        try{
            Connection connection = DB.getConnection();
            if(connection != null){
                sql = "SELECT * FROM `products`";

                ResultSet sales = connection.createStatement().executeQuery(sql);

                return sales;

            }
        }catch (Exception e){
            System.out.println(sql);
        }
        return null;
    }

    public static int count(){
        String sql = null;
        try{
            Connection connection = DB.getConnection();
            if(connection != null){
                sql = "SELECT COUNT(*) FROM `products`";

                ResultSet count = connection.createStatement().executeQuery(sql);

                if (count.next())
                    return count.getInt(1);

            }
        }catch (Exception e){
            System.out.println("unable to count products");
        }
        return 0;
    }

    public static void populateTable( TableView<ObservableList> table){
        Connection c ;
        ObservableList<ObservableList> data = FXCollections.observableArrayList();
        try{
            c = DB.getConnection();
            //SQL FOR SELECTING ALL OF CUSTOMER
            String SQL = "SELECT `category`, `brand`, `size`, `color`, `price` from products";
            //ResultSet
            ResultSet rs = c.createStatement().executeQuery(SQL);

            /**********************************
             * TABLE COLUMN ADDED DYNAMICALLY *
             **********************************/
            String[] cols = new String[6];
            cols[0] = "#";
            cols[1] = "Category";
            cols[2] = "Brand";
            cols[3] = "Size";
            cols[4] = "Color";
            cols[5] = "Price";

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
                    row.add(rs.getString("category"));
                    row.add(rs.getString("brand"));
                    row.add(rs.getString("size"));
                    row.add(rs.getString("color"));
                    row.add(rs.getString("price"));
                    row.add(rs.getString("price"));
                   //Iterate Column

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

    public static void populateTable_sold( TableView<ObservableList> table){
        Connection c ;
        ObservableList<ObservableList> data = FXCollections.observableArrayList();
        try{


            /**********************************
             * TABLE COLUMN ADDED DYNAMICALLY *
             **********************************/
            String[] cols = new String[6];
            cols[0] = "#";
            cols[1] = "Category";
            cols[2] = "Brand";
            cols[3] = "Size";
            cols[4] = "Color";
            cols[5] = "Price";

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
            c = DB.getConnection();
            //SQL FOR SELECTING ALL OF CUSTOMER
            String SQL = "SELECT `product_id` FROM product_sales WHERE sale_id = " + Sale.sale_id;
            //ResultSet
            ResultSet sold_products = c.createStatement().executeQuery(SQL);
            int num = 1;
            while(sold_products.next()){
            ObservableList<String> row = FXCollections.observableArrayList();
                //Iterate Row
                String _sql = "SELECT * FROM products WHERE id = " + sold_products.getInt("product_id");
                ResultSet product = c.createStatement().executeQuery(_sql);
                if (product.next()){
                    row.add(String.valueOf(num++));
                    row.add(product.getString("category"));
                    row.add(product.getString("brand"));
                    row.add(product.getString("size"));
                    row.add(product.getString("color"));
                    row.add(product.getString("price"));
                }

                   //Iterate Column

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

    public static void add(String category, String brand, String size, String price, String color){
        String sql = null;
        try{
            Connection connection = DB.getConnection();
            if(connection != null){
                sql = "INSERT INTO `products` (category, brand, size, price, color) VALUES ('"+ category +"', '"+ brand +"', '" + size + "', '"+ price +"', '"+ color +"' )";

                connection.createStatement().execute(sql);

            }
        }catch (Exception e){
            System.out.println(sql);
        }
    }

//    public static void add(String category, String brand, String size, String price, String color) {
//
//    }
}
