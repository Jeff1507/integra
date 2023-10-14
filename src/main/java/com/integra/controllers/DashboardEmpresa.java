package com.integra.controllers;

import com.github.hugoperlin.results.Resultado;
import com.integra.model.entities.Projeto;
import com.integra.model.repositories.RepositorioProjeto;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class DashboardEmpresa {

    @FXML
    private TextField tfNomeProjeto;

    @FXML
    private TextField tfAreaEmpresa;

    @FXML
    private TextArea taDescricao;

    @FXML
    private Pane aba1, aba2, aba3, aba4;

    @FXML
    private Button btn_menu_lateral1, btn_menu_lateral2, btn_menu_lateral3, btn_menu_lateral4;

    private RepositorioProjeto repositorioProjeto;

    public DashboardEmpresa(RepositorioProjeto repositorioProjeto){
        this.repositorioProjeto = repositorioProjeto;
    }

    @FXML
    private void criarProjeto(ActionEvent event){
        String nome = tfNomeProjeto.getText();
        String areEmpresa = tfAreaEmpresa.getText();
        String descricao = taDescricao.getText();

        Resultado<Projeto> resultado = repositorioProjeto.criarProjeto(nome, areEmpresa, descricao);

        Alert alert;
        if (resultado.foiErro()) {
            alert = new Alert(AlertType.ERROR, resultado.getMsg());
            
        }
        else{
            alert = new Alert(AlertType.INFORMATION, resultado.getMsg());
        } 
        alert.showAndWait();
    }

    @FXML
    private void abrirAba(ActionEvent event){
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
