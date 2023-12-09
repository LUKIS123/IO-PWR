package pl.edu.pwr.models;

import pl.edu.pwr.models.enums.CargoType;
import pl.edu.pwr.models.enums.JobStatus;

public class Job {
    private int job_Id;
    private int driverId;
    private final CargoType cargoType;
    private JobStatus status;
    private final double distance;
    private final double weight;
    private final int clientId;
    public boolean isPaid = false;

    public Job(int job_Id, int driverId, String cargoType, String status, double distance, double weight, int clientId) {
        this.job_Id = job_Id;
        this.driverId = driverId;
        this.cargoType = mapTypeToEnum(cargoType);
        this.status = mapStatusToEnum(status);
        this.distance = distance;
        this.weight = weight;
        this.clientId = clientId;
    }

    public Job(int driverId, String cargoType, String status, double distance, double weight, int clientId) {
        this.driverId = driverId;
        this.cargoType = mapTypeToEnum(cargoType);
        this.status = mapStatusToEnum(status);
        this.distance = distance;
        this.weight = weight;
        this.clientId = clientId;
    }

    private JobStatus mapStatusToEnum(String s) {
        switch (s) {
            case "NEWLY_ADDED" -> {
                return JobStatus.NEWLY_ADDED;
            }
            case "PAID" -> {
                return JobStatus.PAID;
            }
            case "CANCELLED" -> {
                return JobStatus.CANCELLED;
            }
            case "IN_VERIFICATION_PROCESS" -> {
                return JobStatus.IN_VERIFICATION_PROCESS;
            }
            case "VERIFIED" -> {
                return JobStatus.VERIFIED;
            }
            case "REJECTED" -> {
                return JobStatus.REJECTED;
            }
            case "IN_PROGRESS" -> {
                return JobStatus.IN_PROGRESS;
            }
            case "FINISHED" -> {
                return JobStatus.FINISHED;
            }
            default -> {
                return JobStatus.UNKNOWN;
            }
        }
    }

    private CargoType mapTypeToEnum(String s) {
        switch (s) {
            case "LIGHT" -> {
                return CargoType.LIGHT;
            }
            case "HEAVY" -> {
                return CargoType.HEAVY;
            }
            case "FRAGILE" -> {
                return CargoType.FRAGILE;
            }
            case "HAZARDOUS" -> {
                return CargoType.HAZARDOUS;
            }
            default -> {
                return CargoType.NONE;
            }
        }
    }

    public int getJob_Id() {
        return job_Id;
    }

    public int getDriverId() {
        return driverId;
    }

    public CargoType getCargoType() {
        return cargoType;
    }

    public JobStatus getStatus() {
        return status;
    }

    public double getDistance() {
        return distance;
    }

    public double getWeight() {
        return weight;
    }

    public int getClientId() {
        return clientId;
    }

    public boolean isPaid() {
        return isPaid;
    }

}
