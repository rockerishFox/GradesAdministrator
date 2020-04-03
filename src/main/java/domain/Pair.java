package domain;

import java.util.Objects;

public class Pair {
    private String stanga;
    private String dreapta;

    public Pair(String st, String dr) {
        this.stanga = st;
        this.dreapta = dr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair pereche = (Pair) o;
        return Objects.equals(stanga, pereche.stanga) &&
                Objects.equals(dreapta, pereche.dreapta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stanga, dreapta);
    }

    public String getStanga()
    {
        return this.stanga;
    }

    public String getDreapta() {
        return this.dreapta;
    }

    public void setStanga(String st) {
        this.stanga = st;
    }

    public void setDreapta(String dr) {
        this.dreapta = dr;
    }
}
