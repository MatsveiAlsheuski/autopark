package application.service;

import application.entity.Vehicles;
import application.infrastructure.core.annotations.Autowired;
import application.infrastructure.core.annotations.InitMethod;
import application.infrastructure.orm.EntityManager;

import java.util.List;

public class VehiclesService {
    @Autowired
    EntityManager entityManager;
    @InitMethod
    public void init() {}

    public Vehicles get(Long id) {
        return entityManager.get(id,Vehicles.class).get();
    }

    public List<Vehicles> getAll() {
        return entityManager.getAll(Vehicles.class);
    }

    public Long save(Vehicles vehicle) {
        return entityManager.save(vehicle);
    }
}
