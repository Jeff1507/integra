package com.integra.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class DashboardEstudante {

    @FXML
    private Pane abaEditarConta, abaInicio, abaMinhasSolucoes, abaPesquisar, abaVerProjeto, abaVerConta;

    @FXML
    private Button btnInicio, btnMinhasSolucoes, btnVerConta, btnFecharVerPerfil, btnEditarConta, btnFecharEditarConta;

    @FXML
    private ToggleButton btnPesquisar;

    @FXML
    private void abrirAba(ActionEvent event){
        if(event.getSource() == btnInicio){
            abaInicio.toFront();
        }
        else if (event.getSource() == btnMinhasSolucoes) {
            abaMinhasSolucoes.toFront();
        }
        else if (event.getSource() == btnVerConta) {
            abaVerConta.toFront();
        }
        else if (event.getSource() == btnFecharVerPerfil) {
            abaVerConta.toBack();
        }
        else if (event.getSource() == btnEditarConta) {
            abaEditarConta.toFront();
        }
        else if (event.getSource() == btnFecharEditarConta) {
            abaEditarConta.toBack();
        }
        else if (event.getSource() == btnPesquisar) {
            if (btnPesquisar.isSelected()) {
                abaPesquisar.toFront();
            }
            else{
                abaPesquisar.toBack();
            }
        }
    }

    @FXML
    void pesquisarProjeto(KeyEvent event) {

    }

    @FXML
    void verProjetoPesquisa(MouseEvent event) {

    }

}
