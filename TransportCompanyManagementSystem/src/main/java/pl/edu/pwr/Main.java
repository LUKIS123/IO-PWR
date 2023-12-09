package pl.edu.pwr;

import pl.edu.pwr.Repositories.UserRepository;
import pl.edu.pwr.application.AdministratorApplication;
import pl.edu.pwr.application.ClientApplication;
import pl.edu.pwr.application.DriverApplication;
import pl.edu.pwr.controllers.DriverController;
import pl.edu.pwr.controllers.JobController;
import pl.edu.pwr.models.User;
import pl.edu.pwr.views.application.InitializeIndex;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        System.setProperty("console.encoding", "UTF-8");


        //todo do testów query
        Test test = new Test();
        test.test6();

        UserRepository userRepository = new UserRepository();
        String username = InitializeIndex.initialize();
        User user = userRepository.getByUsername(username);

        // todo login in tylko po nazwie
        user = new User("m");
        if (user == null) {
            return;
        }

        // po użytkowniku param do switcha user.getUserType()


        JobController jobController = new JobController();
        DriverController driverController = new DriverController();

        switch (user.getUserType()) {
            case CLIENT -> new ClientApplication(user, jobController).index();
            case ADMINISTRATOR -> new AdministratorApplication(user, jobController, driverController).index();
            case DRIVER -> new DriverApplication(user, jobController, driverController).index();
            default -> {
                System.out.println("Niepoprawna nazwa!");
            }
        }


        /*
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
         */


    }
}

