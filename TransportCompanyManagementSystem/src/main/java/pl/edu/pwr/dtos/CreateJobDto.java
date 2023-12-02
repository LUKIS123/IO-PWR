package pl.edu.pwr.dtos;

import pl.edu.pwr.models.enums.CargoType;

public class CreateJobDto {
    public String description;
    public CargoType cargoType;
    public double distance;
    public double weight;
}
