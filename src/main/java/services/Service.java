package services;

import domain.Entity;
import repositories.CrudRepository;
import validators.ValidationException;

public class Service<ID,E extends Entity<ID>> {

    private CrudRepository<ID,E> repository;

    public Service(CrudRepository<ID, E> repository) {
        this.repository = repository;
    }

    public E save(E entity) throws ValidationException {
        return repository.save(entity);
    }
    public E findOne(ID id) throws IllegalAccessException {
        return repository.findOne(id);
    }
    public Iterable<E> findAll(){
        return repository.findAll();
    }

    public E delete(ID id){
        return repository.delete(id);
    }
    public E update(E entity) throws ValidationException {
        return repository.update(entity);
    }
}
