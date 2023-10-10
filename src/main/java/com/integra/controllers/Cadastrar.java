package com.integra.controllers;

import com.integra.App;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

public class Cadastrar {

    @FXML
    void cadastrarEmpresa(){
        App.pushScreen("CADASTRAREMPRESA");
    }

    @FXML
    void CadastrarEstudante(){
        App.pushScreen("CADASTRARESTUDANTE");
    }
}
