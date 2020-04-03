package controller;

import domain.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import services.NotaService;
import services.StudentService;
import services.TemaService;
import utils.Constants;
import validators.ValidationException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static controller.ErrorAlert.showErrorMessage;

public class NoteController {
    StudentService studentService;
    TemaService temaService;
    NotaService notaService;
    ObservableList<NotaDTO> model = FXCollections.observableArrayList();
    StructuraSemestru structuraSemestru = new StructuraSemestru();

    private int penalitati = -1;

    @FXML
    TableView tabelViewNote;
    @FXML
    TableColumn<NotaDTO, String> tableViewColumnStudent;
    @FXML
    TableColumn<NotaDTO, String> tableViewColumnTema;
    @FXML
    TableColumn<NotaDTO, String> tableViewColumnNota;
    @FXML
    TableColumn<NotaDTO, String> tableViewColumnProf;
    @FXML
    TableColumn<NotaDTO, String> tableViewColumnGrupa;

    @FXML
    TextField textFieldNumeStudent;
    @FXML
    TextField textFieldValoareNota;
    @FXML
    ComboBox<Student> comboBoxStudent;
    @FXML
    ComboBox<Tema> comboBoxTema;
    @FXML
    ComboBox<String> comboBoxProf;
    @FXML
    DatePicker predareDatePicker;

    @FXML
    CheckBox motivariCheckBox;
    @FXML
    HBox hBoxMotivari;

    @FXML
    DatePicker datePickerMotivareStart;
    @FXML
    DatePicker datePickerMotivareDeadline;

    @FXML
    TextArea textAreaFeedback;

    @FXML
    Button buttonAddNota;


    public void setService(StudentService s, TemaService t, NotaService n) {
        studentService = s;
        temaService = t;
        notaService = n;
        initializeModel();

        comboBoxProf.focusedProperty().addListener(
                (x, y, z) -> {
                    if (z) {
                        comboBoxProf.getItems().setAll(getProfesori());
                        comboBoxProf.setValue(comboBoxProf.getSelectionModel().getSelectedItem());
                    }
                }
        );

        comboBoxStudent.focusedProperty().addListener(
                (x, y, z) -> {
                    if (z) {
                        handleSearchStudent();
                        Student st = comboBoxStudent.getSelectionModel().getSelectedItem();
                        comboBoxStudent.setValue(st);
                    }
                }
        );

        comboBoxTema.focusedProperty().addListener(
                (x, y, z) -> {
                    if (z) {
                        comboBoxTema.getItems().setAll(getTeme());
                        comboBoxTema.setValue(comboBoxTema.getSelectionModel().getSelectedItem());
                    }
                }
        );

        predareDatePicker.focusedProperty().addListener(
                (obs, old, newValue) -> {
                    if (newValue) {
                        predareDatePicker.setValue(predareDatePicker.getValue());
                    }
                }
        );

    }

    public void initializeModel() {
        model.setAll(getNoteDTOList());
    }

    @FXML
    public void initialize() {
        tableViewColumnStudent.setCellValueFactory(new PropertyValueFactory<NotaDTO, String>("studentAllName"));
        tableViewColumnTema.setCellValueFactory(new PropertyValueFactory<NotaDTO, String>("temaId"));
        tableViewColumnNota.setCellValueFactory(new PropertyValueFactory<NotaDTO, String>("notaValoare"));
        tableViewColumnProf.setCellValueFactory(new PropertyValueFactory<NotaDTO, String>("profesor"));
        tableViewColumnGrupa.setCellValueFactory(new PropertyValueFactory<NotaDTO, String>("grupa"));
        tabelViewNote.setItems(model);
        motivariCheckBox.setSelected(false);
        hBoxMotivari.setVisible(false);
        predareDatePicker.setValue(LocalDate.now());
        buttonAddNota.setDisable(true);
    }

    @FXML
    public void clearFields() {
        textFieldValoareNota.setText("");
        textAreaFeedback.setText("");
        textFieldNumeStudent.setText("");
        comboBoxTema.getSelectionModel().clearSelection();
        comboBoxStudent.getSelectionModel().clearSelection();
        comboBoxProf.getSelectionModel().clearSelection();
        predareDatePicker.valueProperty().setValue(LocalDate.now());
        motivariCheckBox.selectedProperty().setValue(false);
        datePickerMotivareStart.setValue(LocalDate.now());
        datePickerMotivareDeadline.setValue(LocalDate.now());
        setPenalty(0);
    }


    /*
                --- getters
     */

