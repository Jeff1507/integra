package com.integra.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.github.hugoperlin.results.Resultado;
import com.integra.App;
import com.integra.model.entities.Empresa;
import com.integra.model.entities.Estudante;
import com.integra.model.entities.Projeto;
import com.integra.model.entities.Solucao;
import com.integra.model.repositories.RepositorioEstudante;
import com.integra.model.repositories.RepositorioProjeto;
import com.integra.model.repositories.RepositorioSolucao;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
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

public class DashboardEstudante implements Initializable{

    @FXML
    private Pane abaEditarConta, abaInicio, abaMinhasSolucoes, abaPesquisar, abaVerProjeto, abaVerConta;

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
    private void abrirAba(ActionEvent event) {
        if (event.getSource() == btn_inicio) {
            abaInicio.toFront();
        }
        
        else if (event.getSource() == btnVerConta) {
            verConta();
            abaVerConta.toFront();
        }
        else if (event.getSource() == btnFecharVerPerfil) {
            abaVerConta.toBack();
        }
        else if(event.getSource() == btn_editar_conta){
            editarConta();
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
                tfBarraPesquisa.clear();
                lstProjetosPesquisa.getItems().clear();
            }
        }
    }
    
    @FXML
    private void verProjeto(Projeto projeto){
        
        String nome = projeto.getNome();
        String areaEmpresa = projeto.getAreaEmpresa();
        String descricao = projeto.getDescricao();

        lbNomeProjeto.setText(nome);
        lbAreaEmpresa.setText(areaEmpresa);
        //lbDescricao.setText(descricao);
        taDescricaoAtual.setText(descricao);


    }

    private void verMinhasSolucoes(){
        contaLogada = repositorioEstudante.contaLogada();

        Resultado<ArrayList<Solucao>> resultado = repositorioSolucao.listarSolucaoEstudante(contaLogada);

        if (resultado.foiErro()) {
            Alert alert = new Alert(AlertType.ERROR, resultado.getMsg());
            alert.showAndWait();
        }

        List<Solucao> solucaos = (List<Solucao>) resultado.comoSucesso().getObj();
        
    }

    private void verConta(){
        lbUserNome.setText("Nome: "+contaLogada.getNome());
        lbUserEmail.setText("E-mail: "+contaLogada.getEmail());
        lbUserProjetos.setText("Soluções: "+contaLogada.getSolucoes().size());
    }

    private void editarConta(){
        tfEditNomeConta.setText(contaLogada.getNome());
        tfEditEmailConta.setText(contaLogada.getEmail());
        tfEditSenhaConta.setText(contaLogada.getSenha());
    }
    @FXML
    private void editarPerfil(ActionEvent event){
        String nome = tfEditNomeConta.getText();
        String email = tfEditEmailConta.getText();
        String senha = tfEditSenhaConta.getText();

        Resultado<Estudante> resultado = repositorioEstudante.editarConta(contaLogada.getId(), nome, email, senha);

        Alert alert;
        
        if(resultado.foiErro()){
            alert = new Alert(AlertType.ERROR, resultado.getMsg());
        }else{
            alert = new Alert(AlertType.INFORMATION, resultado.getMsg());
        }
        alert.showAndWait();
    }

    @FXML
    private void desconectar(ActionEvent event) {
        App.pushScreen("LOGIN");
    }

    @FXML
    private void pesquisarProjeto(KeyEvent event) {
        String pesquisa = tfBarraPesquisa.getText();
        Resultado<ArrayList<Projeto>> resultado = repositorioProjeto.filtraPorNome(pesquisa);
        List<Projeto> projetos = (List<Projeto>) resultado.comoSucesso().getObj();

        if (resultado.foiSucesso()) {
            atualizarListaPesquisa(projetos);
        }

        if (tfBarraPesquisa.getText().isEmpty() || tfBarraPesquisa.getText().isBlank() || lstProjetosPesquisa.getItems().isEmpty()) {
            lbPesquisaErro.setVisible(true);
            lstProjetosPesquisa.setVisible(false);
            lbPesquisaErro.setText("Não há nada pra mostrar aqui!");
        }

        else {
            lbPesquisaErro.setVisible(false);
            lstProjetosPesquisa.setVisible(true);
       
        }
    }

    @FXML
    private void verProjetoPesquisa(){
        Projeto projeto = lstProjetosPesquisa.getSelectionModel().getSelectedItem();
        if (projeto != null) {
            verProjeto(projeto);
            abaVerProjeto.toFront();
            abaPesquisar.toBack();
        }
        
    }
    private void atualizarListaPesquisa(List<Projeto> projetos){
        lstProjetosPesquisa.getItems().clear();
        lstProjetosPesquisa.getItems().addAll(projetos);
    }

    private VBox secaoSolucoes(List<Solucao> solucoes){
        return null;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'initialize'");
    }

}

