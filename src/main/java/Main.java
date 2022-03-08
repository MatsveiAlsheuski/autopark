import vehicle.engine.DieselEngine;
import vehicle.engine.ElectricalEngine;
import vehicle.engine.GasolineEngine;
import vehicle.Color;
import vehicle.technical.TechnicalSpecialist;
import vehicle.Vehicle;
import vehicle.VehicleType;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        /** первая задача **/
        VehicleType[] types = {new VehicleType(1,"Bus", 1.2),
                new VehicleType(2,"Car", 1),
                new VehicleType(3,"Rink", 1.5),
                new VehicleType(4,"Tractor", 1.2)};

        for (int i = 0; i < types.length; i++) {
            System.out.println(types[i].display());
        }

        types[3].setTaxCoefficient(1.3);

        double maxCoefficient = types[0].getTaxCoefficient();
        for (VehicleType type : types) {
            if (maxCoefficient < type.getTaxCoefficient()) maxCoefficient = type.getTaxCoefficient();
        }
        System.out.println(maxCoefficient);

        double middleCoefficient = 0;
        for (VehicleType type : types) {
            middleCoefficient += type.getTaxCoefficient();
        }
        middleCoefficient /= types.length;
        System.out.println(middleCoefficient);

        for (VehicleType type : types) {
            System.out.println(type.display());
            if (maxCoefficient < type.getTaxCoefficient()) maxCoefficient = type.getTaxCoefficient();
            middleCoefficient += type.getTaxCoefficient();
        }
        System.out.println(maxCoefficient);
        middleCoefficient /= types.length;
        System.out.println(middleCoefficient);

        /** Вторая задача **/

        Vehicle[] vehicle = {new Vehicle(types[0], "Volkswagen Crafter", "5427 AX-7", 2022, 2015, 376000, Color.BLUE, new GasolineEngine(2, 8.5, 75)),
                new Vehicle(types[0], "Volkswagen Crafter", "6427 AA-7", 2500, 2014, 227010, Color.WHITE, new GasolineEngine(2.18, 8.5, 75)),
                new Vehicle(types[0], "Electric Bus E321", "6785 BA-7", 12080, 2019, 20451, Color.GREEN, new ElectricalEngine(50, 150)),
                new Vehicle(types[1], "Golf 5", "8682 AX-7", 1200, 2006, 230451, Color.GRAY, new DieselEngine(1.6, 7.2, 55)),
                new Vehicle(types[1], "Tesla Model S 70D", "0001 AA-7", 2200, 2019, 10454, Color.WHITE, new ElectricalEngine(25, 70)),
                new Vehicle(types[2], "Hamm HD 12 VV", null, 3000, 2016, 122, Color.YELLOW, new DieselEngine(3.2, 25, 20)),
                new Vehicle(types[3], "МТЗ Беларус-1025.4", "1145 AB-7", 1200, 2020, 109, Color.RED, new DieselEngine(4.75, 20.1, 135))};

        for (Vehicle vehicle1 : vehicle) {
            System.out.println(vehicle1.toString());
        }

        /** vehicle.Vehicle vehicleSort Выборка Big O(n2);*/

       /* vehicle.Vehicle vehicleSort;
        for (int i = 0; i < vehicle.length - 1; i++) {
            for (int j = i + 1; j < vehicle.length; j++) {
                if (vehicle[i].compareTo(vehicle[j]) > 0) {
                    vehicleSort = vehicle[i];
                    vehicle[i] = vehicle[j];
                    vehicle[j] = vehicleSort;
                }
            }
        }*/
        Arrays.sort(vehicle);
        System.out.println();
        for (Vehicle vehicle1 : vehicle) {
            System.out.println(vehicle1.toString());
        }

        System.out.println();
        Vehicle vehicleMin = vehicle[0];
        Vehicle vehicleMax = vehicle[0];
        for (int i = 0; i < vehicle.length - 1; i++) {
            if (vehicle[i].getMileage() > vehicleMax.getMileage()) vehicleMax = vehicle[i];
            if (vehicle[i].getMileage() < vehicleMin.getMileage()) vehicleMin = vehicle[i];
        }
        System.out.println("Min Mileage = " + vehicleMin);
        System.out.println("Max Mileage = " + vehicleMax);

        System.out.println(TechnicalSpecialist.validateRegistrationNumber(vehicle[1].getRegistrationNumber()));

        /** Четвертая задача **/
        for (int i = 0; i < vehicle.length - 1; i++) {
            for (int j = i + 1; j < vehicle.length; j++) {
                if (vehicle[i].equals(vehicle[j])) System.out.println(vehicle[i]);
            }
        }
        Vehicle vehicleMaxKilometers = vehicle[0];
        for (int i = 0; i < vehicle.length - 1; i++) {
            for (int j = i + 1; j < vehicle.length; j++) {
                if (vehicleMaxKilometers.getEngine().getMaxKilometers() > (vehicle[j].getEngine().getMaxKilometers()))
                    vehicleMaxKilometers = vehicle[i];

            }
        }
        System.out.println(vehicleMaxKilometers);
        System.out.println(vehicle[0].getCalcTaxPerMonth() );
    }

}
