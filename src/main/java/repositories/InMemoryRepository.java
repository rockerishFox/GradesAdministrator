package repositories;



import domain.Entity;
import validators.ValidationException;
import validators.Validator;

import java.util.HashMap;


public class InMemoryRepository <ID,E extends Entity<ID>> implements CrudRepository<ID,E> {
    private HashMap<ID, E> entities;
    private Validator<E> validator;

    public InMemoryRepository(Validator<E> validator) {
        this.entities = new HashMap<ID, E>();
        this.validator = validator;
    }


    @Override
    public E findOne(ID id) throws IllegalAccessException {
        if(id == null)
            throw new IllegalAccessException("Id incorect");
        if(entities.containsKey(id))
            return entities.get(id);
        return null;
    }

    @Override
    public Iterable<E> findAll() {
        return entities.values();
    }

    @Override
    public E save(E entity) throws ValidationException {
        if (entity==null)
            throw new IllegalArgumentException("Entitate null!!");
        validator.validate(entity);
        if( entities.containsKey(entity.getId()) )
            return entity;
        return entities.put(entity.getId(), entity);
    }

    @Override
    public E delete(ID id) {
        if (id == null)
            throw new IllegalArgumentException("Id incorect!");
        if (entities.containsKey(id)) {
            E entity = entities.get(id);
            entities.remove(id, entities.get(id));
            return entity;
        } else
            return null;
    }

    @Override
    public E update(E entity) throws ValidationException {
            if(entity == null)
                throw  new IllegalArgumentException("Entitate inexistenta!");
            validator.validate(entity);
            if(entities.containsKey(entity.getId())){
                entities.replace(entity.getId(),entity);
                return null;
            }
            return entity;
    }
}