    public List<NotaDTO> getNoteDTOList() {
        Stream<Nota> s = (Stream<Nota>) StreamSupport.stream(notaService.findAll().spliterator(), false)
                .sorted((x, y) -> x.getStudent().getNume().compareToIgnoreCase(y.getStudent().getNume()));
        return s.map(n -> new NotaDTO(n)).collect(Collectors.toList());
    }

    public List<Tema> getTeme() {
        List<Tema> t = (List<Tema>) StreamSupport.stream(temaService.findAll().spliterator(), false)
                .distinct().collect(Collectors.toList());

        if (!comboBoxStudent.getSelectionModel().isEmpty()) {
            Student st = comboBoxStudent.getSelectionModel().getSelectedItem();
            List<Tema> temeStudent = getNoteDTOList().stream()
                    .filter(x -> x.getStudent().getId().equals(st.getId()))
                    .map(x -> x.getTema())
                    .distinct().collect(Collectors.toList());
            Predicate<Tema> areNota =
                    x -> !temeStudent.contains(x);
            return t.stream().filter(areNota).distinct().collect(Collectors.toList());
        }
        return t;
    }

    private List<String> getProfesori() {
        Stream<Student> s = (Stream<Student>) StreamSupport.stream(studentService.findAll().spliterator(), false);
        return s.map(n -> n.getCadruDidacticIndrumatorLab()).distinct().collect(Collectors.toList());
    }

    private Float getMedie(Student st) {
        int numarTeme = Integer.parseInt(Long.toString(temaService.findAll().spliterator().getExactSizeIfKnown()));
        List<Nota> noteStud = notaService.noteStudent(st);
        float suma = 0f;

        if (noteStud.size() == numarTeme) {
            for (Nota n : noteStud) {
                suma += n.getValoare();
            }
            return suma / numarTeme;
        }
        int i = 1;
        for (Nota n : noteStud) {
            suma += n.getValoare();
            i++;
        }
        while (i <= numarTeme) {
            suma += 1;
            i++;
        }

        return suma / numarTeme;
    }

    private Float getMedie(Tema t) {
        List<Nota> noteTema = notaService.noteTema(t);
        int nrStudenti = Integer.parseInt(Long.toString(studentService.findAll().spliterator().getExactSizeIfKnown()));
        float suma = 0f;

        for (Nota n : noteTema) {
            suma += n.getValoare();
        }
        return suma / nrStudenti;
    }

    private boolean allPenalitiesZero(Student st) {
        List<NotaDTO> notes = getNoteDTOList();
        for (NotaDTO n : notes) {
            if (n.getStudent().getId().equals(st.getId())) {
                if (n.getPenalitati() != 0)
                    return false;
            }
        }
        return true;
    }

    /*
                --- handlers
     */

