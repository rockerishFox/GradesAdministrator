package domain;

import utils.Constants;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Nota extends Entity<Pair> {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private String data;
    private String profesor;
    private Student student;
    private Tema tema;
    private float valoare;
    private String feedBack;
    private StructuraAnUniversitar structuraAnUniversitar;
    private int penalitati;

    public Nota(String data, String profesor, Student student, Tema tema) {
        super(new Pair(student.getId(),tema.getId()));

        this.data = data;

        this.profesor = profesor;
        this.student = student;
        this.tema = tema;
        this.valoare = 1;
        this.feedBack = "nenotat";
        this.penalitati = 0;

        structuraAnUniversitar = new StructuraAnUniversitar();
    }

    /**
     * determina numarul de puncte care se vor scadea din nota in functie de cate intarzieri a avut studentul la tema respectiva
     *
     * @return un numar natural mai mare sau egal cu 0
     */
    public int intarzieri() {

        return structuraAnUniversitar.getCurrentWeek(LocalDate.parse(data, Constants.DATE_FORMATTER)) - this.getTema().getEndWeek();

    }

    /**
     * seteaza valoarea notei
     * nota dupa setare va avea nota data de profesor
     * daca studentul nu a respectat deadline-ul, i se va scadea cate un punct pentru fiecare saptamna intarziata
     * daca studentul are mai mult de 2 intarzieri, nota temei va fi 1
     * daca studentul a lipsit motivat si nu a predat tema la timp, atunci nu este depunctat si poate preda chiar daca au trecut cele 2 saptamani de la deadline
     *
     * @param valoareData    numar rational, valoarea data de profseor
     * @param motivari       - numar intreg, numarul de motivari al studentului
     * @param intarzieriProf - cate saptamani a uitat profesorul sa treaca nota studentului
     */
    public void setNota(float valoareData, int motivari, int intarzieriProf) {
        this.valoare = valoareData;
        int penalizari = intarzieri();
        if(penalizari < 0) penalizari = 0;
        if (penalizari > 2 && motivari == 0 && intarzieriProf == 0) this.valoare = 1;
        else {
            if (penalizari - motivari - intarzieriProf > 2) this.valoare = 1;
            else {
                float fin = this.valoare - penalizari + motivari + intarzieriProf;
                if (fin > 10)
                    this.valoare = 10;
                else
                    this.valoare = fin;
            }
        }

    }

    public int getWeek() {

        return structuraAnUniversitar.getCurrentWeek(LocalDate.parse(data, Constants.DATE_FORMATTER));
    }


    public int getPenalitati() {
        return penalitati;
    }

    public void setPenalitati(int penalitati) {
        this.penalitati = penalitati;
    }

    public String getDate() {
        return data;
    }

    public String getProfesor() {
        return profesor;
    }

    public String getFeedBack() {
        return this.feedBack;
    }

    public Student getStudent() {
        return student;
    }

    public Tema getTema() {
        return tema;
    }

    public float getValoare() {
        return valoare;
    }

    public void setDate(String date) {
        this.data = date;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public void setTema(Tema tema) {
        this.tema = tema;
    }

    public void setValoare(float valoare) {
        this.valoare = valoare;
    }

    public void setFeedBack(String f) {
        this.feedBack = f;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Nota nota = (Nota) o;
        return Float.compare(nota.valoare, valoare) == 0 &&
                Objects.equals(data, nota.data) &&
                Objects.equals(profesor, nota.profesor) &&
                Objects.equals(student, nota.student) &&
                Objects.equals(tema, nota.tema);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, profesor, student, tema, valoare);
    }

    @Override
    public String toString() {

        return student.getId().toString() + " "+ tema.getId().toString() + " " +
                data.toString() + " " + profesor + " " + valoare + " " + feedBack;

    }
}
