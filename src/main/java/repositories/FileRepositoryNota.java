package repositories;

import domain.Nota;
import domain.Pair;
import domain.Student;
import domain.Tema;
import services.config.ApplicationContext;
import validators.NotaValidator;

public class FileRepositoryNota extends AbstractFileRepository<Pair, Nota>{
    public FileRepositoryNota(FileRepositoryStudent studentRepo, FileRepositoryTema temaRepo, String filepath) {
        super(new NotaValidator(), filepath);
    }

    @Override
    public Nota parseLine(String linie) {
        try {
            FileRepositoryStudent studRepo = new FileRepositoryStudent(ApplicationContext.getPROPERTIES().getProperty("data.students"));
            FileRepositoryTema temaRepo = new FileRepositoryTema(ApplicationContext.getPROPERTIES().getProperty("data.teme"));
            String[] l = linie.split(";");
            String studentId = l[0];
            String temaId = l[1];
            Student student = studRepo.findOne(studentId);
            Tema tema = temaRepo.findOne(temaId);
            String date = l[2];
            String numeProfesor = l[3];
            Nota nota = new Nota(date, numeProfesor, student, tema);
            Float valoare = Float.parseFloat(l[4]);
            nota.setValoare(valoare);
            String feedback = l[5];
            nota.setFeedBack(feedback);
            nota.setId(new Pair(studentId, temaId));
            return nota;
        } catch(NumberFormatException | IllegalAccessException e){
            return null;
        }
    }

    @Override
    public String outputString(Nota entity) {
        return entity.getStudent().getId() + ";" +
                entity.getTema().getId() + ";" +
                entity.getDate() + ";" +
                entity.getProfesor() + ";" +
                entity.getValoare() + ";" +
                entity.getFeedBack();
    }

}
