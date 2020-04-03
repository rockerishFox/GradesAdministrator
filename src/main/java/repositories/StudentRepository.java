package repositories;


import domain.Student;
import validators.ValidationException;
import validators.StudentValidator;

public class StudentRepository extends InMemoryRepository<String, Student> {
    public StudentRepository() {
        super(new StudentValidator());
    }

    @Override
    public Student findOne(String s) throws IllegalAccessException {
        return super.findOne(s);
    }

    @Override
    public Iterable<Student> findAll() {
        return super.findAll();
    }

    @Override
    public Student save(Student entity) throws ValidationException {
        return super.save(entity);
    }

    @Override
    public Student delete(String s) {
        return super.delete(s);
    }

    @Override
    public Student update(Student entity) throws ValidationException {
        return super.update(entity);
    }
}
