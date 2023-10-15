package com.integra.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.github.hugoperlin.results.Resultado;
import com.integra.model.entities.Projeto;
import com.integra.model.repositories.RepositorioProjeto;

import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class DashboardEmpresa implements Initializable{

    @FXML
    private TextField tfNomeProjeto;

    @FXML
    private TextField tfAreaEmpresa;

    @FXML
    private TextArea taDescricao;
    
    @FXML
    private TableView<Projeto> tbProjetosRecentes;

    @FXML
    private TableColumn<Projeto, String> tbNome, tbAreEmpresa;

    @FXML
    private TableColumn<Projeto, Projeto> tbAcoes;

    //@FXML
    //private ListView<Projeto> lstProjetosRecentes;

    @FXML
    private Pane aba1, aba2, aba3, aba4, abaInicio;

    @FXML
    private Button btn_menu_lateral1, btn_menu_lateral2, btn_menu_lateral3, btn_menu_lateral4, btn_inicio;

    private RepositorioProjeto repositorioProjeto;

    public DashboardEmpresa(RepositorioProjeto repositorioProjeto){
        this.repositorioProjeto = repositorioProjeto;
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
        else if(event.getSource() == btn_menu_lateral4){
            aba4.toFront();
        }
        else{
            abaInicio.toFront();
        }
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

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        tbNome.setCellValueFactory(celula -> new SimpleStringProperty(celula.getValue().getNome()));
        tbAreEmpresa.setCellValueFactory(celula -> new SimpleStringProperty(celula.getValue().getAreaEmpresa()));
        tbAcoes.setCellFactory(celula -> new TableCell<Projeto, Projeto>() {
        private final Button verButton = new Button("Ver");
        private final Button editarButton = new Button("Editar");
        private final Button excluirButton = new Button("Excluir");

        

        {
            // Adicione eventos aos botões (por exemplo, abrir um diálogo de visualização, edição ou exclusão)
            verButton.setOnAction(event -> {
                Projeto projeto = getTableView().getItems().get(getIndex());
                // Lógica para visualizar o projeto
            });

            editarButton.setOnAction(event -> {
                Projeto projeto = getTableView().getItems().get(getIndex());
                // Lógica para editar o projeto
            });

            excluirButton.setOnAction(event -> {
                Projeto projeto = getTableView().getItems().get(getIndex());
                // Lógica para excluir o projeto
            });
            verButton.getStyleClass().add("btn-perfil-menu");
            editarButton.getStyleClass().add("btn-perfil-menu");
            excluirButton.getStyleClass().add("btn-perfil-menu");
        }

        @Override
        protected void updateItem(Projeto projeto, boolean empty) {
            super.updateItem(projeto, empty);
            if (empty) {
                setGraphic(null);
            } else {
                HBox buttonsBox = new HBox(verButton, editarButton, excluirButton);
                setGraphic(buttonsBox);
                buttonsBox.getStyleClass().add("tb-hbox");
            }
        }
    });
        
        //lstProjetosRecentes.getItems().clear();
        Resultado<ArrayList<Projeto>> resultado = repositorioProjeto.listarProjetosRecentes();

        if(resultado.foiErro()){
            Alert alert = new Alert(AlertType.ERROR, resultado.getMsg());
            alert.showAndWait();
        }
        List<Projeto> lista = (List<Projeto>)resultado.comoSucesso().getObj();
        tbProjetosRecentes.getItems().addAll(lista);
        //lstProjetosRecentes.getItems().addAll(lista);

    }
}
