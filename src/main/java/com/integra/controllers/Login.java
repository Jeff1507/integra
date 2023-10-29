package com.integra.controllers;

import com.github.hugoperlin.results.Resultado;
import com.integra.App;
import com.integra.model.entities.Empresa;
import com.integra.model.repositories.RepositorioEmpresa;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

public class Login {

    @FXML
    private TextField tfNome;

    @FXML
    private TextField tfSenha;

    private RepositorioEmpresa repositorioEmpresa;

    public Login(RepositorioEmpresa repositorioEmpresa){
        this.repositorioEmpresa = repositorioEmpresa;
    }

    @FXML
    private void logar(ActionEvent event){
        String nome = tfNome.getText();
        String senha = tfSenha.getText();

        Resultado<Empresa> resultado = repositorioEmpresa.login(nome, senha);
        
        Alert alert;
        if(resultado.foiErro()){
            alert = new Alert(AlertType.ERROR, resultado.getMsg());
        }else{
            alert = new Alert(AlertType.INFORMATION, resultado.getMsg());
            App.pushScreen("DASHBOARDEMPRESA");
        }

        alert.showAndWait();

    }

}
