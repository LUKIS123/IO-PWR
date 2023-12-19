package pl.edu.pwr.views.driver;

import pl.edu.pwr.models.Driver;

import java.util.List;
import java.util.Scanner;

public class DriverView {
    public int listDrivers(List<Driver> driverList) {
        System.out.println("Wybierz wpisujac ID kierowcy:");
        driverList.forEach(System.out::println);
        System.out.print("Wpisz ID kierowcy: ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    public boolean makeAction(Driver driver) {
        return true;
    }

    public void displayDriverInfo(Driver driver) {
    }
}
