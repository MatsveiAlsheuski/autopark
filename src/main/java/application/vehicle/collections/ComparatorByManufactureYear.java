package application.vehicle.collections;

import application.vehicle.Vehicle;

import java.util.Comparator;

public class ComparatorByManufactureYear implements Comparator<Vehicle> {
    @Override
    public int compare(Vehicle vehicle1, Vehicle vehicle2) {
        if (vehicle1.getManufactureYear() == vehicle2.getManufactureYear()) {
            if (vehicle1.getMileage() == vehicle2.getMileage()) return 0;
            else if (vehicle1.getMileage() < vehicle2.getMileage()) return -1;
            else return 1;
        } else if (vehicle1.getManufactureYear() < vehicle2.getManufactureYear()) return -1;
        else return 1;
    }
}
