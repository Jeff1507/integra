package com.integra.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.github.hugoperlin.results.Resultado;
import com.integra.model.entities.Projeto;
import com.integra.model.repositories.RepositorioProjeto;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

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

    @FXML
    private Label lbNomeProjeto, lbAreaEmpresa, lbDescricao;

    //@FXML
    //private ListView<Projeto> lstProjetosRecentes;

    @FXML
    private Pane abaCriarProjeto, abaInicio, abaVerProjeto;

    @FXML
    private Button btn_criar_projeto, btn_inicio;

    private RepositorioProjeto repositorioProjeto;

    public DashboardEmpresa(RepositorioProjeto repositorioProjeto){
        this.repositorioProjeto = repositorioProjeto;
    }
 
    @FXML
    private void abrirAba(ActionEvent event){
        if (event.getSource() == btn_inicio) {
            abaInicio.toFront();
        }
        else if(event.getSource() == btn_criar_projeto){
            abaCriarProjeto.toFront();
        }
    }

    @FXML
    void criarProjeto(ActionEvent event){
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
    private void verProjeto(Projeto projeto){
        /* 
        String nome = projeto.getNome();
        String areaEmpresa = projeto.getAreaEmpresa();
        String descricao = projeto.getDescricao();

        lbNomeProjeto.setText(nome);
        lbAreaEmpresa.setText(areaEmpresa);
        lbDescricao.setText(descricao);*/
    }
    @FXML
    VBox testebox;
    @FXML
    
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        tbNome.setCellValueFactory(celula -> new SimpleStringProperty(celula.getValue().getNome()));
        tbAreEmpresa.setCellValueFactory(celula -> new SimpleStringProperty(celula.getValue().getAreaEmpresa()));
        tbAcoes.setCellFactory(celula -> new TableCell<Projeto, Projeto>() {
            private final Button btnVer = new Button();
            private final Button btnEditar = new Button();
            private final Button btnExcluir = new Button();

            private final Image imgVer = new Image(getClass().getResource("/com/integra/img/aaa.png").toExternalForm());
            private final Image imgEditar = new Image(getClass().getResource("/com/integra/img/editIcon.png").toExternalForm());
            private final Image imgExcluir = new Image(getClass().getResource("/com/integra/img/excluir-icon.png").toExternalForm());
            private final ImageView iconeVer=new ImageView(imgVer);
            private final ImageView iconeEditar = new ImageView(imgEditar);
            private final ImageView iconeExcluir = new ImageView(imgExcluir);

            {
                //
                iconeVer.setFitWidth(30);
                iconeVer.setFitHeight(30);
                btnVer.setGraphic(iconeVer);
                //
                iconeEditar.setFitWidth(30);
                iconeEditar.setFitHeight(30);
                btnEditar.setGraphic(iconeEditar);
                //
                iconeExcluir.setFitWidth(30);
                iconeExcluir.setFitHeight(30);
                btnExcluir.setGraphic(iconeExcluir);
                //
                btnVer.getStyleClass().addAll("btn-read", "btn-crud-icone");
                btnEditar.getStyleClass().addAll("btn-update", "btn-crud-icone");
                btnExcluir.getStyleClass().addAll("btn-delete", "btn-crud-icone");
                // Adicione eventos aos botões (por exemplo, abrir um diálogo de visualização, edição ou exclusão)
                btnVer.setOnAction(event -> {
                    Projeto projeto = tbProjetosRecentes.getItems().get(getIndex());
                    verProjeto(projeto);
                    abaVerProjeto.toFront();
                });

                btnEditar.setOnAction(event -> {
                    Projeto projeto = getTableView().getItems().get(getIndex());
                    // Lógica para editar o projeto
                });

                btnExcluir.setOnAction(event -> {
                    Projeto projeto = getTableView().getItems().get(getIndex());
                    // Lógica para excluir o projeto
                });
        }

        @Override
        protected void updateItem(Projeto projeto, boolean empty) {
            super.updateItem(projeto, empty);
            if (empty) {
                setGraphic(null);
            } else {
                HBox hboxBtnCRUD = new HBox(btnVer, btnEditar, btnExcluir);
                setGraphic(hboxBtnCRUD);
                hboxBtnCRUD.getStyleClass().add("tb-hbox");
            }
        }
    });
    
        Resultado<ArrayList<Projeto>> resultado = repositorioProjeto.listarProjetosRecentes();

        if(resultado.foiErro()){
            Alert alert = new Alert(AlertType.ERROR, resultado.getMsg());
            alert.showAndWait();
        }
        List<Projeto> lista = (List<Projeto>)resultado.comoSucesso().getObj();
        tbProjetosRecentes.getItems().addAll(lista);
         
        /* 
         Resultado<ArrayList<Projeto>> resultado = repositorioProjeto.listarProjetosRecentes();
        if(resultado.foiErro()){
            Alert alert = new Alert(AlertType.ERROR, resultado.getMsg());
            alert.showAndWait();
        }
        List<Projeto> lista = (List<Projeto>)resultado.comoSucesso().getObj();

        testebox.getChildren().clear();

        for (Projeto projeto : lista) {
            TextField teste1=new TextField(projeto.getNome());
            TextField teste2=new TextField(projeto.getAreaEmpresa());
            TextArea teste3=new TextArea(projeto.getDescricao());
            VBox testeteste=new VBox(teste1, teste2, teste3);
            testebox.getChildren().add(testeteste);
            
        }*/

    }
}
