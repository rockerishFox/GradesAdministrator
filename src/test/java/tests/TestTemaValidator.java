package tests;

import domain.Student;
import domain.Tema;

import org.junit.Test;
import validators.ValidationException;
import validators.TemaValidator;

public class TestTemaValidator {
    @Test
    void validate() {
        TemaValidator tval=new TemaValidator();
        Student s=new Student("1","Crisan","Bianca",222,"crisanbia@gmail.com","Teofana");
        Tema t=new Tema("","","1",19);
        try{
            tval.validate(t);
            assert false;
        }
        catch (ValidationException v){
            assert true;
        }
        Tema t1=new Tema("1","sws","1",1);
        try{
            tval.validate(t1);
            assert false;
        }
        catch (ValidationException v){
            assert true;
        }
        Tema t2=new Tema("1","sws","1",13);
        try{
            tval.validate(t2);
            assert true;
        }
        catch (ValidationException v){
            assert true;
        }
    }
}
