package pl.edu.pwr.views.job;

import pl.edu.pwr.dtos.CreateJobDto;
import pl.edu.pwr.dtos.JobDriverAssignmentDto;
import pl.edu.pwr.models.Driver;
import pl.edu.pwr.models.Job;
import pl.edu.pwr.models.enums.CargoType;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class JobView {

    public static boolean tryMakePayment(Job job) {
        System.out.println("Koszt: " + job.getCost());
        System.out.println("Tak kliknij-> 1, Nie -> dowolny inny");
        Scanner scanner = new Scanner(System.in);

        return scanner.nextInt() == 1;
    }

    public void listAllNoAction(List<Job> jobs) {
        jobs.forEach(System.out::println);
    }

    public static int listAll(List<Job> jobs) {
        jobs.forEach(System.out::println);
        System.out.print("Wpisz ID: ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    public static int verifyView(Job job, Driver driver) {
        String info = "Job:\n" + job.toString() + "\nAssigned Driver:\n" + driver.toString();
        System.out.println(info);

        System.out.println("Wybierz sposrod podanych:");
        System.out.println("0 - OK,\n1 - Zmien kierowce,\n2 - Odrzuc zlecenie");

        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    public void displayJobInfo(Job job) {
        System.out.println(job.toString());
    }

    public void displayDriverJobInfo(Driver driver, Job job) {
    }

    public static CreateJobDto order() {
        return new CreateJobDto(
                CargoType.NONE,
                0,
                0
        );
    }

    public Optional<Integer> listOrders(List<Job> byUserId) {
        byUserId.forEach(System.out::println);
        return Optional.empty();
    }

    public int listOrdersWithDriverAssigment(List<JobDriverAssignmentDto> jobList) {
        System.out.println("Wybierz wspisujac ID zlecenia:");
        jobList.forEach(System.out::println);
        System.out.print("ID zlecenia: ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
}
