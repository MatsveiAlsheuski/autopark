package application.service;

import application.entity.Rents;
import application.infrastructure.core.annotations.Autowired;
import application.infrastructure.core.annotations.InitMethod;
import application.infrastructure.orm.EntityManager;

import java.util.List;

public class RentsService {
    @Autowired
    EntityManager entityManager;
    @InitMethod
    public void init() {}

    public Rents get(Long id) {
        return entityManager.get(id,Rents.class).get();
    }

    public List<Rents> getAll() {
        return entityManager.getAll(Rents.class);
    }

    public Long save(Rents rent) {
        return entityManager.save(rent);
    }
}
