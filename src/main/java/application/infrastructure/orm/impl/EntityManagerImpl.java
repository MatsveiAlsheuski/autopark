package application.infrastructure.orm.impl;

import application.infrastructure.core.Context;
import application.infrastructure.core.annotations.Autowired;
import application.infrastructure.orm.ConnectionFactory;
import application.infrastructure.orm.EntityManager;
import application.infrastructure.orm.service.PostgreDateBaseService;

import java.util.List;
import java.util.Optional;

public class EntityManagerImpl implements EntityManager {

    @Autowired
    private ConnectionFactory connection;
    @Autowired
    private PostgreDateBaseService dateBaseService;
    @Autowired
    private Context context;

    public EntityManagerImpl() {
    }

    @Override
    public <T> Optional<T> get(Long id, Class<T> clazz) {
        return dateBaseService.get(id, clazz);
    }

    @Override
    public Long save(Object object) {
        return dateBaseService.save(object);
    }

    @Override
    public <T> List<T> getAll(Class<T> clazz) {
        return dateBaseService.getAll(clazz);
    }
}
