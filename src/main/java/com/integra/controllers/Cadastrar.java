package com.integra.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

public class Cadastrar {

    @FXML
    private TextArea taTexto;

    public Cadastrar(){
        
    }

    @FXML
    void mostrarTextoEmpresa(MouseEvent event){
        String texto = new String("a");
        taTexto = new TextArea(texto);
        taTexto.setVisible(true);
    }

    @FXML
    void mostrarTextoEstudante(MouseEvent event){
        String texto = new String("b");
        taTexto = new TextArea(texto);
        taTexto.setVisible(true);
    }

    @FXML
    void ocultarTexto(MouseEvent event){
        taTexto.setVisible(false);
    }
}
