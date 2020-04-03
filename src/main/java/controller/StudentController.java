package controller;

import domain.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import services.NotaService;
import services.StudentService;
import services.TemaService;
import validators.ValidationException;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class StudentController {
    StudentService studentService;
    ObservableList<Student> model = FXCollections.observableArrayList();

    @FXML
    TableView tableViewStudent;
    @FXML
    TableColumn<Student, String> tableViewColumnId;
    @FXML
    TableColumn<Student, String> tableViewColumnNume;
    @FXML
    TableColumn<Student, String> tableViewColumnPrenume;
    @FXML
    TableColumn<Student, String> tableViewColumnGrupa;
    @FXML
    TableColumn<Student, String> tableViewColumnMail;
    @FXML
    TableColumn<Student, String> tableViewColumnProf;

    @FXML
    TextField textFieldSearchNume;
    @FXML
    TextField textFieldSearchGrupa;
    @FXML
    TextField textFieldSearchProf;


    @FXML
    TextField textFieldStudentId;
    @FXML
    TextField textFieldStudentNume;
    @FXML
    TextField textFieldStudentPrenume;
    @FXML
    TextField textFieldStudentGrupa;
    @FXML
    TextField textFieldStudentProf;
    @FXML
    TextField textFieldStudentMail;


    public void setService(StudentService studentService1) {
        studentService = studentService1;
        initializeModel();

        textFieldSearchGrupa.textProperty().addListener(
                (x, y, z) -> handleFilter()
        );
        textFieldSearchNume.textProperty().addListener(
                (x, y, z) -> handleFilter()
        );
        textFieldSearchProf.textProperty().addListener(
                (x, y, z) -> handleFilter()
        );
    }


    private List<Student> getAllStudents() {
        Stream<Student> s = (Stream<Student>) StreamSupport.stream(studentService.findAll().spliterator(), false);
        return s.collect(Collectors.toList());
    }

    public void initializeModel() {
        Iterable<Student> students = studentService.findAll();
        List<Student> st = StreamSupport.stream(students.spliterator(), false).collect(Collectors.toList());
        model.setAll(st);
    }

    @FXML
    public void initialize() {
        tableViewColumnId.setCellValueFactory(new PropertyValueFactory<Student, String>("id"));
        tableViewColumnNume.setCellValueFactory(new PropertyValueFactory<Student, String>("nume"));
        tableViewColumnPrenume.setCellValueFactory(new PropertyValueFactory<Student, String>("prenume"));
        tableViewColumnGrupa.setCellValueFactory(new PropertyValueFactory<Student, String>("grupa"));
        tableViewColumnMail.setCellValueFactory(new PropertyValueFactory<Student, String>("email"));
        tableViewColumnProf.setCellValueFactory(new PropertyValueFactory<Student, String>("CadruDidacticIndrumatorLab"));
        tableViewStudent.setItems(model);
    }


    private void setFields(Student s) {
        textFieldStudentId.setText(s.getId());
        textFieldStudentNume.setText(s.getNume());
        textFieldStudentPrenume.setText(s.getPrenume());
        textFieldStudentGrupa.setText(Integer.toString(s.getGrupa()));
        textFieldStudentMail.setText(s.getEmail());
        textFieldStudentProf.setText(s.getCadruDidacticIndrumatorLab());
    }

    private void clearFields() {
        textFieldStudentId.setText("");
        textFieldStudentNume.setText("");
        textFieldStudentPrenume.setText("");
        textFieldStudentGrupa.setText("");
        textFieldStudentMail.setText("");
        textFieldStudentProf.setText("");
    }



    private void handleFilter() {
        Predicate<Student> byNume = x -> x.getNume().toLowerCase().startsWith(textFieldSearchNume.getText().toLowerCase());
        Predicate<Student> byPrenume = x -> x.getPrenume().toLowerCase().startsWith(textFieldSearchNume.getText().toLowerCase());

        Predicate<Student> byGrupa = x -> {
            if (!(textFieldSearchGrupa.getText().isEmpty()))
                return x.getGrupa() == Integer.parseInt(textFieldSearchGrupa.getText());
            else
                return true;
        };

        Predicate<Student> byProf = x -> {
            if (!(textFieldSearchProf.getText().isEmpty()))
                return x.getCadruDidacticIndrumatorLab().toLowerCase().startsWith(textFieldSearchProf.getText().toLowerCase());
            else
                return true;
        };

        List<Student> students = getAllStudents();
        model.setAll(
                students.stream()
                        .filter((byNume.or(byPrenume)).and(byGrupa).and(byProf))
                        .collect(Collectors.toList())
        );
    }

    @FXML
    public void handleAddButton() {
        try {
            String id = textFieldStudentId.getText();
            int id_validator = Integer.parseInt(id);
            String nume = textFieldStudentNume.getText();
            String prenume = textFieldStudentPrenume.getText();
            int grupa = Integer.parseInt(textFieldStudentGrupa.getText());
            String prof = textFieldStudentProf.getText();
            String email = textFieldStudentMail.getText();

            Student rez = studentService.save(new Student(id, nume, prenume, grupa, email, prof));
            if (rez != null) {
                ErrorAlert.showErrorMessage(null, "Id deja existent!");
            }
            initializeModel();
            clearFields();

        } catch (NumberFormatException x) {
            ErrorAlert.showErrorMessage(null, "Id si grupa trebuie sa fie numere!");
        } catch (ValidationException x) {
            ErrorAlert.showErrorMessage(null, x.getMessage());
        }
    }

    @FXML
    public void handleUpdateButton() {


        try {
            String id = textFieldStudentId.getText();
            String nume = textFieldStudentNume.getText();
            String prenume = textFieldStudentPrenume.getText();
            int grupa = Integer.parseInt(textFieldStudentGrupa.getText());
            String prof = textFieldStudentProf.getText();
            String email = textFieldStudentMail.getText();

            Student rez = studentService.update(new Student(id, nume, prenume, grupa, email, prof));
            if (rez != null) {
                ErrorAlert.showErrorMessage(null, "Nu s-a gasit student!");
            }
            initializeModel();
            clearFields();
        } catch (NumberFormatException x) {
            ErrorAlert.showErrorMessage(null, "Id si grupa trebuie sa fie numere!");
        } catch (ValidationException x) {
            ErrorAlert.showErrorMessage(null, x.getMessage());
        }
    }

    @FXML
    public void handleDeleteButton() {
        Student selectedItem = (Student) tableViewStudent.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            ErrorAlert.showErrorMessage(null, "NU ati selectat nici un student");
        } else {
            studentService.delete(selectedItem.getId());
            initializeModel();
        }
    }

    @FXML
    public void handleTableSelection() {
        Student selectedItem = (Student) tableViewStudent.getSelectionModel().getSelectedItem();
        if (selectedItem != null)
            setFields(selectedItem);
        else {
            clearFields();
        }
    }


    public void mouseClickedEvent(MouseEvent mouseEvent) {
        tableViewStudent.getSelectionModel().clearSelection();
    }

}
