package pl.edu.pwr.views.application;

import java.util.Scanner;

public class ApplicationView {

    public static String initializeIndex() {
        System.out.println("Podaj nazwe uzytkownika: ");
        Scanner scanner = new Scanner(System.in);
        return scanner.next();
    }

    public int adminMenu() {
        System.out.println("MENU: Wybierz sposrod podanych opcji...");
        System.out.println("1 => Sprawdz nowe zlecenia");
        System.out.println("2 => Zweryfikuj zlecenia");
        System.out.println("3 => Wyswietl aktualne zlecenia");
        System.out.println("4 => Wyswietl zrealizowane zlecen");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    public int clientMenu() {
        System.out.println("MENU: Wybierz sposrod podanych opcji...");
        System.out.println("1 => Utorz nowe zlecenie");
        System.out.println("2 => Dokonaj platnosc za wybrane zlecenie");
        System.out.println("3 => Wyswietl swoje zlecenie");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    public int driverMenu() {
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
