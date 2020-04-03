package tests;

import org.junit.Test;
import domain.Student;

public class TestStudent {
    private Student[] generateStudents(){
        Student s=new Student("1","Crisan","Bianca",222,"crisanbia@gmail.com","Teofana");
        Student s1=new Student("2","Birle","Damaris",221,"birledama@gmail.com","Teofana");
        Student s2=new Student("3","Csiki","Delia",222,"csikidelia@gmail.com","Camelia");
        Student[] students={s,s1,s2};
        return students;
    }

    @Test
    public void getName() {
        Student[] s=generateStudents();
        assert(s[0].getNume().compareTo("Crisan")==0);
        assert(s[1].getNume().compareTo("Birle")==0);
        assert(s[2].getNume().compareTo("Csiki")==0);
    }

    @Test
    public void setName() {
        Student[] s=generateStudents();
        s[0].setNume("Cristescu");
        assert (s[0].getNume().compareTo("Cristescu")==0);
    }

    @Test
    public void getPrenume() {
        Student[] s=generateStudents();
        assert (s[0].getPrenume().compareTo("Bianca")==0);
        assert (s[1].getPrenume().compareTo("Bianca")!=0);
    }

    @Test
    public void setPrenume() {
        Student[] s=generateStudents();
        s[0].setPrenume("Eliza");
        assert (s[0].getPrenume().compareTo("Eliza")==0);
    }

    @Test
    public void getEmail() {
        Student[] s=generateStudents();
        assert (s[0].getEmail().compareTo("crisanbia@gmail.com")==0);
        assert (s[1].getEmail().compareTo("crisanbia@gmail.com")!=0);
    }

    @Test
    public void setEmail() {
        Student[] s=generateStudents();
        s[0].setEmail("cristescubianca@gmail.com");
        assert (s[0].getEmail().compareTo("cristescubianca@gmail.com")==0);
    }

    @Test
    public void getCadruDidacticIndrumatorLab() {
        Student[] s=generateStudents();
        assert (s[0].getCadruDidacticIndrumatorLab().compareTo("Teofana")==0);
        assert (s[2].getCadruDidacticIndrumatorLab().compareTo("Teofana")!=0);
    }

    @Test
    public void setCadruDidacticIndrumatorLab() {
        Student[] s=generateStudents();
        s[0].setCadruDidacticIndrumatorLab("Carmen");
        assert (s[0].getCadruDidacticIndrumatorLab().compareTo("Carmen")==0);
    }

    @Test
    public void getGrupa() {
        Student[] s=generateStudents();
        assert (s[0].getGrupa() == 222);
        assert (s[2].getGrupa() != 221);
    }

    @Test
    public void setGrupa() {
        Student[] s=generateStudents();
        s[0].setGrupa(224);
        assert (s[0].getGrupa() == 224);
    }

    @Test
    public void testEquals() {
        Student[] s=generateStudents();
        assert(s[0].equals(s[0])==true);
        assert(s[1].equals(s[0])==false);
    }


}
