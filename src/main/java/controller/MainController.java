package controller;

import domain.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import services.NotaService;
import services.StudentService;
import services.TemaService;
import validators.ValidationException;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class MainController {

    @FXML private StudentController studentController;
    @FXML private  NoteController noteController;
    @FXML private Tab studentTab;
    @FXML private Tab noteTab;


    public void setService(StudentService s,TemaService t, NotaService n) throws IOException {
        studentController.setService(s);
        noteController.setService(s,t,n);

//
//        FXMLLoader loader2 = new FXMLLoader();
//        loader2.setLocation(getClass().getResource("/views/NoteView.fxml"));
//        NoteController noteController = loader2.getController();
//        noteController.setService(studentService,temaService,notaService);
    }

    @FXML private void initialize() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/StudentTabView.fxml"));

        try {
            AnchorPane anch1 = loader.load();
            studentController = loader.getController();
            studentTab.setContent(anch1);

        } catch (Exception e) {

        }

        FXMLLoader loaderNote = new FXMLLoader(getClass().getResource("/views/NoteTabView.fxml"));

        try {
            AnchorPane anch2 = loaderNote.load();
            noteController = loaderNote.getController();
            noteTab.setContent(anch2);

        } catch (Exception e) {

        }
    }
}
