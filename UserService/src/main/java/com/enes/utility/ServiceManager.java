package com.enes.utility;


import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;



public class ServiceManager<T,ID> implements Iservice<T,ID> {

    /**
     * IProductRepository
     * IUserRepository
     * @param t
     * @return
     */
    private final MongoRepository<T,ID> repository;
    public ServiceManager(MongoRepository<T,ID> repository){
        this.repository=repository;
    }
    @Override
    public T save(T t) {
        return repository.save(t);
    }
    @Override
    public Iterable<T> saveAll(Iterable<T> entities) {
        return repository.saveAll(entities);
    }

    @Override
    public T update(T t) {
        return repository.save(t);
    }

    @Override
    public void delete(T t) {
        repository.delete(t);
    }

    @Override
    public void deleteById(ID id) {
        repository.deleteById(id);
    }

    @Override
    public Optional<T> findById(ID id) {
        return repository.findById(id);
    }

    @Override
    public List<T> findAll() {
        return repository.findAll();
    }
}
