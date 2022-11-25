package com.example.supplychain;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseConnection {
    String SQLURL = "jdbc:mysql://localhost:3306/supply?useSSL=false";
    String userName = "root";
    String password = "mysql";
    Connection con = null;

    DatabaseConnection(){
        try{
            con = DriverManager.getConnection(SQLURL, userName, password);
            if(con != null){
                System.out.println("OUR DATABASE CONNECTION IS SUCCESSFUL");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }


    }

    //function to sent queries to sql.

    public ResultSet executeQuery(String query){

        //store result from query.
        ResultSet res = null;
        try{
            Statement statement = con.createStatement();
            //this executeQuery is in java.util.sql
            res = statement.executeQuery(query);
            return res;
        }
        catch(Exception e){
            e.printStackTrace();
        }

        return res;

    }

    public int executeUpdate(String query){

        int res = 0;
        try{
            Statement statement = con.createStatement();
            //this executeUpdate is in java.util.sql
            res = statement.executeUpdate(query);
            return res;

        }catch(Exception e){

            e.printStackTrace();

        }
        return res;
    }

}
