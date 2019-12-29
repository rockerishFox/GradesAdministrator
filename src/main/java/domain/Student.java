package domain;

public class Student extends Entity<String>{
    private String nume;
    private String prenume;
    private String profesor;
    private String grupa;

    public Student(String id, String nume, String prenume, String profesor, String grupa) {
        super(id);
        this.nume = nume;
        this.prenume = prenume;
        this.profesor = profesor;
        this.grupa = grupa;
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

    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    public String getGrupa() {
        return grupa;
    }

    public void setGrupa(String grupa) {
        this.grupa = grupa;
    }
}
