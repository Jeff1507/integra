package com.integra.controllers;

import com.integra.App;
import com.integra.model.entities.Estudante;
import com.integra.model.entities.Projeto;
import com.integra.model.repositories.RepositorioEstudante;
import com.integra.model.repositories.RepositorioProjeto;
import com.integra.model.repositories.RepositorioSolucao;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class DashboardEstudante {

    @FXML
    private Pane abaEditarConta, abaInicio, abaMinhasSolucoes, abaPesquisar, abaVerProjeto;

    @FXML
    private Button btnFecharEditarConta, btnFecharVerPerfil;

    @FXML
    private ToggleButton btnPesquisar;

    @FXML
    private Button btnVerConta;

    @FXML
    private Button btn_editar_conta;

    @FXML
    private Button btn_inicio;

    @FXML
    private Button btn_minhas_solucoes;

    @FXML
    private Label lbAreaEmpresa;

    @FXML
    private Label lbNomeProjeto;

    @FXML
    private Label lbPesquisaErro;

    @FXML
    private Label lbUserEmail;

    @FXML
    private Label lbUserNome;

    @FXML
    private Label lbUserProjetos;

    @FXML
    private ListView<Projeto> lstProjetosPesquisa;

    @FXML
    private VBox mostraProjetoAtual;

    @FXML
    private ScrollPane spExplorar;

    @FXML
    private ScrollPane spMeusProjetos;

    @FXML
    private TextArea taDescricaoAtual;

    @FXML
    private TextField tfBarraPesquisa;

    @FXML
    private TextField tfEditEmailConta;

    @FXML
    private TextField tfEditNomeConta;

    @FXML
    private TextField tfEditSenhaConta;

    @FXML
    private VBox v1;

    @FXML
    private VBox v2;

    private RepositorioEstudante repositorioEstudante;
    private RepositorioProjeto repositorioProjeto;
    private RepositorioSolucao repositorioSolucao;
    private Estudante contaLogada;

    @FXML
    void abrirAba(ActionEvent event) {

    }

    @FXML
    void atualizar(ActionEvent event) {

    }

    @FXML
    void desconectar(ActionEvent event) {
        App.pushScreen("LOGIN");
    }

    @FXML
    void editarPerfil(ActionEvent event) {

    }

    @FXML
    void pesquisarProjeto(KeyEvent event) {

    }

    @FXML
    void verProjetoPesquisa(MouseEvent event) {

    }

}

