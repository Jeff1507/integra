package com.integra.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class DashboardEmpresa {
    @FXML
    private Pane aba1;

    @FXML
    private Pane aba2;

    @FXML
    private Pane aba3;

    @FXML
    private Pane aba4;

    @FXML
    private Button btn_menu_lateral1;

    @FXML
    private Button btn_menu_lateral2;

    @FXML
    private Button btn_menu_lateral3;

    @FXML
    private Button btn_menu_lateral4;

    public DashboardEmpresa(){
        
    }

    @FXML
    void abrirAba(ActionEvent event){
        if (event.getSource() == btn_menu_lateral1) {
            aba1.toFront();
        }
        else if(event.getSource() == btn_menu_lateral2){
            aba2.toFront();
        }
        else if(event.getSource() == btn_menu_lateral3){
            aba3.toFront();
        }
        else{
            aba4.toFront();
        }
    }
}
