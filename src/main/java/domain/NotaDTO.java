package domain;

public class NotaDTO {

    private String studentName;
    private String studentPrenume;
    private String studentAllName;

    private String temaId;
    private double notaValoare;
    private String profesor;
    private int grupa;

    private Student student;
    private Tema tema;
    private Nota nota;


    private int penalitati;

    public NotaDTO(Nota n){
        studentName = n.getStudent().getNume();
        studentPrenume = n.getStudent().getPrenume();
        studentAllName = studentName + " " + studentPrenume;
        temaId = n.getTema().getId();
        notaValoare = n.getValoare();
        profesor = n.getProfesor();
        grupa = n.getStudent().getGrupa();

        student = n.getStudent();
        tema = n.getTema();
        nota = n;

        penalitati = n.getPenalitati();
    }

    public int getPenalitati() {
        return penalitati;
    }

    public void setPenalitati(int penalitati) {
        this.penalitati = penalitati;
    }

    public Nota getNota() {
        return nota;
    }

    public void setNota(Nota nota) {
        this.nota = nota;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Tema getTema() {
        return tema;
    }

    public void setTema(Tema tema) {
        this.tema = tema;
    }

    public String getStudentPrenume() {
        return studentPrenume;
    }

    public void setStudentPrenume(String studentPrenume) {
        this.studentPrenume = studentPrenume;
    }

    public String getStudentAllName() {
        return studentAllName;
    }

    public void setStudentAllName(String studentAllName) {
        this.studentAllName = studentAllName;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getTemaId() {
        return temaId;
    }

    public void setTemaId(String temaId) {
        this.temaId = temaId;
    }

    public double getNotaValoare() {
        return notaValoare;
    }

    public void setNotaValoare(double notaValoare) {
        this.notaValoare = notaValoare;
    }

    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    public int getGrupa() {
        return grupa;
    }

    public void setGrupa(int grupa) {
        this.grupa = grupa;
    }
}
