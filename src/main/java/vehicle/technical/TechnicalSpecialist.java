package vehicle.technical;

import vehicle.engine.DieselEngine;
import vehicle.engine.ElectricalEngine;
import vehicle.engine.GasolineEngine;
import vehicle.Color;
import vehicle.VehicleType;

public class TechnicalSpecialist {

    private TechnicalSpecialist() {
    }

    public static final int LOWER_LIMIT_MANUFACTURE_YEAR = 1886;

    public static boolean validateManufactureYear(int year) {
        if (year >= LOWER_LIMIT_MANUFACTURE_YEAR && year < 10000) return true;
        return false;
    }

    public static boolean validateMileage(int mileage) {
        if (mileage >= 0) return true;
        return false;
    }

    public static boolean validateWeight(int weight) {
        if (weight >= 0) return true;
        return false;
    }

    public static boolean validateColor(Color color) {
        if (color == null) return false;
        return true;
    }

    public static boolean validateVehicleType(VehicleType type) {
        if (type.getTypeName() != null && type.getTaxCoefficient() >= 0) return true;
        return false;
    }

    public static boolean validateRegistrationNumber(String number) {
        if (number != null) {
            char[] numberChar = number.toCharArray();
            if ((numberChar.length != 9) || numberChar[4] != ' ' || numberChar[7] != '-' ||
                    !((numberChar[0] >= '0') && (numberChar[0] <= '9')) ||
                    !((numberChar[1] >= '0') && (numberChar[1] <= '9')) ||
                    !((numberChar[2] >= '0') && (numberChar[2] <= '9')) ||
                    !((numberChar[3] >= '0') && (numberChar[3] <= '9')) ||
                    !((numberChar[8] >= '0') && (numberChar[8] <= '9')) ||
                    !((numberChar[5] >= 'A') && (numberChar[5] <= 'Z')) ||
                    !((numberChar[6] >= 'A') && (numberChar[6] <= 'Z')))
                return false;
        }
        return true;
    }

    public static boolean validateModelName(String name) {
        if (name != null) return true;
        return false;
    }

    public static boolean validateGasolineEngine(GasolineEngine engine) {
        if (engine.getEngineCapacity() > 0 && engine.getFuelConsumptionPer100() > 0 && engine.getFuelTankCapacity() > 0)
            return true;
        return false;
    }

    public static boolean validateElectricalEngine(ElectricalEngine engine) {
        if (engine.getBatterySize() > 0 && engine.getElectricityConsumption() > 0)
            return true;
        return false;
    }

    public static boolean validateDieselEngine(DieselEngine engine) {
        if (engine.getEngineCapacity() > 0 && engine.getFuelConsumptionPer100() > 0 && engine.getFuelTankCapacity() > 0)
            return true;
        return false;
    }

}
