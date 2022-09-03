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

public class Sale {

    public static int sale_id = 0;
    public static String customer = "";

    private int id;
    private String client;
    private int user_id;

    public Sale(int id) {
        this.id = id;

        this.find(id);
    }

    public Sale find(int id){
        String sql = null;
        try{
            Connection connection = DB.getConnection();
            if(connection != null){
                sql = "SELECT * FROM `sales` WHERE `id` = " + id ;

                ResultSet sale = connection.createStatement().executeQuery(sql);

                if (sale.next()){
                    this.client = sale.getString("client");
                    this.user_id = sale.getInt("user_id");
                }

            }
        }catch (Exception e){
            System.out.println(sql);
        }
        return null;
    }

    public String attendant(){
        String sql = null;
        try{
            Connection connection = DB.getConnection();
            if(connection != null){
                sql = "SELECT `username` FROM `users` WHERE `id` = '" + user_id + "'";

                ResultSet user = connection.createStatement().executeQuery(sql);

                if (user.next())
                    return user.getString("username");
            }
        }catch (Exception e){
            System.out.println(sql);
        }

        return null;
    }

    public String stock(){
        String sql = null;
        try{
            Connection connection = DB.getConnection();
            if(connection != null){
                sql = "SELECT COUNT(*) FROM `product_sales` WHERE `sale_id` = '" + this.id + "'";

                ResultSet sale = connection.createStatement().executeQuery(sql);

                if (sale.next())
                    return String.valueOf(sale.getInt(1));
            }
        }catch (Exception e){
            System.out.println(sql);
        }
        return null;
    }

    public String amount(){
        String sql = null;
        int sum = 0;
        try{
            Connection connection = DB.getConnection();
            if(connection != null){
                sql = "SELECT `product_id` FROM `product_sales` WHERE `sale_id` = '" + this.id + "'";

                ResultSet sold = connection.createStatement().executeQuery(sql);

                while (sold.next()) {

                    String _sql = "SELECT `price` FROM `products` WHERE `id` = " + sold.getInt(1) ;
                    ResultSet price = connection.createStatement().executeQuery(_sql);

                    if (price.next())
                        sum += price.getInt("price");



                }
            }
        }catch (Exception e){
            System.out.println(sql);
        }

        return String.valueOf(sum);
    }

    public String getClient(){
        return this.client;
    }

    public static void addProduct(int product_id){
        String sql = null;
        try{
            Connection connection = DB.getConnection();
            if(connection != null){
                sql = "INSERT INTO `product_sales` (`product_id`, `sale_id`) VALUES ('"+ product_id +"', '" + Sale.sale_id + "')";

                connection.createStatement().execute(sql);


            }
        }catch (Exception e){
            System.out.println(sql);
        }
    }

    public static void create(String client_name){
        String sql = null;
        try{
            Connection connection = DB.getConnection();
            if(connection != null){
                sql = "INSERT INTO `sales` (`client`, `user_id`) VALUES ('" + client_name +"', '" + User.id + "')";

                boolean sale = connection.createStatement().execute(sql);

                sql = "SELECT * FROM sales";

                ResultSet _sale = connection.createStatement().executeQuery(sql);

                if(_sale.last()) {
                    sale_id = _sale.getInt("id");
                    Sale.customer = client_name;
                }

            }
        }catch (Exception e){
            System.out.println(sql);
        }

    }

    public static ResultSet all(){
        String sql = null;
        try{
            Connection connection = DB.getConnection();
            if(connection != null){
                sql = "SELECT * FROM `sales`";

                ResultSet sales = connection.createStatement().executeQuery(sql);

                return sales;

            }
        }catch (Exception e){
            System.out.println(sql);
        }
            return null;
    }

    public static int currentStock(){
        String sql = null;
        try{
            Connection connection = DB.getConnection();
            if(connection != null){
                sql = "SELECT COUNT(*) FROM `product_sales` WHERE `sale_id` = '" + Sale.sale_id + "'";

                ResultSet sale = connection.createStatement().executeQuery(sql);

                if (sale.next())
                    return sale.getInt(1);
            }
        }catch (Exception e){
            System.out.println(sql);
        }
        return 0;
    }

