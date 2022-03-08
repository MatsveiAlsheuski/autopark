package vehicle.engine;

public class GasolineEngine extends CombustionEngine {
    public GasolineEngine(double engineCapacity, double fuelConsumptionPer100, double fuelTankCapacity) {
        super("Gasoline", 1.1, engineCapacity, fuelConsumptionPer100, fuelTankCapacity);
    }
}
