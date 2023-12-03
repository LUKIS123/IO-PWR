package pl.edu.pwr.views.application;

import java.util.Scanner;

public class AdministratorAppIndex {
    public static int adminMenu() {
        System.out.println("MENU: Wybierz sposrod podanych opcji...");
        System.out.println("1 => Sprawdz nowe zlecenia");
        System.out.println("2 => Zweryfikuj zlecenia");
        System.out.println("3 => Wyswietl aktualne zlecenia");
        System.out.println("4 => Wyswietl zrealizowane zlecen");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
}
