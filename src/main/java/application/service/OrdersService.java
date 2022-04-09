package application.service;

import application.entity.Orders;
import application.infrastructure.core.annotations.Autowired;
import application.infrastructure.core.annotations.InitMethod;
import application.infrastructure.orm.EntityManager;

import java.util.List;

public class OrdersService {
    @Autowired
    EntityManager entityManager;
    @InitMethod
    public void init() {}

    public Orders get(Long id) {
        return entityManager.get(id,Orders.class).get();
    }

    public List<Orders> getAll() {
        return entityManager.getAll(Orders.class);
    }

    public Long save(Orders order) {
        return entityManager.save(order);
    }
}
