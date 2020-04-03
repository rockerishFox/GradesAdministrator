package domain;

import java.util.Objects;

public class Student extends Entity<String> {
    private String nume;
    private String prenume;
    private int grupa;
    private String email;
    private String CadruDidacticIndrumatorLab;

    public Student(String id, String nume, String prenume, int grupa, String email, String cadruDidacticIndrumatorLab) {
        super(id);
        this.nume = nume;
        this.prenume = prenume;
        this.grupa = grupa;
        this.email = email;
        CadruDidacticIndrumatorLab = cadruDidacticIndrumatorLab;
    }


    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public int getGrupa() {
        return grupa;
    }

    public void setGrupa(int grupa) {
        this.grupa = grupa;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCadruDidacticIndrumatorLab() {
        return CadruDidacticIndrumatorLab;
    }

    public void setCadruDidacticIndrumatorLab(String cadruDidacticIndrumatorLab) {
        CadruDidacticIndrumatorLab = cadruDidacticIndrumatorLab;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return grupa == student.grupa &&
                Objects.equals(nume, student.nume) &&
                Objects.equals(prenume, student.prenume) &&
                Objects.equals(email, student.email) &&
                Objects.equals(CadruDidacticIndrumatorLab, student.CadruDidacticIndrumatorLab);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nume, prenume, grupa, email, CadruDidacticIndrumatorLab);
    }

    @Override
    public String toString() {
        return this.getId() + " " + nume + " " + prenume + " " + grupa + " " + email + " " + CadruDidacticIndrumatorLab ;
    }
}

