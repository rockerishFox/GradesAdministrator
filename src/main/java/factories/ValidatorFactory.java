package factories;


import domain.Student;
import domain.Tema;
import validators.Validator;
import validators.StudentValidator;
import validators.TemaValidator;

public class ValidatorFactory<E>{
    private E entitate;
    private ValidatorFactory() {
    }

    private static ValidatorFactory instance = null;

    public static ValidatorFactory getInstance() {
        if (instance == null)
            instance = new ValidatorFactory();
        return instance;
    }

    public Validator createContainer(E entitate) {
        if (entitate instanceof Student)
            return new StudentValidator();
        else if (entitate instanceof Tema)
            return new TemaValidator();
        return null;
    }

}
