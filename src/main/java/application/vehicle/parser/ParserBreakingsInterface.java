package application.vehicle.parser;

import application.entity.Orders;
import application.vehicle.Order;
import application.vehicle.Vehicle;

import java.util.List;
import java.util.Map;

public interface ParserBreakingsInterface {
    List<Order> getListOrders();
    Map<String, Integer> detectBreaking(Vehicle vehicle, String[] details);
    void repair(Vehicle vehicle);
}