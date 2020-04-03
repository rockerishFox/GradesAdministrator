package domain;

import java.time.LocalDate;
import java.util.Objects;

public class Tema extends Entity<String> {
    private String descriere;
    private String id_st;
    private int startWeek;
    private int endWeek;
    private StructuraAnUniversitar anUniv;

    public Tema(String ID, String descriere, String s, int endWeek) {
        super(ID);
        this.id_st=s;
        this.descriere = descriere;
        this.anUniv=new StructuraAnUniversitar();
        this.startWeek = this.anUniv.getCurrentWeek(LocalDate.now());
        this.endWeek = endWeek;
    }

    public Tema(String ID, String descriere,  int endWeek) {
        super(ID);
        this.descriere = descriere;
        this.anUniv=new StructuraAnUniversitar();
        this.startWeek = this.anUniv.getCurrentWeek(LocalDate.now());
        this.endWeek = endWeek;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public int getStartWeek() {
        return startWeek;
    }


    public int getEndWeek() {
        return endWeek;
    }

    public void setEndWeek(int endWeek) {
        this.endWeek = endWeek;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tema tema = (Tema) o;
        return startWeek == tema.startWeek &&
                endWeek == tema.endWeek &&
                Objects.equals(descriere, tema.descriere);
    }

    @Override
    public int hashCode() {
        return Objects.hash( descriere, startWeek, endWeek);
    }

    @Override
    public String toString() {
        return this.getId() + " " + descriere + " " + startWeek + " " + endWeek ;
    }

    public String getStudent() {
        return this.id_st;
    }

    public void setStartWeek(int sw) { this.startWeek = sw;
    }
}
