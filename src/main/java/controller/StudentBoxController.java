package controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;


public class StudentBoxController {
    @FXML
    TextArea textAreaLabel;

    public void setContent(String content){
        textAreaLabel.setText(content);
    }
}
