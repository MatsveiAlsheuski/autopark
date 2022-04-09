
package application.vehicle.collections;

import application.infrastructure.core.annotations.Autowired;
import application.infrastructure.core.annotations.InitMethod;
import application.vehicle.Vehicle;
import application.vehicle.VehicleType;
import application.vehicle.parser.ParserVehicleInterface;

import java.util.*;

public class VehicleCollection/* implements Comparable<appliction.vehicle.Vehicle>*/ {
    private List<VehicleType> vehicleTypeList = new ArrayList<>();
    private List<Vehicle> vehicleList = new ArrayList<>();

    @Autowired
    private ParserVehicleInterface parser;// = new ParserVehicleFromFile();

    public VehicleCollection() {
    }

    @InitMethod
    public void init() {
        vehicleTypeList = parser.loadTypes();
        vehicleList = parser.loadVehicles();
        parser.saveFromDBToFileOrFromFileToDB();
    }

    public List<Vehicle> getVehicleList() {
        return vehicleList;
    }

    public List<VehicleType> getVehicleTypeList() {
        return vehicleTypeList;
    }

    public void setVehicleTypeList(List<VehicleType> vehicleTypeList) {
        this.vehicleTypeList = vehicleTypeList;
    }

    public void setVehicleList(List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
    }

    public ParserVehicleInterface getParser() {
        return parser;
    }

    public void setParser(ParserVehicleInterface parser) {
        this.parser = parser;
    }
    /*
    public double sumTotalProfit() {
        double sumTotal = 0;
        for (Vehicle vehicle : vehicleList) {
            sumTotal += vehicle.getTotalProfit();
        }
        double scale = Math.pow(10, 2);//Окрушление
        return Math.ceil(sumTotal * scale) / scale;
    }

   public void insert(int index, Vehicle v) {
        try {
            vehicleList.add(index, v);
        } catch (Exception e) {
            vehicleList.add(v);
        }
    }

    public int delete(int index) {
        try {
            vehicleList.remove(index);
            return index;
        } catch (Exception e) {
            return -1;
        }
    }

    public void display() {
        System.out.println(
                "Id    Type     ModelName     Number      Weight(kg)   Year    Mileage    appliction.vehicle.Color    Income   Tax   Profit");
        for (Vehicle vehicle : vehicleList) {
            System.out.println(vehicle.getId() + " " + vehicle);
        }
        System.out.println("Total    " + sumTotalProfit());
    }

   /* public static void main(String[] args)  {
        VehicleCollection vehicleCollection = new VehicleCollection();
        vehicleCollection.init();
        // vehicleCollection.insert(5, new appliction.vehicle.Vehicle(new appliction.vehicle.VehicleType(2, "Car", 1), "Volkswagen Crafter", "6427 AA-7", 2500, 2014, 227010, appliction.vehicle.Color.WHITE, new GasolineEngine(2.18, 8.5, 75)));
        // System.out.println(vehicleCollection.delete(1));
        // vehicleCollection.display();
        //  appliction.vehicle.collections.VehicleComparator vehicleComparator = new appliction.vehicle.collections.VehicleComparator();
        // Collections.sort(vehicleCollection.vehicleList, vehicleComparator);
        vehicleCollection.display();
    }*/
}

