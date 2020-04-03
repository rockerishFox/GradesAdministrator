package tests;

import domain.Student;

import org.junit.Test;
import validators.ValidationException;
import validators.StudentValidator;

public class TestStudentValidator {
    @Test
    public void validate() {
        StudentValidator stval=new StudentValidator();
        Student s= new Student("","","",222,"","");
        try{
            stval.validate(s);
            assert false;
        }
        catch (ValidationException v){
            assert true;
        }
        Student s1= new Student("1","Crisan","Bia",-15,"sdfds","sfds");
        try{
            stval.validate(s1);
            assert false;
        }
        catch (ValidationException v){
            assert true;
        }
    }
}
