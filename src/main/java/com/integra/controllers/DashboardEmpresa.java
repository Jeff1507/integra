package com.integra.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.github.hugoperlin.results.Resultado;
import com.integra.App;
import com.integra.model.entities.Empresa;
import com.integra.model.entities.Projeto;
import com.integra.model.repositories.RepositorioEmpresa;
import com.integra.model.repositories.RepositorioProjeto;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Pagination;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;

public class DashboardEmpresa implements Initializable{

    @FXML
    private TextField tfNomeProjeto;

    @FXML
    private TextField tfAreaEmpresa;

    @FXML
    private TextArea taDescricao, taDescricaoAtual;
    
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
    private Pane abaCriarProjeto, abaInicio, abaVerProjeto, abaMeusProjetos;
    
    @FXML
    private Button btn_criar_projeto, btn_inicio, btn_meus_projetos;

    @FXML
    private VBox secaoProjeto;

    @FXML
    private ScrollPane sp;

    private RepositorioProjeto repositorioProjeto;
    private RepositorioEmpresa repositorioEmpresa;
    Empresa contaLogada;

    public DashboardEmpresa(RepositorioProjeto repositorioProjeto, RepositorioEmpresa repositorioEmpresa){
        this.repositorioProjeto = repositorioProjeto;
        this.repositorioEmpresa = repositorioEmpresa;
    }
 
    @FXML
    private void abrirAba(ActionEvent event){
        if (event.getSource() == btn_inicio) {
            abaInicio.toFront();
        }
        else if(event.getSource() == btn_criar_projeto){
            abaCriarProjeto.toFront();
        }
        else if(event.getSource() == btn_meus_projetos){
            abaMeusProjetos.toFront();
        }
    }

