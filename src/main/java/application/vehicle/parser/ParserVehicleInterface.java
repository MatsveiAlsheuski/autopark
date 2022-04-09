package application.vehicle.parser;

import application.entity.Rents;
import application.entity.Types;
import application.entity.Vehicles;
import application.vehicle.Rent;
import application.vehicle.Vehicle;
import application.vehicle.VehicleType;


import java.util.List;

public interface ParserVehicleInterface {
    List<VehicleType> loadTypes();
    List<Vehicle> loadVehicles();
    List<Rent> loadRents();
    void save(Vehicle vehicle);
    void save(VehicleType vehicleType);
    void save(Rent rent);
    void saveFromDBToFileOrFromFileToDB();
}
