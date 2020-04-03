import UI.UI;
import repositories.*;
import services.NotaService;
import services.StudentService;
import services.TemaService;
import services.config.ApplicationContext;

import java.io.IOException;

public class Main {

    public static void runConsole() throws IOException {
        XMLRepositoryStudent studentRepo = new XMLRepositoryStudent(
                ApplicationContext.getPROPERTIES().getProperty("data.studentsXML")
        );
        XMLRepositoryTema temaRepo = new XMLRepositoryTema(
                ApplicationContext.getPROPERTIES().getProperty("data.temeXML")
        );
        XMLRepositoryNota notaRepo = new XMLRepositoryNota(
                ApplicationContext.getPROPERTIES().getProperty("data.noteXML"),
                ApplicationContext.getPROPERTIES().getProperty("data.studentsXML"),
                ApplicationContext.getPROPERTIES().getProperty("data.temeXML")
        );
        StudentService studentService = new StudentService(studentRepo);
        TemaService temaService = new TemaService(temaRepo);
        NotaService notaService = new NotaService(notaRepo);
        UI ui = new UI(studentService, temaService, notaService);
        ui.run();
    }

    public static void runGui(String[] args){
        MainApp.main(args);
    }

    public static void main(String[] args) throws IOException {
        //runConsole();
        runGui(args);
    }
}
