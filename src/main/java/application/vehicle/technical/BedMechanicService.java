package application.vehicle.technical;

import application.infrastructure.core.annotations.Autowired;
import application.vehicle.Vehicle;

import java.util.Map;

public class BedMechanicService implements Fixer {

    String[] details = {"Фильтр", "Втулка", "Вал", "Ось", "Свеча", "Масло", "ГРМ", "ШРУС"};
    @Autowired
    private ParserBreakingsFromFile parser = new ParserBreakingsFromFile();

    public BedMechanicService() {
    }

    public ParserBreakingsFromFile getParser() {
        return parser;
    }

    public void setParser(ParserBreakingsFromFile parser) {
        this.parser = parser;
    }

    @Override
    public Map<String, Integer> detectBreaking(Vehicle vehicle) {
        return parser.detectBreaking(vehicle, details);
    }

    @Override
    public void repair(Vehicle vehicle) {
        parser.repair(vehicle);
    }

    @Override
    public boolean isBroken(Vehicle vehicle) {
        return false;
    }
}
