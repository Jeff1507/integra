package com.integra.controllers;

import com.github.hugoperlin.results.Resultado;
import com.integra.App;
import com.integra.model.entities.Estudante;
import com.integra.model.repositories.RepositorioEstudante;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class CadastrarEstudante {

    @FXML
    private TextField tfEmail;

    @FXML
    private TextField tfNome;

    @FXML
    private PasswordField tfSenha;

    private RepositorioEstudante repositorioEstudante;

    public CadastrarEstudante(RepositorioEstudante repositorioEstudante){
        this.repositorioEstudante = repositorioEstudante;
    }

    @FXML
    private void cadastrar(ActionEvent event) {
        String nome = tfNome.getText();
        String email = tfEmail.getText();
        String senha = tfSenha.getText();

        Resultado<Estudante> resultado = repositorioEstudante.cadastrar(nome, email, senha);

        Alert alert;
        
        if(resultado.foiErro()){
            alert = new Alert(AlertType.ERROR, resultado.getMsg());
        }else{
            alert = new Alert(AlertType.INFORMATION, resultado.getMsg());
        }

        alert.showAndWait();
    }

    @FXML
    void voltar() {
        App.popScreen();
    }

}
