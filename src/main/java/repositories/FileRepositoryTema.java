package repositories;

import domain.Tema;
import validators.TemaValidator;

public class FileRepositoryTema extends AbstractFileRepository<String, Tema> {
    public FileRepositoryTema(String filepath) {
        super(new TemaValidator(), filepath);
    }

    @Override
    public Tema parseLine(String linie) {
        String[] l = linie.split(";");
        Tema t = null;

        try {
            t = new Tema(l[0], l[1], Integer.parseInt(l[3]));
            t.setStartWeek(Integer.parseInt(l[2]));

        } catch (NumberFormatException x){
        }

        return t;
    }

    @Override
    public String outputString(Tema tema) {
        return tema.getId() + ";" +
                tema.getDescriere() + ";" +
                tema.getStartWeek() + ";" +
                tema.getEndWeek();
    }
}
