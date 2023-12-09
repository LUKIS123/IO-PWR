package pl.edu.pwr.utility;

import java.sql.*;

public class DatabaseConnectionSettings {
    public static String databaseUrl = "jdbc:postgresql://flora.db.elephantsql.com:5432/yhfksepb";
    public static String password = "DBQ049gdClJc9NqcSLDbeSanTHt1bz97";
    public static String user = "yhfksepb";

    public static ResultSet getDataFromDatabse(String Query) {

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(databaseUrl, user, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(Query);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ResultSet resultSet = null;
        try {
            resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return resultSet;
    }

    public static void executeQuery(String Query) throws SQLException {
        try (Connection connection = DriverManager.getConnection(databaseUrl, user, password)) {
            try (Statement statement = connection.createStatement()) {
                statement.execute(Query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
/*
try (Connection connection = DriverManager.getConnection(jdbcUrl, username, password)) {
        // Example query
        String sqlQuery = "SELECT column1, column2 FROM your_table WHERE your_condition";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sqlQuery)) {
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
        List<String> resultData = new ArrayList<>();

        while (resultSet.next()) {
        // Assuming you have two columns (column1 and column2) of type String
        String column1Value = resultSet.getString("column1");
        String column2Value = resultSet.getString("column2");

        // Process the retrieved data (you can add it to a list, array, etc.)
        resultData.add(column1Value + ", " + column2Value);
        }

        // Now 'resultData' contains the retrieved data
        for (String data : resultData) {
        System.out.println(data);
        }
        }
        }
        } catch (SQLException e) {
        e.printStackTrace();
        }

 */