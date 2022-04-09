package application.entity;

import application.infrastructure.orm.annotations.Column;
import application.infrastructure.orm.annotations.ID;
import application.infrastructure.orm.annotations.Table;
import application.vehicle.Color;
import lombok.*;

@Table(name = "vehicles")
@Builder
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Vehicles {
    @ID
    private Long id;
    @Column(name = "vehicleType")
    private Integer vehicleType;
    @Column(name = "modelName")
    private String modelName;
    @Column(name = "registrationNumber")
    private String registrationNumber;
    @Column(name = "weight")
    private Integer weight;
    @Column(name = "manufactureYear")
    private Integer manufactureYear;
    @Column(name = "mileage")
    private Integer mileage;
    @Column(name = "color")
    private String color;
    @Column(name = "nameEngine")
    private String nameEngine;
    @Column(name = "engineCapacity")//battery Size
    private Double engineCapacity;
    @Column(name = "fuelConsumptionPer100")//electricity Consumption
    private Double fuelConsumptionPer100;

    @Column(name = "fuelTankCapacity", nullable = false)//null
    private Double fuelTankCapacity;

}
