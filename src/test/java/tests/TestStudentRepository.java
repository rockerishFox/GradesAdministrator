package tests;

import domain.Student;

import org.junit.Test;
import repositories.StudentRepository;
import validators.ValidationException;

public class TestStudentRepository {
    StudentRepository generate(){
        StudentRepository srepo=new StudentRepository();
        Student s=new Student("1","Crisan","Bianca",222,"crisanbia@gmail.com","Teofana");
        Student s1=new Student("2","Birle","Damaris",221,"birledama@gmail.com","Teofana");
        Student s2=new Student("3","Csiki","Delia",222,"csikidelia@gmail.com","Camelia");
        try {
            srepo.save(s);
        } catch (ValidationException e) {
            e.printStackTrace();
        }
        try {
            srepo.save(s1);
        } catch (ValidationException e) {
            e.printStackTrace();
        }
        return srepo;
    }

    @Test
     public void findOne() {
        StudentRepository srepo=generate();
        Student s=srepo.findOne("1");
        assert(s.getGrupa()==222);
        assert(s.getCadruDidacticIndrumatorLab()=="Teofana");
        assert (srepo.findOne("3")==null);
        try {
            Student s1=srepo.findOne(null);
        }
        catch (IllegalArgumentException i){
        }
    }

    @Test
    public void findAll() {
        StudentRepository srepo=generate();
        Iterable<Student> st=srepo.findAll();
        assert(st.spliterator().getExactSizeIfKnown()==2);
    }

    @Test
    public void save() {
        StudentRepository srepo=generate();
        Student s2=new Student("3","Csiki","Delia",222,"csikidelia@gmail.com","Camelia");
        try {
            if ((srepo.save(s2) != null)) throw new AssertionError();
        } catch (ValidationException e) {
            e.printStackTrace();
        }
        Iterable<Student> st=srepo.findAll();
        assert(st.spliterator().getExactSizeIfKnown()==3);
        Student s=new Student("1","Crisan","Bianca",222,"crisanbia@gmail.com","Teofana");
        try {
            if ((srepo.save(s) == null)) throw new AssertionError();
        } catch (ValidationException e) {
            e.printStackTrace();
        }
        try {
            srepo.save(null);
        }
        catch (IllegalArgumentException i){
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void delete() {
        StudentRepository srepo=generate();
        Student s=srepo.delete("1");
        assert(s.getId()=="1");
        assert(srepo.delete("4")==null);
        Iterable<Student> st=srepo.findAll();
        assert(st.spliterator().getExactSizeIfKnown()==1);
    }

    @Test
    public void update() {
        StudentRepository srepo=generate();
        Student s1=new Student("2","Birle","Emima",221,"birledama@gmail.com","Teofana");
        Student s2=new Student("3","Csiki","Dragos",222,"csikidelia@gmail.com","Camelia");
        Student s= null;
        try {
            s = srepo.update(s1);
        } catch (ValidationException e) {
            e.printStackTrace();
        }
        assert (s==null);
        try {
            if ((srepo.update(s2).getId() != "3")) throw new AssertionError();
        } catch (ValidationException e) {
            e.printStackTrace();
        }
        try {
            try {
                srepo.update(null);
            } catch (ValidationException e) {
                e.printStackTrace();
            }
        }
        catch (IllegalArgumentException i){
        }
    }
}
