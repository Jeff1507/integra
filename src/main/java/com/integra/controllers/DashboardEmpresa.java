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
import javafx.scene.control.MenuItem;
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
import javafx.scene.text.Text;

public class DashboardEmpresa implements Initializable{

    @FXML
    private TextField tfNomeProjeto, 
    tfEditNomeConta, tfEditEmailConta, tfEditSenhaConta,
    tfEditNomePjt, tfEditAreaPjt,
    tfBarraPesquisa;

    @FXML
    private TextField tfAreaEmpresa;

    @FXML
    private TextArea taDescricao, taDescricaoAtual, taEditDescricao;
    
    @FXML
    private TableView<Projeto> tbProjetosRecentes;

    @FXML
    private TableColumn<Projeto, String> tbNome, tbAreEmpresa;

    @FXML
    private TableColumn<Projeto, Projeto> tbAcoes;

    @FXML
    private Label lbNomeProjeto, lbAreaEmpresa, lbDescricao, 
                  lbUserNome, lbUserEmail, lbUserProjetos,
                  lbExcluirNomePjt, lbExcluirAreaPjt, lbteste;

    @FXML
    private Pane abaCriarProjeto, abaInicio, abaVerProjeto, abaMeusProjetos, abaVerConta, abaEditarConta, abaEditarProjeto,
    abaExcluirProjeto, abaPesquisar;
    
    @FXML
    private Button btn_criar_projeto, btn_inicio, btn_meus_projetos, btnVerConta, btnFecharVerPerfil, btn_editar_conta
    , btnFecharEditarConta, btnPesquisar;

    @FXML
    private VBox secaoProjeto;

    @FXML
    private VBox v1, v2;

    @FXML
    private Text txExcluirDescricaoPjt;

    @FXML
    private ScrollPane spExplorar, spMeusProjetos;

    @FXML
    private ListView<Projeto> lstProjetosPesquisa;

    private RepositorioProjeto repositorioProjeto;
    private RepositorioEmpresa repositorioEmpresa;
    private Empresa contaLogada;
    private int idProjetoAtual;

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
            abaPesquisar.toFront();
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

