package pl.edu.pwr.views.driver;

import java.util.Scanner;

public class DriverIndex {
    public static int driverMenu() {
        System.out.println("MENU: Wybierz sposrod podanych opcji...");
        System.out.println("1 => Wyswietl aktualne zlecenie");
        System.out.println("2 => Zaakceptuj nowe zlecenie");
        System.out.println("3 => Zglos ukonczenie zlecenia");
        System.out.println("4 => Zglos przejscie na przerwe");
        System.out.println("5 => Zglos wznowienie pracy");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
}
