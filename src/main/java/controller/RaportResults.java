package controller;

import domain.Entity;
import domain.NotaDTO;
import domain.Student;
import domain.Tema;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.util.List;
import java.util.Map;

public class RaportResults {

    @FXML
    Label textLabelElemente;
    @FXML
    TextArea textAreaElemente;


    public void setElements(String label) {
        textLabelElemente.setText(label);
    }

    public void showMap(Map<Student,Float> students){
        textAreaElemente.clear();
        students.forEach(
                (x,y) -> textAreaElemente.appendText(x.getNume() + " " + x.getPrenume() + " - " + y.toString() +"\n")
        );
    }

    public void showRaport2(Tema key, Float medie) {
        textAreaElemente.clear();
        textAreaElemente.setText("Tema: " + key.getId() + " \nDescriere: " + key.getDescriere() + "\nMedie: " + medie );
    }

    public void showRaport4(List<Student> note) {
        textAreaElemente.clear();
        note.forEach(
                x -> textAreaElemente.appendText(x.getNume() + " " + x.getPrenume() + "\n")
        );
    }
}
