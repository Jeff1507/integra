package com.integra.controllers;

import com.github.hugoperlin.results.Resultado;
import com.integra.model.entities.Solucao;
import com.integra.model.repositories.RepositorioSolucao;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class DashboardEstudante {
    @FXML
    private Pane aba1, aba2, aba3;

    @FXML
    private Button btn_menu_lateral1, btn_menu_lateral2, btn_menu_lateral3;

    @FXML
    private TextField tfTitulo;

    @FXML
    private TextArea taDescricao;

    private RepositorioSolucao repositorioSolucao;

    public DashboardEstudante(RepositorioSolucao repositorioSolucao){
        this.repositorioSolucao = repositorioSolucao;
    }

    @FXML
    void abrirAba(ActionEvent event) {
        if (event.getSource() == btn_menu_lateral1) {
            aba1.toFront();
        }
        else if(event.getSource() == btn_menu_lateral2){
            aba2.toFront();
        }
        else if(event.getSource() == btn_menu_lateral3){
            aba3.toFront();
        }
    }
    @FXML
    private void criarSolucao(ActionEvent event){
        String titulo = tfTitulo.getText();
        String descricao = taDescricao.getText();

        Resultado<Solucao> resultado = repositorioSolucao.criarSolucao(titulo, descricao);
        
        Alert alert;
        if (resultado.foiErro()) {
            alert=new Alert(AlertType.ERROR, resultado.getMsg());
            
        }
        else{
            alert=new Alert(AlertType.INFORMATION, resultado.getMsg());
        } 
        alert.showAndWait();
    }
}
