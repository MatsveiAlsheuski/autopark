package vehicle.engine;

public abstract class CombustionEngine extends AbstractEngine {

    private double engineCapacity;
    private double fuelConsumptionPer100;
    private double fuelTankCapacity;

    public CombustionEngine(String nameEngine, double taxPerMonth, double engineCapacity, double fuelConsumptionPer100, double fuelTankCapacity) {
        super(nameEngine, taxPerMonth);
        this.engineCapacity = engineCapacity;
        this.fuelConsumptionPer100 = fuelConsumptionPer100;
        this.fuelTankCapacity = fuelTankCapacity;
    }

    @Override
    public double getMaxKilometers() {
        return fuelTankCapacity * 100 / fuelConsumptionPer100;
    }

    public double getEngineCapacity() {
        return engineCapacity;
    }

    public void setEngineCapacity(double engineCapacity) {
        this.engineCapacity = engineCapacity;
    }

    public double getFuelConsumptionPer100() {
        return fuelConsumptionPer100;
    }

    public void setFuelConsumptionPer100(double fuelConsumptionPer100) {
        this.fuelConsumptionPer100 = fuelConsumptionPer100;
    }

    public double getFuelTankCapacity() {
        return fuelTankCapacity;
    }

    public void setFuelTankCapacity(double fuelTankCapacity) {
        this.fuelTankCapacity = fuelTankCapacity;
    }
}
