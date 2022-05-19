package com.example.linecharttest;

import javafx.fxml.FXML;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;

public class printDocument {
    @FXML
    public DialogPane dialogue;
    @FXML
    private TextField masseDuChariot;
    @FXML
    private TextField groupe;
    @FXML
    private TextField nom;

    public String nom(){
        return nom.getText();
    }

    public String group(){return groupe.getText();}

    public String masse(){return masseDuChariot.getText();}
}
