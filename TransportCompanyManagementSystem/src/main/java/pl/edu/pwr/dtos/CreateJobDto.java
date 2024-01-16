package pl.edu.pwr.dtos;

import pl.edu.pwr.models.enums.CargoType;

public class CreateJobDto {
    public CargoType cargoType;
    public int distance;
    public int weight;

    public CreateJobDto(CargoType cargoType, int distance, int weight) {
        this.cargoType = cargoType;
        this.distance = distance;
        this.weight = weight;
    }

    public CreateJobDto() {
    }

    public void setCargoType(CargoType cargoType) {
        this.cargoType = cargoType;
    }

    public CargoType getCargoType() {
        return cargoType;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getDistance() {
        return distance;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }
}