    @FXML
    void criarProjeto(ActionEvent event){
        String nome = tfNomeProjeto.getText();
        String areEmpresa = tfAreaEmpresa.getText();
        String descricao = taDescricao.getText();

        //Empresa contaLogada = repositorioEmpresa.contaLogada();
        contaLogada = repositorioEmpresa.contaLogada();

        Resultado<Projeto> resultado = repositorioProjeto.criarProjeto(contaLogada, nome, areEmpresa, descricao);

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
    private void desconectar(ActionEvent event){

        App.pushScreen("LOGIN");
    }
    @FXML
    private VBox mostraProjetoAtual;
    
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
    
    private void verMeusProjetos(){
        contaLogada = repositorioEmpresa.contaLogada();

        Resultado<ArrayList<Projeto>> resultado = repositorioProjeto.listarProjetosEmpresa(contaLogada);

        if (resultado.foiErro()) {
            Alert alert = new Alert(AlertType.ERROR, resultado.getMsg());
            alert.showAndWait();
        }
        List<Projeto> listaMeusProjetos = (List<Projeto>)resultado.comoSucesso().getObj();

        listarProjetos(listaMeusProjetos);
    }

    public void listarProjetosRecentes(){
        contaLogada = repositorioEmpresa.contaLogada();

        Resultado<ArrayList<Projeto>> resultado = repositorioProjeto.listarProjetosRecentes();

        if(resultado.foiErro()){
            Alert alert = new Alert(AlertType.ERROR, resultado.getMsg());
            alert.showAndWait();
        }
        List<Projeto> listaProjetosRecentes = (List<Projeto>)resultado.comoSucesso().getObj();

        listarProjetos(listaProjetosRecentes);
    }

    public void listarProjetos(List<Projeto> lista){

        secaoProjeto.getChildren().clear();

        for (Projeto projeto : lista) {

            VBox usuarioPerfil = new VBox();
            VBox lbProjeto = new VBox();
            VBox mostraProjeto = new VBox();
            HBox btnProjeto = new HBox();
            HBox projetoBox = new HBox();

            Image imgPerfil = new Image(getClass().getResource("/com/integra/img/482979.png").toExternalForm());
            ImageView iViewPerfil = new ImageView(imgPerfil);
            Button btnaaa=new Button();
            iViewPerfil.setFitWidth(30);
            iViewPerfil.setFitHeight(30);
            btnaaa.setGraphic(iViewPerfil);
            btnaaa.getStyleClass().add("btn-crud-icone");

            Label usuarioNome = new Label(projeto.getEmpresaProjeto().getNome());
            Label usuarioTipo = new Label("Empresa");
            
            Label tituloProjeto = new Label(projeto.getNome());
            
            tituloProjeto.getStyleClass().add("projeto-lb");

            Label areaProjeto = new Label(projeto.getAreaEmpresa());
            areaProjeto.getStyleClass().add("lb");
            Button btnVer = new Button("Ver Completo");
            Button btnEditar = new Button("Editar");
            Button btnExcluir = new Button("Excluir");
            

            usuarioPerfil.getChildren().addAll(btnaaa, usuarioNome, usuarioTipo);
            usuarioNome.getStyleClass().add("user-lb");
            usuarioTipo.getStyleClass().add("user-lb");
            usuarioPerfil.getStyleClass().add("usuario-perfil");

            lbProjeto.getChildren().addAll(tituloProjeto, areaProjeto);
            btnProjeto.getChildren().add(btnVer);
             
            btnVer.setOnAction(event ->{
                    verProjeto(projeto);
                    abaVerProjeto.toFront();
            });

            btnVer.getStyleClass().addAll("btn-read", "btn-crud-secao-projeto");
            btnProjeto.getStyleClass().add("btn-projeto");
            mostraProjeto.getChildren().addAll(lbProjeto, btnProjeto);
            mostraProjeto.getStyleClass().add("mostra-projeto");

            projetoBox.getChildren().addAll(usuarioPerfil, mostraProjeto);
            projetoBox.getStyleClass().add("projeto-box");

            secaoProjeto.getChildren().add(projetoBox);
            secaoProjeto.getStyleClass().add("secao-projeto");

            sp.setFitToWidth(true);
            sp.setContent(secaoProjeto);
        }
    }
    public void secaoProjeto(List<Projeto> projetos, VBox secao){

    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        /*tbNome.setCellValueFactory(celula -> new SimpleStringProperty(celula.getValue().getNome()));
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
         */
        
        /*contaLogada = repositorioEmpresa.contaLogada();
        Resultado<ArrayList<Projeto>> resultado = repositorioProjeto.listarProjetosEmpresa(contaLogada.getId());
        if(resultado.foiErro()){
            Alert alert = new Alert(AlertType.ERROR, resultado.getMsg());
            alert.showAndWait();
        }
        List<Projeto> lista = (List<Projeto>)resultado.comoSucesso().getObj();

        secaoProjeto.getChildren().clear();

        for (Projeto projeto : lista) {
            

            VBox usuarioPerfil = new VBox();
            VBox lbProjeto = new VBox();
            VBox mostraProjeto = new VBox();
            HBox btnProjeto = new HBox();
            HBox projetoBox = new HBox();

            Image imgPerfil = new Image(getClass().getResource("/com/integra/img/482979.png").toExternalForm());
            ImageView iViewPerfil = new ImageView(imgPerfil);
            Button btnaaa=new Button();
            iViewPerfil.setFitWidth(30);
            iViewPerfil.setFitHeight(30);
            btnaaa.setGraphic(iViewPerfil);
            btnaaa.getStyleClass().add("btn-crud-icone");

            Label usuarioNome = new Label(contaLogada.getNome());
            Label usuarioTipo = new Label("Empresa");
            
            Label tituloProjeto = new Label(projeto.getNome());
            
            tituloProjeto.getStyleClass().add("projeto-lb");

            Label areaProjeto = new Label(projeto.getAreaEmpresa());
            areaProjeto.getStyleClass().add("lb");
            Button btnVer = new Button("Ver Completo");
            

            usuarioPerfil.getChildren().addAll(btnaaa, usuarioNome, usuarioTipo);
            usuarioNome.getStyleClass().add("user-lb");
            usuarioTipo.getStyleClass().add("user-lb");
            usuarioPerfil.getStyleClass().add("usuario-perfil");

            lbProjeto.getChildren().addAll(tituloProjeto, areaProjeto);
            btnProjeto.getChildren().add(btnVer);

            btnVer.setOnAction(event ->{
                    verProjeto(projeto);
                    abaVerProjeto.toFront();
            });

            btnVer.getStyleClass().addAll("btn-read", "btn-crud-secao-projeto");
            btnProjeto.getStyleClass().add("btn-projeto");
            mostraProjeto.getChildren().addAll(lbProjeto, btnProjeto);
            mostraProjeto.getStyleClass().add("mostra-projeto");

            projetoBox.getChildren().addAll(usuarioPerfil, mostraProjeto);
            projetoBox.getStyleClass().add("projeto-box");

            secaoProjeto.getChildren().add(projetoBox);
            secaoProjeto.getStyleClass().add("secao-projeto");

            sp.setFitToWidth(true);
            sp.setContent(secaoProjeto);

        }*/
        verMeusProjetos();
        System.out.println(contaLogada.getProjetos());

    }
}
