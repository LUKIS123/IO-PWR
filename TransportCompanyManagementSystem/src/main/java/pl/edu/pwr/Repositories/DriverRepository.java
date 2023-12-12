package pl.edu.pwr.Repositories;

import pl.edu.pwr.models.Driver;
import pl.edu.pwr.utility.DatabaseConnectionSettings;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// todo tutaj zapytania do SQL
public class DriverRepository implements RepositoryInterface<Driver> {
    @Override
    public List<Driver> getAll() throws SQLException {
        List<Driver> driverList = new ArrayList<>();

        String query = "SELECT nick, duringrest, duringexecutionoforder from Drivers;";
        ResultSet Data = DatabaseConnectionSettings.getDataFromDatabse(query);

        while (Data.next()) {
            String nick = Data.getString("nick");
            Boolean duringrest = Data.getBoolean("duringrest");
            Boolean duringexecutionoforder = Data.getBoolean("duringexecutionoforder");

            Driver d = new Driver(nick, duringrest, duringexecutionoforder);
            driverList.add(d);
        }

        //todo usuń to potem
        for (Driver d : driverList) {
            System.out.print(d.getUsername() + " ");
            System.out.print(d.isDuringRest() + " ");
            System.out.print(d.isDuringExecutionOfOrder() + " ");
            System.out.println();
        }

        return driverList;
    }

    @Override
    public Driver getById(int id) throws SQLException {
        String query = "SELECT nick, duringrest, duringexecutionoforder from Drivers where account_id = ";
        query = query + Integer.toString(id) + ";";

        ResultSet Data = DatabaseConnectionSettings.getDataFromDatabse(query);

        Data.next();
        String nick = Data.getString("nick");
        Boolean duringrest = Data.getBoolean("duringrest");
        Boolean duringexecutionoforder = Data.getBoolean("duringexecutionoforder");

        Driver driver1 = new Driver(nick, duringexecutionoforder, duringrest);

        //todo usuń to potem
        System.out.print(driver1.getUsername() + " ");
        System.out.print(driver1.isDuringRest() + " ");
        System.out.print(driver1.isDuringExecutionOfOrder() + " ");
        System.out.println();

        return driver1;
    }

    @Override
    public void insert(Driver model) throws SQLException {

        String query = "INSERT INTO Drivers (account_id, nick, duringRest, duringExecutionOfOrder)" +
                "VALUES (nextval('Drivers_ID_seq'), " + "\'" + model.getUsername() + "\'" + ","
                + model.isDuringRest() + ","
                + model.isDuringExecutionOfOrder() + ");";
        DatabaseConnectionSettings.executeQuery(query);
    }

    @Override
    public void update(int id, Driver model) throws SQLException {
        String query = String.format("UPDATE Drivers " +
                        "SET " +
                        "nick = '%s', " +
                        "duringrest = %b, " +
                        "duringexecutionoforder = %b " +
                        "WHERE account_id = %d;",
                model.getUsername(), model.isDuringRest(), model.isDuringExecutionOfOrder(), id);
        DatabaseConnectionSettings.executeQuery(query);
    }


    @Override
    public void delete(int id) throws SQLException {
        String query = String.format("DELETE FROM drivers WHERE account_id = %d;", id);
        DatabaseConnectionSettings.executeQuery(query);
    }

    public List<Driver> getAvailableDrivers() throws SQLException {
        List<Driver> driverList = new ArrayList<>();
        String query = "SELECT nick from Drivers\n" +
                "WHERE duringrest = FALSE\n" +
                "AND duringexecutionoforder = FALSE;";

        ResultSet Data = DatabaseConnectionSettings.getDataFromDatabse(query);

        while (Data.next()) {
            String nick = Data.getString("nick");
            Driver d = new Driver(nick);
            driverList.add(d);
        }

        //todo usuń to potem 2
        for (Driver d : driverList) {
            System.out.print(d.getUsername() + " ");
            System.out.println();
        }
        return driverList;
    }
}
