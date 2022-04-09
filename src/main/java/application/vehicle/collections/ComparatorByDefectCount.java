package application.vehicle.collections;

import application.vehicle.Vehicle;
import application.vehicle.technical.MechanicService;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class ComparatorByDefectCount/* implements Comparator<Vehicle>*/ {
  /*  @Override
    public int compare(Vehicle vehicle1, Vehicle vehicle2) {
        MechanicService mechanicService = new MechanicService();
        Map<Integer, Integer> breakingCar = new HashMap(mechanicService/*.getMapSumBrokeCar());
        if (breakingCar.get(vehicle1.getId()) > breakingCar.get(vehicle2.getId())) return 1;
        else if (breakingCar.get(vehicle1.getId()) < breakingCar.get(vehicle2.getId())) return -1;
        else return 0;
    }*/
}
