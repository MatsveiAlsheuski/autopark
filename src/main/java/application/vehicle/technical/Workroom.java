package application.vehicle.technical;

import application.infrastructure.core.annotations.Autowired;
import application.vehicle.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class Workroom {
    @Autowired
    Fixer mechanic;

    public Workroom() {
    }

    public Fixer getMechanic() {
        return mechanic;
    }

    public void setMechanic(Fixer mechanic) {
        this.mechanic = mechanic;
    }

    public void checkAllVehicle(List<Vehicle> vehicleList){
        List<Vehicle> serviceableVehicle = new ArrayList<>();
        for (Vehicle vehicle : vehicleList) {
            if (mechanic.isBroken(vehicle)){
                System.out.println(vehicle + " - is broken");
            }else serviceableVehicle.add(vehicle);
        }
        System.out.println();
        for (Vehicle vehicle : serviceableVehicle) {
            System.out.println(vehicle + " - is serviceable");
        }
    }
}
