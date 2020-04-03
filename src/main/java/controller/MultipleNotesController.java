package controller;

import domain.Student;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.NotaService;
import services.StudentService;
import services.TemaService;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MultipleNotesController {
    private NotaService notaService;
    private StudentService studentService;
    private TemaService temaService;

    @FXML
    ComboBox<Integer> comboBoxGrupa;
    @FXML
    VBox contentsBox;

    public void setService(NotaService n, StudentService s, TemaService t) {
        notaService = n;
        studentService = s;
        temaService = t;

        comboBoxGrupa.getItems().setAll(getGrupe());

    }

    private List<Integer> getGrupe() {
        return StreamSupport.stream(studentService.findAll().spliterator(), false)
                .map(x -> x.getGrupa()).distinct().collect(Collectors.toList());
    }

    public void initialize() {

    }

    private void addStudentBox() {

        FXMLLoader loader = new FXMLLoader();
        try {
            AnchorPane node  =  loader.load(getClass().getResource("/views/StudentBox.fxml").openStream());
            contentsBox.getChildren().add(node);
            //get the controller
            StudentBoxController controller = (StudentBoxController)loader.getController();
            controller.setContent(comboBoxGrupa.getSelectionModel().getSelectedItem().toString());
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Adaugare mai note multiple");
            Scene scene = new Scene(node);
            dialogStage.setScene(scene);

            dialogStage.show();
        } catch (IOException x) {
            x.printStackTrace();
        }
    }
}
