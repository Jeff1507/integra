package com.integra.controllers;

import com.github.hugoperlin.results.Resultado;
import com.integra.App;
import com.integra.model.entities.Empresa;
import com.integra.model.repositories.RepositorioEmpresa;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;
import javafx.scene.control.TextField;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

public class Login {

    @FXML
    private TextField tfNome;

    @FXML
    private TextField tfSenha;

    @FXML
    private Label msgErro;

    private RepositorioEmpresa repositorioEmpresa;
    private static final Duration delay = Duration.seconds(3);

    public Login(RepositorioEmpresa repositorioEmpresa){
        this.repositorioEmpresa = repositorioEmpresa;
    }

    @FXML
    private void logar(ActionEvent event){
        String nome = tfNome.getText();
        String senha = tfSenha.getText();

        Resultado<Empresa> resultado = repositorioEmpresa.login(nome, senha);
        
        if(resultado.foiErro()){
            msgErro.setVisible(true);
            msgErro.setText(resultado.getMsg());
            esconderMsgErro();
        }else{
            msgErro.setVisible(true);
            msgErro.setText(resultado.getMsg());
            esconderMsgErro();
            App.pushScreen("DASHBOARDEMPRESA");
        }

        

    }
    @FXML
    private void irParaCadastro(ActionEvent event){
        App.pushScreen("CADASTRAR");
    }
    private void esconderMsgErro(){
        Timeline timeline = new Timeline(new KeyFrame(delay, e -> msgErro.setVisible(false)));
        timeline.play();
    }
}
