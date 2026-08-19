package org.example;

import java.sql.*;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        String URL ="jdbc:mysql://localhost:3306/db2";
        String USER = "root";
        String PASSWORD = "Satya@2005";
        try {

            Connection con = DriverManager.getConnection(URL,USER,PASSWORD);
            PreparedStatement prt = con.prepareStatement("select * from student_data");
            //ResultSet rs = stmt.executeQuery("select * from student_data");

        } catch ( SQLException e) {
            throw new RuntimeException(e);
        }


    }
}