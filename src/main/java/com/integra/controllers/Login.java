package com.integra.controllers;

import com.github.hugoperlin.results.Resultado;
import com.integra.App;
import com.integra.model.entities.Empresa;
import com.integra.model.entities.Estudante;
import com.integra.model.repositories.RepositorioEmpresa;
import com.integra.model.repositories.RepositorioEstudante;

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
    private RepositorioEstudante repositorioEstudante;
    private static final Duration delay = Duration.seconds(3);

    public Login(RepositorioEmpresa repositorioEmpresa, RepositorioEstudante repositorioEstudante){
        this.repositorioEmpresa = repositorioEmpresa;
        this.repositorioEstudante = repositorioEstudante;
    }

    @FXML
    private void logar(ActionEvent event){
        String nome = tfNome.getText();
        String senha = tfSenha.getText();

        Resultado<Empresa> resultado = repositorioEmpresa.login(nome, senha);
        
        if (resultado.foiSucesso()) {
            App.pushScreen("DASHBOARDEMPRESA");
        }
        /*if(resultado.foiErro()){
            msgErro.setVisible(true);
            msgErro.setText(resultado.getMsg());
            esconderMsgErro();
        }
        else{
            App.pushScreen("DASHBOARDEMPRESA");
        }*/
        Resultado<Estudante> resultado2 = repositorioEstudante.login(nome, senha);
        if (resultado2.foiSucesso()) {
            App.pushScreen("CADASTRARESTUDANTE");
        }
        else{
            if (resultado.foiErro()) {
                msgErro.setVisible(true);
                msgErro.setText(resultado.getMsg());
                esconderMsgErro();
            }
            else{
                msgErro.setVisible(true);
                msgErro.setText(resultado2.getMsg());
                esconderMsgErro();
            }
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
