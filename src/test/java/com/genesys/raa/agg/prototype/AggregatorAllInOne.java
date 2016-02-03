package com.genesys.raa.agg.prototype;

import com.genesys.raa.agg.definition.ColumnGroupType;
import com.genesys.raa.agg.definition.ColumnMetaData;
import freemarker.template.*;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.*;


public class AggregatorAllInOne {

    public static void main(String[] args) throws IOException, SQLException, TemplateException {
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
        Set<ColumnMetaData> columnMetaDatas = new HashSet<>();
        String aggSelectQuery = new String(Files.readAllBytes(Paths.get("src/test/resources/AGENT.sql")));
        String selectQuery = aggSelectQuery.replaceAll("\\?", "-1");
        PreparedStatement selectStatement = connection.prepareStatement(selectQuery);
        ResultSetMetaData metaData = selectStatement.getMetaData();
        for (int i = 1; i <= metaData.getColumnCount(); i++) {
            String columnName =  metaData.getColumnName(i);
            ColumnGroupType columnGroupType = ColumnGroupType.NONE;
            if(columnName.contains(ColumnGroupType.COUNT.toString())) {
                columnName = columnName.replace(ColumnGroupType.COUNT.toString(), "");
                columnGroupType = ColumnGroupType.COUNT;
            }
            if(columnName.contains(ColumnGroupType.SUM.toString())) {
                columnName = columnName.replace(ColumnGroupType.SUM.toString(), "");
                columnGroupType = ColumnGroupType.SUM;
            }
            if(columnName.contains(ColumnGroupType.GROUP_BY.toString())) {
                columnName = columnName.replace(ColumnGroupType.GROUP_BY.toString(), "");
                columnGroupType = ColumnGroupType.GROUP_BY;
            }
            boolean isIndexed = columnName.contains("$I");
            columnName = columnName.replace("$I", "");

            System.out.println(metaData.getColumnName(i));
            System.out.println(metaData.getColumnType(i));

            ColumnMetaData columnMetaData = new ColumnMetaData(i, columnName, metaData.getColumnLabel(i), metaData
                    .getColumnType(i), metaData.getColumnTypeName(i), columnGroupType, isIndexed);
            columnMetaDatas.add(columnMetaData);
        }

//        Definition definition = new Definition();


        // 1. Configure FreeMarker
        //
        // You should do this ONLY ONCE, when your application starts,
        // then reuse the same Configuration object elsewhere.

        Configuration cfg = new Configuration();

        // Where do we load the templates from:
//        cfg.setClassForTemplateLoading(AggregatorAllInOne.class, "test/java/resources");
        cfg.setDirectoryForTemplateLoading(new File("src/test/resources"));

        // Some other recommended settings:
        cfg.setIncompatibleImprovements(new Version(2, 3, 20));
        cfg.setDefaultEncoding("UTF-8");
        cfg.setLocale(Locale.US);
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);

        // 2. Proccess template(s)
        //
        // You will do this for several times in typical applications.

        // 2.1. Prepare the template input:

        Map<String, Object> input = new HashMap<String, Object>();

        input.put("date_time_key", "1000");
        input.put("group", "GRP_");
        input.put("sum", "SUM_");

        // 2.2. Get the template

        Template template = cfg.getTemplate("AGENT.ftl");

        // Write output to the console
        Writer consoleWriter = new OutputStreamWriter(System.out);
        template.process(input, consoleWriter);


        String createTableQuery = "CREATE TABLE DT_SUBHR_AGENT AS " + selectQuery;
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
