package domain;

import java.time.LocalDate;

public class StructuraAnUniversitar {
    private StructuraSemestru structuraSemestru;
    private String anUniversitar; //e de forma an1-an2; ex: anUniversitar 2019-2020

    public StructuraAnUniversitar() {
        structuraSemestru = new StructuraSemestru();
        anUniversitar = structuraSemestru.getAnUniversitar();
    }

    /**
     * @param date data curenta
     * @return returneaza semestrul curent in functie de data curenta
     */
    public int getSemestru(LocalDate date) {
        return structuraSemestru.getSemestru(date);
    }

    /**
     *
     * @param date data curenta
     * @return returneaza saptamana curenta din semestru in functie de data curenta
     */
    public int getCurrentWeek(LocalDate date){
        return structuraSemestru.getCurrentWeek(date);
    }
}
