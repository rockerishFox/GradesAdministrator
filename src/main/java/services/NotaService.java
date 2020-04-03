package services;

import domain.*;
import repositories.CrudRepository;
import utils.Constants;
import validators.ValidationException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class NotaService extends Service<Pair, Nota> {

    private CrudRepository<Pair, Nota> notaRepo;

    public NotaService(CrudRepository<Pair, Nota> repository) {
        super(repository);
        notaRepo = repository;
    }

    private void exportNota(Nota nota) {
        String filepath = "data\\" + nota.getStudent().getNume() + nota.getStudent().getPrenume() + ".txt";
        File file = new File(filepath);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filepath, true));

            writer.write("Tema: " + nota.getTema().getId());
            writer.newLine();

            writer.write("Nota: " + Float.toString(nota.getValoare()));
            writer.newLine();

            writer.write("Predata in saptamana: " + Integer.toString(nota.getWeek()));
            writer.newLine();

            writer.write("Feedback: " + nota.getFeedBack());
            writer.newLine();

            writer.close();
        } catch (IOException e) {
        }
    }

    public Nota noteaza(Student st, Tema tema, String feedback, int motivari, float valoare, int intarzieriProf) throws ValidationException, IllegalAccessException {

        Pair notaId = new Pair(st.getId(), tema.getId());
        Nota notaFound = notaRepo.findOne(notaId);

        Nota nota = new Nota(LocalDate.now().format(Constants.DATE_FORMATTER), st.getCadruDidacticIndrumatorLab(), st, tema);
        nota.setFeedBack(feedback);
        nota.setNota(valoare, motivari, intarzieriProf);

        if (notaFound == null) {
            exportNota(nota);
            return notaRepo.save(nota);
        }
        return nota;
    }

    public Nota noteazaNota(LocalDate data, Student st, Tema tema, String feedback, int motivari, float valoare, int intarzieriProf) throws ValidationException, IllegalAccessException {

        Pair notaId = new Pair(st.getId(), tema.getId());
        Nota notaFound = notaRepo.findOne(notaId);

        Nota nota = new Nota(data.format(Constants.DATE_FORMATTER), st.getCadruDidacticIndrumatorLab(), st, tema);
        nota.setFeedBack(feedback);
        nota.setNota(valoare, motivari, intarzieriProf);

        if (notaFound == null) {
            exportNota(nota);
            return notaRepo.save(nota);
        }
        return nota;
    }

    /**
     *  returneaza numarul penalizarilor (0,1,2) pentru data respectiva la tema data
     *      sau -1 altfel
     * @param t = tema data
     * @param data = data predarii
     * @return 0,1,2 = numarul de penalizari
     *          -1 = altfel (aici intra si cazul in care s-a predat tema dupa mai mai mult de 2 saptamani)
     */
    public int getPenality(Tema t,LocalDate data) {
        StructuraSemestru str = new StructuraSemestru();

        if (str.getCurrentWeek(data) <= t.getEndWeek())
            return 0;
        if (str.getCurrentWeek(data) == t.getEndWeek() + 1)
            return 1;
        else if (str.getCurrentWeek(data) == t.getEndWeek() + 2)
            return 2;
        else if (str.getCurrentWeek(data) > t.getEndWeek() +2)
            return -1;

        return -1;
    }


    public ArrayList<Student> filterByTemaId(String id_tema) {
        ArrayList<Student> array = new ArrayList<>();
        StreamSupport.stream(notaRepo.findAll().spliterator(), false)
                .filter(x -> x.getTema().getId().equals(id_tema))
                .forEach(x -> array.add(x.getStudent()));
        return array;
    }

    public ArrayList<Student> filterByTemaIdAndProf(String id_tema, String prof) {
        ArrayList<Student> array = new ArrayList<>();
        StreamSupport.stream(notaRepo.findAll().spliterator(), false)
                .filter(x -> x.getTema().getId().equals(id_tema))
                .filter(x -> x.getProfesor().equals(prof))
                .forEach(x -> array.add(x.getStudent()));
        return array;
    }

    public ArrayList<String> filterByTemaIdAndWeek(String id, int week) {
        ArrayList<String> array = new ArrayList<>();

        StreamSupport.stream(notaRepo.findAll().spliterator(), false)
                .filter(x -> x.getTema().getId().equals(id))
                .filter(x -> x.getWeek() == week)
                .forEach(x -> {
                    array.add(x.getStudent().getNume() + " " +
                            x.getStudent().getPrenume() + " cu nota: " + x.getValoare());
                });
        return array;
    }

    public List<Nota> noteStudent(Student st){
        List<Nota> note = StreamSupport.stream(this.findAll().spliterator(),false).collect(Collectors.toList());
        return note.stream().filter(
                x->x.getStudent().getId().equals(st.getId())
        ).distinct().collect(Collectors.toList());
    }
    public List<Nota> noteTema(Tema tema){
        List<Nota> note = StreamSupport.stream(this.findAll().spliterator(),false).collect(Collectors.toList());
        return note.stream().filter(
                x->x.getTema().getId().equals(tema.getId())
        ).distinct().collect(Collectors.toList());
    }

}
