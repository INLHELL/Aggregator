package com.genesys.raa.agg.prototype;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;


public class AggregatorAllInOne {

    public static void main(String[] args) throws IOException, SQLException {
        System.out.println("-------- Oracle JDBC Connection Testing ------");

        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");

        } catch (ClassNotFoundException e) {

            System.out.println("Where is your Oracle JDBC Driver?");
            e.printStackTrace();
            return;

        }

        System.out.println("Oracle JDBC Driver Registered!");

        Connection connection = null;

        try {
            //URL к базе состоит из протокола:подпротокола://[хоста]:[порта_СУБД]/[БД] и других_сведений
            String url = "jdbc:oracle:thin:@//192.168.27.97:1521/ORAPTS6";
            //Имя пользователя БД
            String name = "GIM_ETL_ORACLE_8XX";
            //Пароль
            String password = "genesys";

            //Создаём соединение
            connection = DriverManager.getConnection(url, name, password);

        } catch (SQLException e) {

            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;

        }

        if (connection != null) {
            System.out.println("You made it, take control your database now!");
        } else {
            System.out.println("Failed to make connection!");
        }



        /*
        CREATE AGGREGATE TABLE(S)
         */
        String aggSelectQuery = new String(Files.readAllBytes(Paths.get("src/test/resources/AGENT.sql")));
        String createTableQuery = "CREATE TABLE DT_SUBHR_AGENT AS " + aggSelectQuery.replaceAll("\\?", "-1");
        PreparedStatement createTableStatement = connection.prepareStatement(createTableQuery);
        createTableStatement.executeUpdate();

        /*
        POPULATE AGGREGATE TABLE
         */
        List<Integer> dateTimeKeys = Arrays.asList(1454137200, 1454136300, 1454135400, 1454134500, 1454133600);
        String insertSelectQuery = "INSERT INTO DT_SUBHR_AGENT " + aggSelectQuery;
        PreparedStatement insertSelectStatement = connection.prepareStatement(insertSelectQuery);

        for (Integer dateTimeKey : dateTimeKeys) {
            insertSelectStatement.setInt(1, dateTimeKey);
            try {
                insertSelectStatement.execute();
                System.out.println("Query was executed for date time key: " + dateTimeKey);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
