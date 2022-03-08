package vehicle.collections;

import vehicle.Vehicle;

import java.util.Comparator;

public class ComparatorByTaxPerMonth implements Comparator<Vehicle> {
    @Override
    public int compare(Vehicle vehicle1, Vehicle vehicle2) {
        if (vehicle1.getCalcTaxPerMonth() > vehicle2.getCalcTaxPerMonth()) return 1;
        else if (vehicle1.getCalcTaxPerMonth() < vehicle2.getCalcTaxPerMonth()) return -1;
        else return 0;
    }
}
