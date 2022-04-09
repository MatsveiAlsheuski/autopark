package application.vehicle.technical;

import application.exception.DefectedVehicleException;
import application.infrastructure.core.annotations.Autowired;
import application.vehicle.Order;
import application.vehicle.Vehicle;
import application.vehicle.parser.ParserBreakingsInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MechanicService implements Fixer {
    String[] details = {"Фильтр", "Втулка", "Вал", "Ось", "Свеча", "Масло", "ГРМ", "ШРУС"};
    @Autowired
    private ParserBreakingsInterface parser;

    public MechanicService() {
    }

    public ParserBreakingsInterface getParser() {
        return parser;
    }

    public void setParser(ParserBreakingsInterface parser) {
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
        List<Order> listOrders = parser.getListOrders();
        for (Order listOrder : listOrders) {
            if (vehicle.getId() == listOrder.getIdVehicle())
                return true;
        }
        return false;
    }
/*
    public Vehicle getMaxBrokeCar(ArrayList<Vehicle> vehicle) {
        Map<Integer, Integer> breakingCar = new HashMap(getMapSumBrokeCar());
        int max = 0;
        for (Map.Entry<Integer, Integer> entry : breakingCar.entrySet()) {
            if (entry.getValue() != null && max < entry.getValue()) max = entry.getValue();
        }
        for (Map.Entry<Integer, Integer> entry : breakingCar.entrySet()) {
            if (entry.getValue() != null && max == entry.getValue()) {
                for (Vehicle vehicle1 : vehicle) {
                    if (entry.getKey() == vehicle1.getId())
                        return vehicle1;
                }
            }
        }
        return null;
    }

    public Map<Integer, Integer> getMapSumBrokeCar() {
        Map<Integer, Integer> breakingCar = new HashMap();
        List<String[]> listOrders = parser.getListOrders();
        for (String[] lines : listOrders) {
            if (breakingCar.get(Integer.parseInt(lines[0])) == null)
                breakingCar.put(Integer.parseInt(lines[0]), Integer.parseInt(lines[2]));
            breakingCar.put(Integer.parseInt(lines[0]), breakingCar.get(Integer.parseInt(lines[0])) + Integer.parseInt(lines[2]));
        }
        return breakingCar;
    }

    public static void main(String[] args) {
        VehicleCollection vehicleCollection = new VehicleCollection();
        vehicleCollection.init();
        ArrayList<Vehicle> appliction.vehicle = (ArrayList<Vehicle>) vehicleCollection.getVehicleList();

        Fixer fixer = new MechanicService();
        MechanicService mechanicService = new MechanicService();
        /**поиск неисправностей и запись их в файл*/
     /*   for (Vehicle vehicle1 : appliction.vehicle) {
            System.out.println(fixer.detectBreaking(vehicle1));
        }*/
    /**поиск машины с самым большим числом неисправностей*/
    /*    System.out.println(mechanicService.getMaxBrokeCar(appliction.vehicle));
     */
    /**ремонт всех неисправных машин*/
       /* for (Vehicle vehicle1 : appliction.vehicle) {
            if (fixer.isBroken(vehicle1)) System.out.println(vehicle1);
            fixer.repair(vehicle1);
        }*/

    /**
     * ремонт одной машины машины ести она неисправна
     */
        /*fixer.repair(appliction.vehicle.get(1));
        System.out.println(mechanicService.arenda(appliction.vehicle.get(1)));

  }
    public Vehicle arenda(Vehicle vehicle) throws DefectedVehicleException {
        if (isBroken(vehicle)) throw new DefectedVehicleException("it Brock Car");
        return vehicle;
    }
*/
}

