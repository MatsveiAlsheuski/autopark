package vehicle.collections;

import vehicle.Vehicle;

import java.util.Comparator;

public class ComparatorVehicle implements Comparator<Vehicle> {
    @Override
    public int compare(Vehicle vehicle1, Vehicle vehicle2) {
        if (vehicle1.getModelName().length() > vehicle2.getModelName().length()) return 1;
        else if (vehicle1.getModelName().length() < vehicle2.getModelName().length()) return -1;
        else {
            char[] charsVehicle1 = vehicle1.getModelName().toCharArray();
            char[] charsVehicle2 = vehicle1.getModelName().toCharArray();
            for (int i = 0; i < vehicle1.getModelName().length(); i++) {
                if (charsVehicle1[i] > charsVehicle2[i])
                    return 1;
                else if (charsVehicle1[i] < charsVehicle2[i]) return -1;
            }
            return 0;
        }
    }

}
