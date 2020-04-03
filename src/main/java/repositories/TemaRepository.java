package repositories;


import domain.Tema;
import validators.ValidationException;
import validators.TemaValidator;

public class TemaRepository extends InMemoryRepository<String, Tema> {
    public TemaRepository() {
        super(new TemaValidator());
    }

    @Override
    public Tema findOne(String s) throws IllegalAccessException {
        return super.findOne(s);
    }

    @Override
    public Iterable<Tema> findAll() {
        return super.findAll();
    }

    @Override
    public Tema save(Tema entity) throws ValidationException {
        return super.save(entity);
    }

    @Override
    public Tema delete(String s) {
        return super.delete(s);
    }

    @Override
    public Tema update(Tema entity) throws ValidationException {
        return super.update(entity);
    }
}
