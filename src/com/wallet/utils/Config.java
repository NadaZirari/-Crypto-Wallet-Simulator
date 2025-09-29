package com.wallet.utils;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Config {

	
	private static final String URL = "jdbc:postgresql://localhost:5432/crypto";
    private static final String USER = "postgres";  
    private static final String PASSWORD = "nada123";

    private static Connection connection;

    // Singleton : une seule connexion partagée
    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }
        return connection;
    }
}
