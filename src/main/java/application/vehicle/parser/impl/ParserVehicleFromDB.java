package application.vehicle.parser.impl;

import application.entity.Rents;
import application.entity.Types;
import application.entity.Vehicles;
import application.infrastructure.core.annotations.Autowired;
import application.service.RentsService;
import application.service.TypesService;
import application.service.VehiclesService;
import application.vehicle.Color;
import application.vehicle.Rent;
import application.vehicle.Vehicle;
import application.vehicle.VehicleType;
import application.vehicle.engine.*;
import application.vehicle.parser.ParserVehicleInterface;

import java.util.ArrayList;
import java.util.List;

public class ParserVehicleFromDB implements ParserVehicleInterface {
    @Autowired
    private TypesService typesService;

    @Autowired
    private VehiclesService vehiclesService;

    @Autowired
    private RentsService rentsService;


    public List<VehicleType> loadTypes() {
        List<VehicleType> vehicleType = new ArrayList<>();
        for (Types type : typesService.getAll()) {
            vehicleType.add(new VehicleType(Math.toIntExact(type.getId()), type.getTypeName(), type.getTaxCoefficient()));
        }
        return vehicleType;
    }

    public List<Vehicle> loadVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        for (Vehicles vehicl : vehiclesService.getAll()) {
            vehicles.add(createVehicle(vehicl));
        }
        return vehicles;
    }

    private Vehicle createVehicle(Vehicles vehicles) {
        List<VehicleType> vehicleType = loadTypes();
        Vehicle vehicle = new Vehicle(vehicleType.get(vehicles.getVehicleType() - 1), vehicles.getModelName(),
                vehicles.getRegistrationNumber(), vehicles.getWeight(), vehicles.getManufactureYear(),
                vehicles.getMileage(), Color.valueOf(vehicles.getColor().toUpperCase()), createEngine(vehicles));
        vehicle.setId(Math.toIntExact(vehicles.getId()));
        vehicle.setRents(loadRentsVehicle(Math.toIntExact(vehicles.getId())));                                      /**переделать*/
        return vehicle;
    }

    private AbstractEngine createEngine(Vehicles vehicles) {
        if (vehicles.getNameEngine().equals("Gasoline")) {
            return new GasolineEngine(vehicles.getEngineCapacity(), vehicles.getFuelConsumptionPer100()
                    , vehicles.getFuelTankCapacity());
        }
        if (vehicles.getNameEngine().equals("Diesel")) {
            return new DieselEngine(vehicles.getEngineCapacity(), vehicles.getFuelConsumptionPer100()
                    , vehicles.getFuelTankCapacity());
        }
        if (vehicles.getNameEngine().equals("Electrical")) {
            return new ElectricalEngine(vehicles.getEngineCapacity(), vehicles.getFuelConsumptionPer100());
        }
        return null;
    }

    private List<Rent> loadRentsVehicle(int idVehicle) {
        List<Rent> listRentsVehicle = new ArrayList<>();
        List<Rent> listRents = loadRents();
        for (Rent rent : listRents) {
            if (rent.getId() == idVehicle)
                listRentsVehicle.add(rent);
        }
        return listRentsVehicle;
    }

    public List<Rent> loadRents() {
        List<Rent> rent = new ArrayList<>();
        for (Rents rents : rentsService.getAll()) {
            rent.add(new Rent(Math.toIntExact(rents.getId()), rents.getDate(), rents.getRentCost()));
        }
        return rent;
    }

    @Override
    public void save(Vehicle vehicle) {
        String engine;
        double engineCapacity;
        double fuelConsumptionPer100;
        double fuelTankCapacity;
        String color = vehicle.getColor().toString().substring(0, 1).toUpperCase() + vehicle.getColor().
                toString().substring(1).toLowerCase();
        if (vehicle.getEngine().getClass() == GasolineEngine.class) {
            engine = "Gasoline";
            GasolineEngine gasolineEngine = (GasolineEngine) vehicle.getEngine();
            engineCapacity = gasolineEngine.getEngineCapacity();
            fuelConsumptionPer100 = gasolineEngine.getFuelConsumptionPer100();
            fuelTankCapacity = gasolineEngine.getFuelTankCapacity();
        } else if (vehicle.getEngine().getClass() == DieselEngine.class) {
            DieselEngine dieselEngine = (DieselEngine) vehicle.getEngine();
            engine = "Diesel";
            engineCapacity = dieselEngine.getEngineCapacity();
            fuelConsumptionPer100 = dieselEngine.getFuelConsumptionPer100();
            fuelTankCapacity = dieselEngine.getFuelTankCapacity();
        } else {
            engine = "Electrical";
            ElectricalEngine electricalEngine = (ElectricalEngine) vehicle.getEngine();
            engineCapacity = electricalEngine.getBatterySize();
            fuelConsumptionPer100 = electricalEngine.getElectricityConsumption();
            fuelTankCapacity = 0;
        }

        Vehicles vehicles = new Vehicles((long) vehicle.getId(), vehicle.getVehicleType().getId(), vehicle.getModelName(),
                vehicle.getRegistrationNumber(), vehicle.getWeight(), vehicle.getManufactureYear(), vehicle.getMileage(),
                color, engine, engineCapacity, fuelConsumptionPer100, fuelTankCapacity);
        vehiclesService.save(vehicles);
    }

    @Override
    public void save(VehicleType vehicleType) {
        Types types = new Types((long) vehicleType.getId(), vehicleType.getTypeName(), vehicleType.getTaxCoefficient());
        typesService.save(types);
    }

    @Override
    public void save(Rent rent) {
        Rents rents = new Rents((long) rent.getId(), rent.getRentDate(), rent.getRentCost());
        rentsService.save(rents);
    }

    @Override
    public void saveFromDBToFileOrFromFileToDB() {
       /* ParserVehicleInterface parserVehicleInterface = new ParserVehicleFromFile();
        for (Rent loadRent : parserVehicleInterface.loadRents()) {
            save(loadRent);
        }
        for (VehicleType loadType : parserVehicleInterface.loadTypes()) {
            save(loadType);
        }
        for (Vehicle loadVehicle : parserVehicleInterface.loadVehicles()) {
            save(loadVehicle);
        }

        System.out.println("Метод saveFromDBToFileOrFromFileToDB() в ParserVehicleFromDB отработал " + this);*/
         System.out.println("Метод saveFromDBToFileOrFromFileToDB() в ParserVehicleFromDB отключен " + this);
    }


}