        //listarProjetos(listaMeusProjetos);
        //v1.getChildren().clear();
        v1.getChildren().add(secaoProjetos(listaMeusProjetos));
        spMeusProjetos.setFitToWidth(true);
        spMeusProjetos.setContent(v1);
    }

    public void listarProjetosRecentes(){
        contaLogada = repositorioEmpresa.contaLogada();

        Resultado<ArrayList<Projeto>> resultado = repositorioProjeto.listarProjetosRecentes();

        if(resultado.foiErro()){
            Alert alert = new Alert(AlertType.ERROR, resultado.getMsg());
            alert.showAndWait();
        }
        List<Projeto> listaProjetosRecentes = (List<Projeto>)resultado.comoSucesso().getObj();

        //listarProjetos(listaProjetosRecentes);
        //v2.getChildren().clear();
        v2.getChildren().add(secaoProjetos(listaProjetosRecentes));
        spExplorar.setFitToWidth(true);
        spExplorar.setContent(v2);
    }
    
    private void verConta(){
        lbUserNome.setText("Nome: "+contaLogada.getNome());
        lbUserEmail.setText("E-mail: "+contaLogada.getEmail());
        lbUserProjetos.setText("Projetos: "+contaLogada.getProjetos().size());
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

        Resultado<Empresa> resultado = repositorioEmpresa.editarConta(contaLogada.getId(), nome, email, senha);

        Alert alert;
        
        if(resultado.foiErro()){
            alert = new Alert(AlertType.ERROR, resultado.getMsg());
        }else{
            alert = new Alert(AlertType.INFORMATION, resultado.getMsg());
        }

        alert.showAndWait();
    }
    @FXML
    private void setEditarProjeto(Projeto projeto){
        tfEditNomePjt.setText(projeto.getNome());
        tfEditAreaPjt.setText(projeto.getAreaEmpresa());
        taEditDescricao.setText(projeto.getDescricao());
        idProjetoAtual = projeto.getId();
    }
    @FXML
    private void editarProjeto(ActionEvent event){
        String nome = tfEditNomePjt.getText();
        String area = tfEditAreaPjt.getText();
        String descricao = taEditDescricao.getText();

        Resultado<Projeto> resultado = repositorioProjeto.editarProjeto(idProjetoAtual, nome, area, descricao);

        Alert alert;
        
        if(resultado.foiErro()){
            alert = new Alert(AlertType.ERROR, resultado.getMsg());
        }else{
            alert = new Alert(AlertType.INFORMATION, resultado.getMsg());
        }

        alert.showAndWait();
    }
    private void setExcluirProjeto(Projeto projeto){
        lbExcluirNomePjt.setText(projeto.getNome());
        lbExcluirAreaPjt.setText(projeto.getAreaEmpresa());
        txExcluirDescricaoPjt.setText(projeto.getDescricao());
        idProjetoAtual = projeto.getId();
    }
    @FXML
    private void excluirProjeto(ActionEvent event){
        Resultado<Projeto> resultado = repositorioProjeto.excluirProjeto(idProjetoAtual);

        Alert alert;
        
        if(resultado.foiErro()){
            alert = new Alert(AlertType.ERROR, resultado.getMsg());
        }else{
            alert = new Alert(AlertType.INFORMATION, resultado.getMsg());
        }

        alert.showAndWait();
    }
    /* 
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
            if (contaLogada.getProjetos().contains(projeto)) {
                System.out.println("sexo");
            }
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
    */
    @FXML
    private void atualizar(ActionEvent event){
        v1.getChildren().clear();
        v2.getChildren().clear();

        verMeusProjetos();
        listarProjetosRecentes();    
    }
    @FXML
    private void pesquisarProjeto(KeyEvent keyEvent){
        String pesquisa = tfBarraPesquisa.getText();

        if (!tfBarraPesquisa.getText().isEmpty() || !tfBarraPesquisa.getText().isBlank()) {
            Resultado<ArrayList<Projeto>> resultado = repositorioProjeto.filtraPorNome(pesquisa);
            List<Projeto> projetos = (List<Projeto>) resultado.comoSucesso().getObj();

            if (resultado.foiSucesso()) {
                atualizarListaPesquisa(projetos);
            }
        }
    }
    private void atualizarListaPesquisa(List<Projeto> projetos){
        lstProjetosPesquisa.getItems().clear();
        lstProjetosPesquisa.getItems().addAll(projetos);
    }
    public VBox secaoProjetos(List<Projeto> projetos){
        VBox secao = new VBox();
        secao.getChildren().clear();

        for (Projeto projeto : projetos) {
            VBox vBox1 = new VBox();
            VBox vBox2 = new VBox();
            HBox hBox1 = new HBox();
            HBox hBox2 = new HBox();
            HBox hBox = new HBox();

            Label pjtNome = new Label(projeto.getNome());
            pjtNome.getStyleClass().add("projeto-lb");
            Label prjArea = new Label(projeto.getAreaEmpresa());
            prjArea.getStyleClass().add("lb");

            Label userNome = new Label("Criado por: "+projeto.getEmpresaProjeto().getNome());
            userNome.getStyleClass().add("user-lb");
            hBox.getChildren().add(userNome);
            hBox.getStyleClass().add("user-nome");

            Button btnVer = new Button("Ver Completo");
            btnVer.getStyleClass().addAll("btn-read", "btn-crud-secao-projeto", "btn-ver-completo");

            Image imgEditar = new Image(getClass().getResource("/com/integra/img/edit-icon-removebg-preview.png").toExternalForm());
            ImageView iViewEdit = new ImageView(imgEditar);
            iViewEdit.setFitWidth(40);
            iViewEdit.setFitHeight(40);
            Button btnEditar = new Button();
            btnEditar.setGraphic(iViewEdit);
            btnEditar.getStyleClass().addAll("btn-update", "btn-crud-secao-projeto");
            btnEditar.setVisible(false);

            Image imgExcluir = new Image(getClass().getResource("/com/integra/img/delete_icon-removebg-preview.png").toExternalForm());
            ImageView iViewExcluir = new ImageView(imgExcluir);
            iViewExcluir.setFitWidth(40);
            iViewExcluir.setFitHeight(40);
            Button btnExcluir = new Button();
            btnExcluir.setGraphic(iViewExcluir);
            btnExcluir.getStyleClass().addAll("btn-delete", "btn-crud-secao-projeto");
            btnExcluir.setVisible(false);

            btnVer.setOnAction(event -> {
                    verProjeto(projeto);
                    abaVerProjeto.toFront();
                });
            if (contaLogada.getProjetos().contains(projeto)) {

                btnEditar.setVisible(true);
                btnExcluir.setVisible(true);

                btnEditar.setOnAction(event -> {
                    setEditarProjeto(projeto);
                    abaEditarProjeto.toFront();
                });

                btnExcluir.setOnAction(event -> {
                    setExcluirProjeto(projeto);
                    abaExcluirProjeto.toFront();
                });
                
            }

            hBox1.getChildren().addAll(btnEditar, btnExcluir, btnVer);
            hBox1.getStyleClass().add("btns");
            hBox2.getChildren().addAll(hBox, hBox1);
            hBox2.getStyleClass().add("user-lb-e-btns");
            vBox1.getChildren().addAll(pjtNome, prjArea);

            vBox2.getChildren().addAll(vBox1, hBox2);
            vBox2.getStyleClass().add("projeto-box");
            secao.getChildren().add(vBox2);
            secao.getStyleClass().add("secao-projeto");
        }
        return secao;
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
        listarProjetosRecentes();
        System.out.println(contaLogada.getProjetos());

    }
}
