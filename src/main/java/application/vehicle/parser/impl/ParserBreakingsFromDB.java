package application.vehicle.parser.impl;

import application.entity.Orders;
import application.infrastructure.core.annotations.Autowired;
import application.service.OrdersService;
import application.vehicle.Order;
import application.vehicle.Vehicle;
import application.vehicle.parser.ParserBreakingsInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParserBreakingsFromDB implements ParserBreakingsInterface {
    @Autowired
    private OrdersService ordersService;

    public List<Order> getListOrders() {
        List<Order> order = new ArrayList<>();
        List<Orders> orders = ordersService.getAll();
        for (Orders order1 : orders) {
            order.add(new Order(Math.toIntExact(order1.getId()),order1.getDetails(),order1.getNumberOfBreakdowns()));
        }
        return order;
    }

    @Override
    public Map<String, Integer> detectBreaking(Vehicle vehicle, String[] details) {
        Map<String, Integer> map = new HashMap<>();
        int count = ((int) (Math.random() * 5));
        if (count != 0) {
            String detail = details[(int) (Math.random() * 8)];
            Orders orders = new Orders((long) vehicle.getId(),detail,count);
            ordersService.save(orders);
            map.put(detail, count);
        }
        return map;
    }

    @Override
    public void repair(Vehicle vehicle) {

         /** не работает*/
        System.out.println("Не работает метод repair(Vehicle vehicle в ParserBreakingsFromDB " + this);
        System.out.println("Нет шаблона удаления из БД");
    }
}
