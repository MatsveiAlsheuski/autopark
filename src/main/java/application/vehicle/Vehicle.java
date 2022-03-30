package application.vehicle;

import application.exception.NotVehicleException;
import application.vehicle.engine.Startable;
import application.vehicle.technical.TechnicalSpecialist;

import java.util.List;
import java.util.Objects;

public class Vehicle implements Comparable<Vehicle> {
    private int id;
    private List<Rent> rents;
    private /*final*/ VehicleType vehicleType;
    private Startable engine;
    private /*final*/ String modelName;
    private String registrationNumber;
    private int weight;
    private /*final*/ int manufactureYear;
    private int mileage;
    private Color color;
    private int volumeOfTheTank;

    public Vehicle() {
    }

    public Vehicle(VehicleType vehicleType, String modelName, String registrationNumber,
                   int weight, int manufactureYear, int mileage, Color color, Startable engine) {

        try {
            if (!TechnicalSpecialist.validateVehicleType(vehicleType))
                throw new NotVehicleException("Incorrect appliction.vehicle Type");
            else this.vehicleType = vehicleType;
            if (!TechnicalSpecialist.validateModelName(modelName))
                throw new NotVehicleException("Incorrect model Name");
            else this.modelName = modelName;
            if (!TechnicalSpecialist.validateRegistrationNumber(registrationNumber))
                throw new NotVehicleException("Incorrect registration Number");
            else this.registrationNumber = registrationNumber;
            if (!TechnicalSpecialist.validateWeight(weight)) throw new NotVehicleException("Incorrect weight");
            else this.weight = weight;
            if (!TechnicalSpecialist.validateManufactureYear(manufactureYear))
                throw new NotVehicleException("Incorrect manufacture Year");
            else this.manufactureYear = manufactureYear;
            if (!TechnicalSpecialist.validateMileage(mileage)) throw new NotVehicleException("Incorrect mileage");
            else this.mileage = mileage;
            if (!TechnicalSpecialist.validateColor(color)) throw new NotVehicleException("Incorrect color");
            else this.color = color;
            this.engine = engine;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Don't create auto");
           // new appliction.vehicle.Vehicle();
        }

    }

    public double getTotalIncome(){
        double sumRent = 0;
        for (Rent rent : rents) {
         sumRent += rent.getRentCost();
        }
        return sumRent;
    }

    public double getTotalProfit(){
        return getTotalIncome()-getCalcTaxPerMonth();
    }

    public double getCalcTaxPerMonth() {

        double scale = Math.pow(10, 2);//Окрушление
        return Math.ceil(((getWeight() * 0.0013) + (getVehicleType().getTaxCoefficient() * getEngine().getTaxPerMonth() * 30) + 5) * scale) / scale;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Rent> getRents() {
        return rents;
    }

    public void setRents(List<Rent> rents) {
        this.rents = rents;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public String getModelName() {
        return modelName;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        if (TechnicalSpecialist.validateRegistrationNumber(registrationNumber))
            this.registrationNumber = registrationNumber;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        if (TechnicalSpecialist.validateWeight(weight))
            this.weight = weight;
    }

    public int getManufactureYear() {
        return manufactureYear;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        if (TechnicalSpecialist.validateMileage(mileage))
            this.mileage = mileage;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        if (TechnicalSpecialist.validateColor(color))
            this.color = color;
    }

    public int getVolumeOfTheTank() {
        return volumeOfTheTank;
    }

    public void setVolumeOfTheTank(int volumeOfTheTank) {
        this.volumeOfTheTank = volumeOfTheTank;
    }

    public Startable getEngine() {
        return engine;
    }

    public void setEngine(Startable engine) {
        this.engine = engine;
    }

    @Override
    public String toString() {
        return vehicleType.getString() + ", " + modelName + ", " + registrationNumber + ", " + weight +
                ", " + manufactureYear + ", " + mileage + ", " + color +
                ", " /*+ volumeOfTheTank +", " */ + engine + ", " + getCalcTaxPerMonth();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return vehicleType.equals(vehicle.vehicleType) &&
                modelName.equals(vehicle.modelName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vehicleType, modelName);
    }

    @Override
    public int compareTo(Vehicle obj) {
        if (this.getManufactureYear() == obj.getManufactureYear()) {
            if (this.getMileage() == obj.getMileage()) return 0;
            else if (this.getMileage() < obj.getMileage()) return -1;
            else return 1;
        } else if (this.getManufactureYear() < obj.getManufactureYear()) return -1;
        else return 1;
    }
}
