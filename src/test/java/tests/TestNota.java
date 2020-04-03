package tests;

import domain.Nota;
import domain.Student;
import domain.Tema;
import org.junit.Assert;
import org.junit.Test;

public class TestNota {


    @Test
    public void testNota() {

        Student s=new Student("1","Crisan","Bianca",222,"crisanbia@gmail.com","Teofana");
        Student s1=new Student("2","Meow","Damaris",221,"meowmoew@gmail.com","Teofana");
        
        Tema t1=new Tema("1","descriere1", "1",13);
        Tema t2=new Tema("2","descriere2","2",10);
        
        Nota nota=new Nota("06.11.2019","Andreea",s,t1);
        nota.setNota(8.0f,0,0);
        assert(nota.getValoare()==8.0);
        
        nota.setDate("30.10.2019");
        nota.setProfesor("Camelia");
        nota.setStudent(s1);
        nota.setTema(t2);
        assert(nota.getStudent().getNume().equals("Meow"));
        assert(nota.getTema().getEndWeek()==10);
        assert(nota.getDate().equals("30.10.2019"));
        assert(nota.getWeek()==5);
        assert(nota.getProfesor().equals("Camelia"));

    }
}