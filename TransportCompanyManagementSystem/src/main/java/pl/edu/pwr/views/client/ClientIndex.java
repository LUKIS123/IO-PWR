package pl.edu.pwr.views.client;

import java.util.Scanner;

public class ClientIndex {
    public static int clientMenu() {
        System.out.println("MENU: Wybierz sposrod podanych opcji...");
        System.out.println("1 => Utorz nowe zlecenie");
        System.out.println("2 => Dokonaj platnosc za wybrane zlecenie");
        System.out.println("3 => Wyswietl swoje zlecenie");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
}
