package application.service;

import application.entity.Types;
import application.infrastructure.core.annotations.Autowired;
import application.infrastructure.core.annotations.InitMethod;
import application.infrastructure.orm.EntityManager;

import java.util.List;

public class TypesService {
    @Autowired
    EntityManager entityManager;
    @InitMethod
    public void init() {}

    public Types get(Long id) {
        return entityManager.get(id,Types.class).get();
    }

    public List<Types> getAll() {
        return entityManager.getAll(Types.class);
    }

    public Long save(Types type) {
        return entityManager.save(type);
    }
}
