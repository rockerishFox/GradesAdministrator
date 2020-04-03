package tests;

import domain.Student;
import domain.Tema;

import org.junit.Test;
import repositories.TemaRepository;
import validators.ValidationException;

public class TestTemaRepository {
    TemaRepository generate(){
        TemaRepository trepo=new TemaRepository();
        Student s=new Student("1","Crisan","Bianca",222,"crisanbia@gmail.com","Teofana");
        Student s1=new Student("2","Birle","Damaris",221,"birledama@gmail.com","Teofana");
        Tema t1=new Tema("1","descriere1", "1",13);
        Tema t2=new Tema("2","descriere2","2",10);
        try {
            trepo.save(t1);
        } catch (ValidationException e) {
            e.printStackTrace();
        }
        try {
            trepo.save(t2);
        } catch (ValidationException e) {
            e.printStackTrace();
        }
        return trepo;
    }
    @Test
    public void findOne() {
        TemaRepository trepo=generate();
        Tema t=trepo.findOne("1");
        assert (t.getDescriere()=="descriere1");
        assert (trepo.findOne("3")==null);
        try {
            Tema t1=trepo.findOne(null);
        }
        catch (IllegalArgumentException i){

        }
    }

    @Test
    public void findAll() {
        TemaRepository trepo=generate();
        Iterable<Tema> tema=trepo.findAll();
        assert(tema.spliterator().getExactSizeIfKnown()==2);
    }

    @Test
    public void save() {
        TemaRepository trepo=generate();
        Student s=new Student("1","Crisan","Bianca",222,"crisanbia@gmail.com","Teofana");
        Tema t2=new Tema("3","greu","1",6);
        try {
            if ((trepo.save(t2) != null)) throw new AssertionError();
        } catch (ValidationException e) {
            e.printStackTrace();
        }
        Iterable<Tema> tema=trepo.findAll();
        assert(tema.spliterator().getExactSizeIfKnown()==3);
        try {
            if ((trepo.save(t2) == null)) throw new AssertionError();
        } catch (ValidationException e) {
            e.printStackTrace();
        }
        try {
            trepo.save(null);
        }
        catch (IllegalArgumentException i){
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void delete() {
        TemaRepository trepo=generate();
        Tema t=trepo.delete("1");
        assert (t.getId()=="1");
        assert (trepo.delete("4")==null);
        Iterable<Tema> tema=trepo.findAll();
        assert(tema.spliterator().getExactSizeIfKnown()==1);
    }

    @Test
    public void update() {
        TemaRepository trepo=generate();
        Student s=new Student("1","Crisan","Bianca",222,"crisanbia@gmail.com","Teofana");
        Tema t2=new Tema("1","usor","1",6);
        Tema t22=new Tema("12","usor","1",6);
        Tema t= null;
        try {
            t = trepo.update(t2);
        } catch (ValidationException e) {
            e.printStackTrace();
        }
        assert (t==null);
        try {
            if ((trepo.update(t22).getId() != "12")) throw new AssertionError();
        } catch (ValidationException e) {
            e.printStackTrace();
        }
        try {
            try {
                trepo.update(null);
            } catch (ValidationException e) {
                e.printStackTrace();
            }
        }
        catch (IllegalArgumentException i){
        }
    }
}
