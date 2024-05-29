/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tests;

/**
 *
 * @author Lucas
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestConnection {
    public static void main(String[] args) {
        try {
            
            String url = "jdbc:mysql://localhost:3306/hotel_management";
            String user = "root";
            String password = "magnoliacasa11";

            
            Class.forName("com.mysql.cj.jdbc.Driver");

            
            Connection conn = DriverManager.getConnection(url, user, password);
            if (conn != null) {
                System.out.println("Conectado ao banco de dados!");
                conn.close(); 
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}

