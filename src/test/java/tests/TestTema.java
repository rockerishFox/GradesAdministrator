package tests;


import domain.Student;
import domain.Tema;
import org.junit.Test;


public class TestTema {
    private Tema[] generateTema(){
        Student s=new Student("1","Crisan","Bianca",222,"crisanbia@gmail.com","Teofana");
        Student s1=new Student("2","Birle","Damaris",221,"birledama@gmail.com","Teofana");
        Tema t1=new Tema("1","descriere1", "1",13);
        Tema t2=new Tema("2","descriere2","2",10);
        Tema[] teme={t1,t2};
        return teme;
    }


    @Test
    public void getDescriere() {
        Tema[] teme=generateTema();
        assert(teme[0].getDescriere()=="descriere1");
    }

    @Test
    public void setDescriere() {
        Tema[] teme=generateTema();
        teme[0].setDescriere("descr");
        assert(teme[0].getDescriere()=="descr");
    }

    @Test
    public void getStartWeek() {
        Tema[] teme=generateTema();
        assert(teme[0].getStartWeek()==5);
    }


    @Test
    public void getEndWeek() {
        Tema[] teme=generateTema();
        assert(teme[0].getEndWeek()==13);

    }

    @Test
    public void setEndWeek() {
        Tema[] teme=generateTema();
        teme[0].setEndWeek(11);
        assert(teme[0].getEndWeek()==11);
    }
}
