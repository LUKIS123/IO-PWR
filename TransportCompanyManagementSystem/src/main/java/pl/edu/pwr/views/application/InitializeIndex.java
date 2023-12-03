package pl.edu.pwr.views.application;

import java.util.Scanner;

public class InitializeIndex {
    public static String initialize() {
        System.out.println("Podaj nazwe uzytkownika: ");
        Scanner scanner = new Scanner(System.in);
        return scanner.next();
    }
}
