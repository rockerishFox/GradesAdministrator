package domain;

import services.config.ApplicationContext;
import utils.Constants;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;

public class StructuraSemestru {
    private String filename = ApplicationContext.getPROPERTIES().getProperty("data.catalog.structurasemestru");
    private String anUniversitar;
    //private int semestru;
    private LocalDate startFirstSemester;
    private LocalDate beginHolydayFirstSemester;
    private LocalDate endHolydayFirstSemester;
    private LocalDate endFirstSemester;
    private LocalDate startSecondSemester;
    private LocalDate beginHolidaySecondSemester;
    private LocalDate endHolidaySecondSemester;
    private LocalDate endSecondSemester;


    /**
     * citeste din fisier datele corespunzatoare inceputului si sfarsitului semestrelor, precum si datele vacantelor
     *
     * @return o lista cu liniile citite din fisier
     */
    private List<String> read() {
        Path path = Paths.get(filename);
        List<String> lines = null;
        try {
            lines = Files.readAllLines(path);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return lines;
    }

    public StructuraSemestru() {
        List<String> lines = read();
        this.anUniversitar = lines.get(0);
        this.startFirstSemester = LocalDate.parse(lines.get(1), Constants.DATE_FORMATTER);
        this.beginHolydayFirstSemester = LocalDate.parse(lines.get(2), Constants.DATE_FORMATTER);
        this.endHolydayFirstSemester = LocalDate.parse(lines.get(3), Constants.DATE_FORMATTER);
        this.endFirstSemester = LocalDate.parse(lines.get(4), Constants.DATE_FORMATTER);
        this.startSecondSemester = LocalDate.parse(lines.get(5), Constants.DATE_FORMATTER);
        this.beginHolidaySecondSemester = LocalDate.parse(lines.get(6), Constants.DATE_FORMATTER);
        this.endHolidaySecondSemester = LocalDate.parse(lines.get(7), Constants.DATE_FORMATTER);
        this.endSecondSemester = LocalDate.parse(lines.get(8), Constants.DATE_FORMATTER);
        //this.semestru = numarSemestru;
    }

    /**
     * determina carui semestru apartine o data curenta
     *
     * @param data data curenta
     * @return returneaza 1 - daca data apartine primului semestru, respectiv 2, daca apartine celui de-al doilea semestru, si 0 daca nu apartine niciunui semestru
     */
    public int getSemestru(LocalDate data) {

        if (data == null)
            return 0;
        if (data.isAfter(this.getStartFirstSemester()) && data.isBefore(this.getEndFirstSemester())) return 1;
        if (data.isAfter(this.getStartSecondSemester()) && data.isBefore(this.getEndSecondSemester())) return 2;

        return 0;
    }

    /**
     * determina saptamana curenta din semestru avand o data curenta
     *
     * @param data data curenta
     * @return un numar intreg cuprins intre 1 si 14, reprezentand saptamana curenta din semestru, sau 0 daca data nu apartine niciunui smestru
     */
    public int getCurrentWeek(LocalDate data) {
        LocalDate startSemester = null;
        LocalDate endSemester = null;
        LocalDate startHoliday = null;
        LocalDate endHoliday = null;

        // data nu apartine niciunui semestru
        if (getSemestru(data) == 0)
            return 0;
        // data apartine de primul semestru
        if (getSemestru(data) == 1) {
            // initializam startul semestrului si vacantei
            startSemester = this.getStartFirstSemester();
            endSemester = this.getEndFirstSemester();
            startHoliday = this.getBeginHolydayFirstSemester();
            endHoliday = this.getEndHolydayFirstSemester();
        }
        // data apartine de al doilea semestru
        else if (getSemestru(data) == 2) {
            // initializam startul semestrului si vacantei
            startSemester = this.getStartSecondSemester();
            endSemester = this.getEndSecondSemester();
            startHoliday = this.getBeginHolidaySecondSemester();
            endHoliday = this.getEndHolidaySecondSemester();

        }
        // startWeek = numarul saptamanii din an corespunzator inceputului semestrului
        int startWeek = startSemester.get(WeekFields.of(Locale.GERMANY).weekOfWeekBasedYear());
        // currentWeekOftheYear = numarul saptamanii din an corespunzator datei primite
        int currentWeekOftheYear = data.get(WeekFields.of(Locale.GERMANY).weekOfWeekBasedYear()); //saptamana in care ne aflam in an;

        // Cazul in care data coincide cu inceputul semestrului
        if (data.isEqual(startSemester))
            return 1;
        // Cazul in care data este inainte de vacanta
        if (data.isAfter(startSemester) && data.isBefore(startHoliday))
            return currentWeekOftheYear - startWeek + 1;

        // Cazul in care data este in vacanta
        if (data.isAfter(startHoliday) && data.isBefore(endHoliday))
        {
            int currentFakeWeek = startHoliday.get(WeekFields.of(Locale.GERMANY).weekOfWeekBasedYear());
            return currentFakeWeek - startWeek + 1;
        }
        else if (data.isEqual(startHoliday))
            return currentWeekOftheYear - startWeek + 1;
        else if (data.isEqual(endHoliday)) {
            // daca data este in semestrul 1, endweek coincide cu prima saptamana din an
            if (startSemester.isEqual(getStartFirstSemester())) {
                int currentFakeWeek = startHoliday.get(WeekFields.of(Locale.GERMANY).weekOfWeekBasedYear());
                return currentFakeWeek - startWeek + 1;
            }
            // daca data este in semestrul 2
            else {
                return currentWeekOftheYear - startWeek;
            }
        }

        // Cazul in care data este in semestrul 1 si dupa vacanta
        if (data.isAfter(endHoliday) && startSemester == getStartFirstSemester()) {
            // Luam saptamana la care am ramas in anul trecut
            int currentFakeWeek = startHoliday.get(WeekFields.of(Locale.GERMANY).weekOfWeekBasedYear());
            int semesterWeek = currentFakeWeek - startWeek + 1;
            return semesterWeek + currentWeekOftheYear - 1;
        }
        // Cazul in care data este in semestrul 2 si dupa vacanta
        if (data.isAfter(endHoliday) && data.isBefore(endSemester)) {
            return currentWeekOftheYear - startWeek - 1;
        }

        if (data.isEqual(endSemester))
            return currentWeekOftheYear - startWeek + 1;

        return 0;
    }


    public String getAnUniversitar() {
        return anUniversitar;
    }

//    public int getSemestru() {
//        return semestru;
//    }

    public LocalDate getStartFirstSemester() {
        return startFirstSemester;
    }

    public LocalDate getBeginHolydayFirstSemester() {
        return beginHolydayFirstSemester;
    }

    public LocalDate getEndHolydayFirstSemester() {
        return endHolydayFirstSemester;
    }

    public LocalDate getEndFirstSemester() {
        return endFirstSemester;
    }

    public LocalDate getStartSecondSemester() {
        return startSecondSemester;
    }

    public LocalDate getBeginHolidaySecondSemester() {
        return beginHolidaySecondSemester;
    }

    public LocalDate getEndHolidaySecondSemester() {
        return endHolidaySecondSemester;
    }

    public LocalDate getEndSecondSemester() {
        return endSecondSemester;
    }

    public void setAnUniversitar(String anUniversitar) {
        this.anUniversitar = anUniversitar;
    }

//    public void setSemestru(int semestru) {
//        this.semestru = semestru;
//    }

    public void setStartFirstSemester(LocalDate startFirstSemester) {
        this.startFirstSemester = startFirstSemester;
    }

    public void setBeginHolydayFirstSemester(LocalDate beginHolydayFirstSemester) {
        this.beginHolydayFirstSemester = beginHolydayFirstSemester;
    }

    public void setEndHolydayFirstSemester(LocalDate endHolydayFirstSemester) {
        this.endHolydayFirstSemester = endHolydayFirstSemester;
    }

    public void setEndFirstSemester(LocalDate endFirstSemester) {
        this.endFirstSemester = endFirstSemester;
    }

    public void setStartSecondSemester(LocalDate startSecondSemester) {
        this.startSecondSemester = startSecondSemester;
    }

    public void setBeginHolidaySecondSemester(LocalDate beginHolidaySecondSemester) {
        this.beginHolidaySecondSemester = beginHolidaySecondSemester;
    }

    public void setEndHolidaySecondSemester(LocalDate endHolidaySecondSemester) {
        this.endHolidaySecondSemester = endHolidaySecondSemester;
    }

    public void setEndSecondSemester(LocalDate endSecondSemester) {
        this.endSecondSemester = endSecondSemester;
    }

}

