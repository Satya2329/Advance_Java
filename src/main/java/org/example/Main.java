package org.example;
import java.sql.DriverManager;
import java.sql.SQLOutput;

public class Main {
    public static void main(String[] args) {
        String URL = "jdbc:mysql//localhost:3306/db2";
        String USER = "root";
        String PASSWORD = "Satya@2005";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Database connected sucessfully");
    }
}
