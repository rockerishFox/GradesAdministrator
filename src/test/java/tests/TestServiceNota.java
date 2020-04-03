package tests;


import domain.Nota;
import domain.Pair;
import domain.Student;
import domain.Tema;
import org.junit.Assert;
import org.junit.Test;
import repositories.FileRepositoryNota;
import repositories.FileRepositoryStudent;
import repositories.FileRepositoryTema;
import services.NotaService;
import services.config.ApplicationContext;
import validators.ValidationException;

import java.util.ArrayList;


public class TestServiceNota {

    private FileRepositoryStudent studentFileRepository = new FileRepositoryStudent(ApplicationContext.getPROPERTIES().getProperty("data.studentsTest"));
    private FileRepositoryTema temaFileRepository = new FileRepositoryTema(ApplicationContext.getPROPERTIES().getProperty("data.temeTest"));
    private FileRepositoryNota notaFileRepository = new FileRepositoryNota(studentFileRepository, temaFileRepository, ApplicationContext.getPROPERTIES().getProperty("data.noteTest"));
    private NotaService notaService = new NotaService(notaFileRepository);

    @Test
    public void noteaza() throws ValidationException {
        Student student = new Student("1","Csiki","Delia",222,"csikidelia@gmail.com","Teofana");
        Tema tema = new Tema("1","desc", "1",7);
        notaService.noteaza(student, tema, "fii mai atenta :)", 0, 7, 0);
        Iterable<Nota> note = notaService.findAll();
        Assert.assertEquals(note.spliterator().getExactSizeIfKnown(), 1);
        notaService.delete(new Pair("1","1"));
    }

    public void filtrari(){
        Student student = new Student("1","Csiki","Delia",222,"csikidelia@gmail.com","Teofana");
        Tema tema1 = new Tema("1","desc1",7);
        Tema tema2 = new Tema("2","desc2",8);
        Nota nota1 = new Nota("22-11-2019","Teofana",student,tema1);
        nota1.setFeedBack("Perfect");
        nota1.setValoare(10);

        ArrayList<Student> array = notaService.filterByTemaId("1");
        assert (array.get(0).equals(student));
        assert (array.size()==1);
        array = notaService.filterByTemaId("2");
        assert (array.size()==0);

        array = notaService.filterByTemaIdAndProf("1","Teofana");
        assert (array.get(0).equals(student));
        assert (array.size()==1);
        array = notaService.filterByTemaIdAndProf("1","Teo");
        assert (array.size()==0);

        ArrayList<String> array2 = notaService.filterByTemaIdAndWeek("1",8);
        String aux = nota1.getStudent().getNume() + " " + nota1.getStudent().getPrenume() + " cu nota: " + nota1.getValoare();

        assert (array2.get(0).equals(aux));
        assert (array2.size()==1);

        array2 = notaService.filterByTemaIdAndWeek("1",4);
        assert (array2.size()==0);

    }

}
