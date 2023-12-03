package pl.edu.pwr.views.administrator;

import java.util.Scanner;

public class AdministratorIndex {
    public static int adminMenu() {
        System.out.println("MENU: Wybierz sposrod podanych opcji...");
        System.out.println("1 => Sprawdz nowe zlecenia");
        System.out.println("2 => Zweryfikuj zlecenia");
        System.out.println("3 => Wyswietl aktualne zlecenia");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
}
