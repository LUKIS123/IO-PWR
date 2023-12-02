package pl.edu.pwr.views.application;

import java.util.Scanner;

public class Index {
    public String initialize() {
        System.out.println("Podaj nazwe uzytkownika: ");
        Scanner scanner = new Scanner(System.in);
        return scanner.next();
    }
}
