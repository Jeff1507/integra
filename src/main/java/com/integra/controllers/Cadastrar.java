package com.integra.controllers;

import com.integra.App;

import javafx.fxml.FXML;

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
