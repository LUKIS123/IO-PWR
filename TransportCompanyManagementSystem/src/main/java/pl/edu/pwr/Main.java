package pl.edu.pwr;

import pl.edu.pwr.controllers.ApplicationController;
import pl.edu.pwr.utility.DatabaseConnectionSettings;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        System.setProperty("console.encoding", "UTF-8");
        System.out.println("Łukasz");
//        ApplicationController app = new ApplicationController();
//        app.runApp();


        try (Connection connection = DriverManager.getConnection(DatabaseConnectionSettings.databaseUrl, DatabaseConnectionSettings.user, DatabaseConnectionSettings.password)) {
            System.out.println("Connected to the PostgreSQL server successfully.");

            String selectQuery = "SELECT * FROM accounts";
            PreparedStatement preparedStatement = connection.prepareStatement(selectQuery);

            List<String[]> dataList = new ArrayList<>();

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String column1Value = resultSet.getString("nick");
                String column2Value = resultSet.getString("password");

                // Create an array to store the data
                String[] dataRow = {column1Value, column2Value};

                // Add the array to the list
                dataList.add(dataRow);
            }

            // Print the extracted data
            for (String[] dataRow : dataList) {
                System.out.println("Column1: " + dataRow[0] + ", Column2: " + dataRow[1]);
            }

        } catch (SQLException e) {
            System.out.println("Connection failed. Check the stack trace for more details.");
            e.printStackTrace();
        }


    }
}