    public static int count(){
        String sql = null;
        try{
            Connection connection = DB.getConnection();
            if(connection != null){
                sql = "SELECT COUNT(*) FROM `sales`";

                ResultSet count = connection.createStatement().executeQuery(sql);

                if (count.next())
                    return count.getInt(1);

            }
        }catch (Exception e){
            System.out.println(sql);
        }
        return 0;
    }

    public static int totalSaleAmount(){
        String sql = null;
        try{
            Connection connection = DB.getConnection();
            if(connection != null){
                sql = "SELECT * FROM `product_sales`";

                ResultSet soldProducts = connection.createStatement().executeQuery(sql);

                int sum = 0;
                while (soldProducts.next()){
                    String _sql = "SELECT `price` FROM `products` WHERE `id` = '" + soldProducts.getInt("product_id") + "' ";

                    try  {

                        ResultSet product_price = connection.createStatement().executeQuery(_sql);
                        while (product_price.next()) {
                            sum += product_price.getInt("price");
                        }
                    }catch (Exception e){
                        System.out.println("unable to calculate price inner");
                        e.printStackTrace();
                    }

                }
                return sum;

            }
        }catch (Exception e){
            System.out.println("unable to calculate price");
        }
        return 0;
    }

    public static void populateTable( TableView<ObservableList> table){

        try{
            /**********************************
             * TABLE COLUMN ADDED DYNAMICALLY *
             **********************************/
            String[] cols = new String[4];
            cols[0] = "Client";
            cols[1] = "Total Stock";
            cols[2] = "Amount";
            cols[3] = "Attendant";

            for(int i=0 ; i<cols.length; i++){
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
            Connection c ;
            c = DB.getConnection();
            //sale_SQL FOR SELECTING ALL OF CUSTOMER
            String sale_SQL = "SELECT `id` from sales";
            //ResultSet
            ResultSet sales = c.createStatement().executeQuery(sale_SQL);

            ObservableList<ObservableList> data = FXCollections.observableArrayList();

            while(sales.next()){
                //Iterate Row
                System.out.println();
                Sale sale = new Sale(sales.getInt("id"));

                ObservableList<String> row = FXCollections.observableArrayList();

                row.add(sale.getClient());
                row.add(sale.stock());
                row.add(sale.amount());
                row.add(sale.attendant());

                data.add(row);

            }

//            FINALLY ADDED TO TableView
            table.setItems(data);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }

    public static void destroy(){
        String sql = null;
        try{
            Connection connection = DB.getConnection();
            if(connection != null){
                sql = "DELETE FROM `sales` WHERE id = " + Sale.sale_id;

                connection.createStatement().execute(sql);

                Sale.sale_id = 0;
                Sale.customer = "";
            }
        }catch (Exception e){
            System.out.println(sql);
        }
    }

    public static int currentSaleAmount(){
        String sql = null;
        try{
            Connection connection = DB.getConnection();
            if(connection != null){
                sql = "SELECT * FROM `product_sales` WHERE `sale_id` = " + Sale.sale_id;

                ResultSet soldProducts = connection.createStatement().executeQuery(sql);

                int sum = 0;
                while (soldProducts.next()){
                    String _sql = "SELECT `price` FROM `products` WHERE `id` = '" + soldProducts.getInt("product_id") + "' ";

                    try  {

                        ResultSet product_price = connection.createStatement().executeQuery(_sql);
                        while (product_price.next()) {
                            sum += product_price.getInt("price");
                        }
                    }catch (Exception e){
                        System.out.println("unable to calculate price inner");
                        e.printStackTrace();
                    }

                }
                return sum;

            }
        }catch (Exception e){
            System.out.println("unable to calculate price");
        }
        return 0;
    }

}