    @FXML
    public void handleRaport1() {
        List<Student> students = StreamSupport.stream(studentService.findAll().spliterator(), false).collect(Collectors.toList());
        Map<Student, Float> map = new HashMap<>();
        students.forEach(x -> map.put(x, getMedie(x)));

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/RaportResultsView.fxml"));
            AnchorPane root = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Rezulate raport 1");
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            RaportResults ctrl = loader.getController();
            ;
            ctrl.setElements("Studenti + medie laborator:");

            ctrl.showMap(map);
            dialogStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void handleRaport2() {
        List<Tema> teme = StreamSupport.stream(temaService.findAll().spliterator(), false).collect(Collectors.toList());
        Map<Tema, Float> map = new HashMap<>();
        teme.forEach(x -> map.put(x, getMedie(x)));
        Map.Entry<Tema, Float> min = null;

        for (Map.Entry<Tema, Float> m : map.entrySet())
            if (min == null || m.getValue() < min.getValue()) {
                min = m;
            }


        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/RaportResultsView.fxml"));
            AnchorPane root = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Rezulate raport 2");
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            RaportResults ctrl = loader.getController();
            ;
            ctrl.setElements("Cea mai grea tema:");

            ctrl.showRaport2(min.getKey(), min.getValue());
            dialogStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void handleRaport3() {
        List<Student> students = StreamSupport.stream(studentService.findAll().spliterator(), false).collect(Collectors.toList());
        Map<Student, Float> map = new HashMap<>();
        students.stream()
                .filter(x -> getMedie(x) >= 5)
                .collect(Collectors.toList())
                .forEach(x -> map.put(x, getMedie(x)));

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/RaportResultsView.fxml"));
            AnchorPane root = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Rezulate raport 3");
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            RaportResults ctrl = loader.getController();
            ;
            ctrl.setElements("Studentii care intra in examen:");

            ctrl.showMap(map);
            dialogStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void handleRaport4() {

        List<Student> students = getNoteDTOList().stream()
                .filter(x -> allPenalitiesZero(x.getStudent()))
                .map(x -> x.getStudent())
                .distinct()
                .collect(Collectors.toList());

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/RaportResultsView.fxml"));
            AnchorPane root = (AnchorPane) loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Rezulate raport 4");
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);

            RaportResults ctrl = loader.getController();
            ;
            ctrl.setElements("Studentii care nu au intarziat cu niciun lab:");

            ctrl.showRaport4(students);
            dialogStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleCheckBoxSelection() {
        boolean selected = motivariCheckBox.isSelected();
        if (!selected) {
            hBoxMotivari.setVisible(false);
            hBoxMotivari.setMaxHeight(0);
        } else {
            hBoxMotivari.setVisible(true);
            hBoxMotivari.setMaxHeight(60);
        }
    }

    @FXML
    public void handleSearchStudent() {
        List<Student> s = (List<Student>) StreamSupport
                .stream(studentService.findAll().spliterator(), false)
                .collect(Collectors.toList());

        if (!comboBoxProf.getSelectionModel().isEmpty()) {

            Predicate<Student> byProf = x ->
                    x.getCadruDidacticIndrumatorLab().equals(comboBoxProf.getSelectionModel().getSelectedItem());

            List<Student> students = (List<Student>) StreamSupport
                    .stream(studentService.findAll().spliterator(), false)
                    .filter(byProf)
                    .collect(Collectors.toList());

            Predicate<Student> byNume = x ->
                    x.getNume().toLowerCase().startsWith(textFieldNumeStudent.getText().toLowerCase());
            Predicate<Student> byPrenume = x ->
                    x.getPrenume().toLowerCase().startsWith(textFieldNumeStudent.getText().toLowerCase());

            s = students.stream()
                    .filter(byNume.or(byPrenume))
                    .collect(Collectors.toList());
        }

        comboBoxStudent.getItems().setAll(s);
    }

    @FXML
    public void handleSave() {
        try {
            Student st = comboBoxStudent.getSelectionModel().getSelectedItem();
            Tema t = comboBoxTema.getSelectionModel().getSelectedItem();
            if (st == null) {
                ErrorAlert.showErrorMessage(null, "Nu s-a selectat niciun student!");
                return;
            }
            if (t == null) {
                ErrorAlert.showErrorMessage(null, "Nu s-a selectat nicio tema!");
                return;
            }

            LocalDate data = predareDatePicker.getValue();
            String profesor = st.getCadruDidacticIndrumatorLab();
            String feedback = textAreaFeedback.getText();

            Float nota = Float.parseFloat(textFieldValoareNota.getText());
            if (nota > 10f || nota <= 0f)
                showErrorMessage(null, "Introduceti o nota intre 1 si 10!");
            else {

                Nota save_nota = new Nota(data.format(Constants.DATE_FORMATTER), profesor, st, t);
                save_nota.setValoare(nota);
                save_nota.setFeedBack(feedback);
                save_nota.setPenalitati(penalitati);
                try {
                    if (notaService.findOne(save_nota.getId()) != null) {
                        showErrorMessage(null, "Aceasta tema a fost deja notata!");
                        clearFields();
                    } else {
                        notaService.save(save_nota);
                        clearFields();
                    }
                } catch (ValidationException e) {
                    showErrorMessage(null, e.getMessage());
                }
            }
        } catch (NullPointerException e) {
            showErrorMessage(null, "Introduceti toate datele!");
        } catch (NumberFormatException e) {
            showErrorMessage(null, "Nota trebuie sa fie numar!");
        } catch (ArrayIndexOutOfBoundsException e) {
            showErrorMessage(null, "Introduceti toate datele!");
        } catch (IllegalAccessException x) {
        }
        initializeModel();
        buttonAddNota.setDisable(true);
    }

    @FXML
    public void handleDeleteNota() {
        NotaDTO n = (NotaDTO) tabelViewNote.getSelectionModel().getSelectedItem();
        if (n == null) {
            showErrorMessage(null, "Nu s-a selectat nicio nota!");
        } else {
            Nota nota = n.getNota();
            notaService.delete(nota.getId());
            initializeModel();
        }
    }

    @FXML
    public void checkMotivari() {
        buttonAddNota.setDisable(true);
        Tema tema = comboBoxTema.getSelectionModel().getSelectedItem();
        if (tema == null) {
            showErrorMessage(null, "Nu s-a selectat tema!!");
            return;
        }

        LocalDate data = predareDatePicker.getValue();
        try {
            LocalDate start;
            LocalDate end;
            if (motivariCheckBox.isSelected()) {
                start = datePickerMotivareStart.getValue();
                end = datePickerMotivareDeadline.getValue();
            } else {
                start = null;
                end = null;
            }

            int penality = notaService.getPenality(tema, data);
            setPenalty(penality);
            int nota = Integer.parseInt(textFieldValoareNota.getText());

            // cazul in care nu avem motivari
            if(!motivariCheckBox.isSelected()){
                if (penality > 0) {
                    textFieldValoareNota.setText(Integer.toString(nota - penality));
                    textAreaFeedback.appendText("\nS-au scazut " + penality + " puncte pentru intarziere!\n");
                }
                else if (penality == 0)
                    textFieldValoareNota.setText(Integer.toString(nota));
                else{
                    textFieldValoareNota.setText(Integer.toString(2));
                }
            }

            // cazul in care avem motivari
            else if (penality == 1) {
                if (structuraSemestru.getCurrentWeek(start) >= tema.getStartWeek()
                        && structuraSemestru.getCurrentWeek(start) <= tema.getEndWeek()) {
                    nota = Integer.parseInt(textFieldValoareNota.getText()) + 1;
                    if (nota > 10) {
                        nota = 10;
                    }
                    textFieldValoareNota.setText(Integer.toString(nota));
                    hBoxMotivari.setVisible(false);
                    textAreaFeedback.appendText("");
                } else if (structuraSemestru.getCurrentWeek(end) >= tema.getStartWeek()
                        && structuraSemestru.getCurrentWeek(end) <= tema.getEndWeek()) {
                    nota = Integer.parseInt(textFieldValoareNota.getText()) + 1;
                    if (nota > 10) {
                        nota = 10;
                    }
                    textFieldValoareNota.setText(Integer.toString(nota));
                    hBoxMotivari.setVisible(false);
                    textAreaFeedback.appendText("");
                } else {
                    showErrorMessage(null, "Motivare invalida!");
                }
            } else if (penality == 2) {
                if (structuraSemestru.getCurrentWeek(end) - structuraSemestru.getCurrentWeek(start) == 1) {
                    if (structuraSemestru.getCurrentWeek(start) == tema.getStartWeek() ||
                            structuraSemestru.getCurrentWeek(start) == tema.getEndWeek() ||
                            structuraSemestru.getCurrentWeek(end) == tema.getStartWeek()) {
                        nota = Integer.parseInt(textFieldValoareNota.getText()) + 2;
                        if (nota > 10) {
                            nota = 10;
                        }
                        textFieldValoareNota.setText(Integer.toString(nota));
                        hBoxMotivari.setVisible(false);
                        textAreaFeedback.appendText("");
                    } else {
                        showErrorMessage(null, "Motivare invalida!");
                    }
                } else if (structuraSemestru.getCurrentWeek(end) == structuraSemestru.getCurrentWeek(start)) {
                    if (structuraSemestru.getCurrentWeek(start) == tema.getStartWeek()
                            || structuraSemestru.getCurrentWeek(start) == tema.getEndWeek()
                            || structuraSemestru.getCurrentWeek(start) == tema.getEndWeek() + 1) {
                        nota = Integer.parseInt(textFieldValoareNota.getText()) + 1;
                        if (nota > 10) {
                            nota = 10;
                        }
                        textFieldValoareNota.setText(Integer.toString(nota));
                        hBoxMotivari.setVisible(false);
                        textAreaFeedback.appendText("Ai intarziat o saptamana! -1 punct la nota! \n");
                    } else {
                        showErrorMessage(null, "Motivare invalida!");
                    }
                }
            } else if (penality == -1) {
                textFieldValoareNota.setText("2");
                hBoxMotivari.setVisible(false);
                textAreaFeedback.appendText("Tema intarziata cu mai mult de 2 saptamani!!\n");
            }

            textFieldValoareNota.setEditable(false);
            buttonAddNota.setDisable(false);
            motivariCheckBox.selectedProperty().setValue(false);
        } catch (NumberFormatException ex) {
            showErrorMessage(null, "Nota si numarul de motivari trebuie sa fie numere!");
        }
    }


    @FXML
    public void handleAddMoreNotes(){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/views/AddNoteForMoreStudents.fxml"));
            AnchorPane root = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Adaugare mai note multiple");
            Scene scene = new Scene(root);
            dialogStage.setScene(scene);


            MultipleNotesController ctrl = loader.getController();
            ctrl.setService(notaService, studentService, temaService);
            dialogStage.show();
            initializeModel();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleOutsideClick() {
        tabelViewNote.getSelectionModel().clearSelection();
    }

    private void setPenalty(int p) {
        penalitati = p;
    }
}
