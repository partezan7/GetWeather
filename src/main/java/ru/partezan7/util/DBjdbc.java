package main.java.ru.partezan7.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBjdbc {
    private static final String URL = "jdbc:mysql://localhost:3306/getweather";
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "1234";


    private Connection connection;

    public DBjdbc (){
        try {
            connection= DriverManager.getConnection(URL, USERNAME, PASSWORD);
            if (!connection.isClosed()){
                System.out.println("Database connection established!");
            }

        } catch (SQLException e){
            System.out.println("Database connection failed!");;
        }
    }

    public Connection getConnection() {
        return connection;
    }
}



