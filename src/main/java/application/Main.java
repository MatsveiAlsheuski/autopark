package application;

import application.infrastructure.config.Config;
import application.infrastructure.config.impl.JavaConfig;
import application.infrastructure.configurations.ObjectConfigurator;
import application.infrastructure.configurations.impl.AutowiredObjectConfigurator;
import application.infrastructure.core.Cache;
import application.infrastructure.core.Context;
import application.infrastructure.core.ObjectFactory;
import application.infrastructure.core.Scanner;
import application.infrastructure.core.impl.ApplicationContext;
import application.infrastructure.core.impl.CacheImpl;
import application.infrastructure.core.impl.ObjectFactoryImpl;
import application.infrastructure.core.impl.ScannerImpl;
import application.vehicle.collections.VehicleCollection;
import application.vehicle.technical.Fixer;
import application.vehicle.technical.MechanicService;
import application.vehicle.technical.Workroom;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        Map<Class<?>, Class<?>> interfaceToImplementation = new HashMap<>();
        interfaceToImplementation.put(Fixer.class, MechanicService.class);
        interfaceToImplementation.put(Config.class, JavaConfig.class);
        //interfaceToImplementation.put(ObjectConfigurator.class, PropertyObjectConfigurator.class);
        interfaceToImplementation.put(ObjectConfigurator.class, AutowiredObjectConfigurator.class);
        interfaceToImplementation.put(Cache.class, CacheImpl.class);
        interfaceToImplementation.put(Context.class, ApplicationContext.class);
        interfaceToImplementation.put(ObjectFactory.class, ObjectFactoryImpl.class);
        interfaceToImplementation.put(Scanner.class, ScannerImpl.class);
        // interfaceToImplementation.put(ParserVehicleFromFile.class, ParserVehicleFromFile.class);

        ApplicationContext context = new ApplicationContext("application",interfaceToImplementation);
        VehicleCollection vehicleCollection = context.getObject(VehicleCollection.class);
        Workroom workroom = context.getObject(Workroom.class);
        workroom.checkAllVehicle(vehicleCollection.getVehicleList());

        /** первая задача **/
   /*     VehicleType[] types = {new VehicleType(1,"Bus", 1.2),
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
*/
        /** Вторая задача **/
/*
        Vehicle[] appliction.vehicle = {new Vehicle(types[0], "Volkswagen Crafter", "5427 AX-7", 2022, 2015, 376000, Color.BLUE, new GasolineEngine(2, 8.5, 75)),
                new Vehicle(types[0], "Volkswagen Crafter", "6427 AA-7", 2500, 2014, 227010, Color.WHITE, new GasolineEngine(2.18, 8.5, 75)),
                new Vehicle(types[0], "Electric Bus E321", "6785 BA-7", 12080, 2019, 20451, Color.GREEN, new ElectricalEngine(50, 150)),
                new Vehicle(types[1], "Golf 5", "8682 AX-7", 1200, 2006, 230451, Color.GRAY, new DieselEngine(1.6, 7.2, 55)),
                new Vehicle(types[1], "Tesla Model S 70D", "0001 AA-7", 2200, 2019, 10454, Color.WHITE, new ElectricalEngine(25, 70)),
                new Vehicle(types[2], "Hamm HD 12 VV", null, 3000, 2016, 122, Color.YELLOW, new DieselEngine(3.2, 25, 20)),
                new Vehicle(types[3], "МТЗ Беларус-1025.4", "1145 AB-7", 1200, 2020, 109, Color.RED, new DieselEngine(4.75, 20.1, 135))};

        for (Vehicle vehicle1 : appliction.vehicle) {
            System.out.println(vehicle1.toString());
        }*/

        /** appliction.vehicle.Vehicle vehicleSort Выборка Big O(n2);*/

       /* appliction.vehicle.Vehicle vehicleSort;
        for (int i = 0; i < appliction.vehicle.length - 1; i++) {
            for (int j = i + 1; j < appliction.vehicle.length; j++) {
                if (appliction.vehicle[i].compareTo(appliction.vehicle[j]) > 0) {
                    vehicleSort = appliction.vehicle[i];
                    appliction.vehicle[i] = appliction.vehicle[j];
                    appliction.vehicle[j] = vehicleSort;
                }
            }
        }*/
  /*      Arrays.sort(appliction.vehicle);
        System.out.println();
        for (Vehicle vehicle1 : appliction.vehicle) {
            System.out.println(vehicle1.toString());
        }

        System.out.println();
        Vehicle vehicleMin = appliction.vehicle[0];
        Vehicle vehicleMax = appliction.vehicle[0];
        for (int i = 0; i < appliction.vehicle.length - 1; i++) {
            if (appliction.vehicle[i].getMileage() > vehicleMax.getMileage()) vehicleMax = appliction.vehicle[i];
            if (appliction.vehicle[i].getMileage() < vehicleMin.getMileage()) vehicleMin = appliction.vehicle[i];
        }
        System.out.println("Min Mileage = " + vehicleMin);
        System.out.println("Max Mileage = " + vehicleMax);

        System.out.println(TechnicalSpecialist.validateRegistrationNumber(appliction.vehicle[1].getRegistrationNumber()));
*/
        /** Четвертая задача **/
    /*    for (int i = 0; i < appliction.vehicle.length - 1; i++) {
            for (int j = i + 1; j < appliction.vehicle.length; j++) {
                if (appliction.vehicle[i].equals(appliction.vehicle[j])) System.out.println(appliction.vehicle[i]);
            }
        }
        Vehicle vehicleMaxKilometers = appliction.vehicle[0];
        for (int i = 0; i < appliction.vehicle.length - 1; i++) {
            for (int j = i + 1; j < appliction.vehicle.length; j++) {
                if (vehicleMaxKilometers.getEngine().getMaxKilometers() > (appliction.vehicle[j].getEngine().getMaxKilometers()))
                    vehicleMaxKilometers = appliction.vehicle[i];

            }
        }
        System.out.println(vehicleMaxKilometers);
        System.out.println(appliction.vehicle[0].getCalcTaxPerMonth() );*/


    }
}
