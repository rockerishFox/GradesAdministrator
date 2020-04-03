import controller.MainController;

import controller.StudentController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import repositories.XMLRepositoryNota;
import repositories.XMLRepositoryStudent;
import repositories.XMLRepositoryTema;
import services.NotaService;
import services.StudentService;
import services.TemaService;
import services.config.ApplicationContext;

import java.io.IOException;

public class MainApp extends Application {
    StudentService studentService;
    NotaService notaService;
    TemaService temaService;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
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
        studentService = new StudentService(studentRepo);
        temaService = new TemaService(temaRepo);
        notaService = new NotaService(notaRepo);

        loadInitialStage(primaryStage);
        primaryStage.show();
    }

    public void loadInitialStage(Stage primaryStage) throws IOException {

        primaryStage.setTitle("MAP Application");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/views/MainView.fxml"));
        AnchorPane layout = loader.load();
        primaryStage.setScene(new Scene(layout));

        MainController main = loader.getController();
        main.setService(studentService, temaService, notaService);
    }

}
