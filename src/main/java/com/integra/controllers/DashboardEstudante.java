package com.integra.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.github.hugoperlin.results.Resultado;
import com.integra.App;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class DashboardEstudante implements Initializable{

    @FXML
    private Pane abaEditarConta, abaInicio, abaMinhasSolucoes, abaPesquisar, abaVerProjeto, abaVerConta
    , abaCriarSolucao, abaEditarSolucao, abaExcluirSolucao;

    @FXML
    private Button btnInicio, btnMinhasSolucoes, btnVerConta, btnFecharVerPerfil, btnEditarConta, btnFecharEditarConta
    , btnVerProjeto;

    @FXML
    private ToggleButton btnPesquisar;

    @FXML
    private VBox v1,v2, secao;

    @FXML
    private ScrollPane spExplorar, spMinhasSolucoes;

    @FXML
    private TextField tfBarraPesquisa, tfEditUserNome, tfEditUserEmail, tfEditUserSenha
    , tfNomeSolucao, tfEditNomeSolucao;

    @FXML
    private TextArea taDescricaoSolucao, taEditDescricaoSolucao;

    @FXML
    private ListView<Projeto> lstProjetosPesquisa;

    @FXML
    private Label lbPesquisaErro, lbUserNome, lbUserEmail, lbUserSolucoes, projTitulo, projArea, projUser
    , lbCriarSlcProjNome, lbCriarSlcProjArea, lbCriarSlcProJUser, lbExcluirNomeSolucao;

    @FXML
    private Text txtDescricao, txtDescricaoProj, txtExcluirSolucao;

    private Estudante contaLogada;
    private Projeto projetoAtual;
    private int idSolucaoAtual;
    private RepositorioEstudante repositorioEstudante;
    private RepositorioProjeto repositorioProjeto;
    private RepositorioSolucao repositorioSolucao;

    public DashboardEstudante(RepositorioEstudante repositorioEstudante, RepositorioProjeto repositorioProjeto, RepositorioSolucao repositorioSolucao){
        this.repositorioEstudante = repositorioEstudante;
        this.repositorioProjeto = repositorioProjeto;
        this.repositorioSolucao = repositorioSolucao;
    }

    @FXML
    private void abrirAba(ActionEvent event){
        if(event.getSource() == btnInicio){
            abaInicio.toFront();
        }
        else if (event.getSource() == btnMinhasSolucoes) {
            abaMinhasSolucoes.toFront();
        }
        else if (event.getSource() == btnVerConta) {
            verConta();
            abaVerConta.toFront();
        }
        else if (event.getSource() == btnFecharVerPerfil) {
            abaVerConta.toBack();
        }
        else if (event.getSource() == btnEditarConta) {
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
            }
        }
    }
    public void listarProjetosRecentes(){

        Resultado<ArrayList<Projeto>> resultado = repositorioProjeto.listarProjetosRecentes();

        if(resultado.foiErro()){
            Alert alert = new Alert(AlertType.ERROR, resultado.getMsg());
            alert.showAndWait();
        }
        List<Projeto> listaProjetosRecentes = (List<Projeto>) resultado.comoSucesso().getObj();

        
        v2.getChildren().add(secaoProjetos(listaProjetosRecentes));
        spExplorar.setFitToWidth(true);
        spExplorar.setContent(v2);
    }
    @FXML
    private void verProjeto(Projeto projeto){
        projTitulo.setText(projeto.getNome());
        projArea.setText(projeto.getAreaEmpresa());
        if (projeto.getEmpresaProjeto() != null) {
            projUser.setText("Criado por: " + projeto.getEmpresaProjeto().getNome());
            System.out.println(projeto.getEmpresaProjeto().getNome());
        } else {
            projUser.setText("Criado por: (Nome não disponível)");
        }
        txtDescricao.setText(projeto.getDescricao());

        Resultado<ArrayList<Solucao>> resultado = repositorioSolucao.listarSolucaoProjeto(projeto);
        List<Solucao> lista = (List<Solucao>) resultado.comoSucesso().getObj();

        secao.getChildren().clear();
        secao.getChildren().add(listarSolucoesProjeto(lista));

    }
    private VBox listarSolucoesProjeto(List<Solucao> solucaos){
        VBox secao = new VBox();
        secao.getChildren().clear();
        for (Solucao solucao : solucaos) {
            VBox vBox1 = new VBox();

            Label nome = new Label(solucao.getTitulo());
            nome.getStyleClass().add("projeto-lb");

            Label por = new Label("Criado por: "+solucao.getEstudanteSolucao().getNome());
            por.getStyleClass().add("lb");

            Text descricao = new Text(solucao.getDescricao());
            descricao.getStyleClass().add("lb");
            descricao.setFill(Color.WHITE);
            descricao.setWrappingWidth(1050);
            
            vBox1.getChildren().addAll(nome, por, descricao);
            vBox1.getStyleClass().add("projeto-box");

            secao.getChildren().add(vBox1);
            secao.getStyleClass().add("secao-projeto");
        }
        return secao;
    }
    private VBox secaoProjetos(List<Projeto> projetos){
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

            btnVer.setOnAction(event -> {
                    verProjeto(projeto);
                    abaVerProjeto.toFront();
            });

            Button btnSolucao = new Button("Criar Solução");
            btnSolucao.getStyleClass().addAll("btn-create", "btn-crud-secao-projeto", "btn-ver-completo");
            hBox1.getChildren().addAll(btnSolucao, btnVer);
            hBox1.getStyleClass().add("btns");
            hBox2.getChildren().addAll(hBox, hBox1);
            hBox2.getStyleClass().add("user-lb-e-btns");
            vBox1.getChildren().addAll(pjtNome, prjArea);

            btnSolucao.setOnAction(event -> {
                setCriarSolucao(projeto);
                abaCriarSolucao.toFront();
            });

            vBox2.getChildren().addAll(vBox1, hBox2);
            vBox2.getStyleClass().add("projeto-box");
            secao.getChildren().add(vBox2);
            secao.getStyleClass().add("secao-projeto");
        }
        return secao;
    }
    private void verMinhasSolucoes(){
        contaLogada = repositorioEstudante.contaLogada();

        Resultado<ArrayList<Solucao>> resultado = repositorioSolucao.listarSolucaoEstudante(contaLogada);

        if (resultado.foiErro()) {
            Alert alert = new Alert(AlertType.ERROR, resultado.getMsg());
            alert.showAndWait();
        }

        List<Solucao> solucaos = (List<Solucao>) resultado.comoSucesso().getObj();
        
        v1.getChildren().add(secaoSolucoes(solucaos));
        spMinhasSolucoes.setFitToWidth(true);
        spMinhasSolucoes.setContent(v1);
    }

    private VBox secaoSolucoes(List<Solucao> solucoes){
        VBox secao = new VBox();
        secao.getChildren().clear();

        for (Solucao solucao : solucoes) {
            VBox vBox1 = new VBox();
            VBox vBox2 = new VBox();
            HBox hBox1 = new HBox();
            HBox hBox2 = new HBox();
            HBox hBox = new HBox();

            Label slcNome = new Label(solucao.getTitulo());
            slcNome.getStyleClass().add("projeto-lb");
            Label slcProjeto = new Label("Solução de: "+solucao.getProjetoSolucao().getNome());
            slcProjeto.getStyleClass().add("lb");
            
            Label userNome = new Label("Criado por: "+solucao.getEstudanteSolucao().getNome());
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

            Image imgExcluir = new Image(getClass().getResource("/com/integra/img/delete_icon-removebg-preview.png").toExternalForm());
            ImageView iViewExcluir = new ImageView(imgExcluir);
            iViewExcluir.setFitWidth(40);
            iViewExcluir.setFitHeight(40);
            Button btnExcluir = new Button();
            btnExcluir.setGraphic(iViewExcluir);
            btnExcluir.getStyleClass().addAll("btn-delete", "btn-crud-secao-projeto");

            btnVer.setOnAction(event -> {
                verProjeto(solucao.getProjetoSolucao());
                abaVerProjeto.toFront();
            });
            btnEditar.setOnAction(event -> {
                setEditarSolucao(solucao);
                abaEditarSolucao.toFront();
            });

            btnExcluir.setOnAction(event -> {
                setExcluirSolucao(solucao);
                abaExcluirSolucao.toFront();
            });

            hBox1.getChildren().addAll(btnEditar, btnExcluir, btnVer);
            hBox1.getStyleClass().add("btns");
            hBox2.getChildren().addAll(hBox, hBox1);
            hBox2.getStyleClass().add("user-lb-e-btns");
            vBox1.getChildren().addAll(slcNome, slcProjeto);

            vBox2.getChildren().addAll(vBox1, hBox2);
            vBox2.getStyleClass().add("projeto-box");
            secao.getChildren().add(vBox2);
            secao.getStyleClass().add("secao-projeto");
        }
        return secao;
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
    private void atualizarListaPesquisa(List<Projeto> projetos){
        lstProjetosPesquisa.getItems().clear();
        lstProjetosPesquisa.getItems().addAll(projetos);
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
    private void atualizar(){
        v1.getChildren().clear();
        v2.getChildren().clear();

        verMinhasSolucoes();
        listarProjetosRecentes();
    }
    @FXML
    private void atualizar(ActionEvent event){
        atualizar();  
    }
    private void verConta(){
        lbUserNome.setText("Nome: "+contaLogada.getNome());
        lbUserEmail.setText("E-mail: "+contaLogada.getEmail());
        lbUserSolucoes.setText("Soluções: "+contaLogada.getSolucoes().size());
    }
    private void editarConta(){
        tfEditUserNome.setText(contaLogada.getNome());
        tfEditUserEmail.setText(contaLogada.getEmail());
        tfEditUserSenha.setText(contaLogada.getSenha());
    }
    @FXML
    private void editarPerfil(ActionEvent event){
        if (msgConfirmacao("Confirmação", 
                           "Esta ação é irreversível", 
                           "Tem certeza que deseja editar seu perfil?")) 
        {
            String nome = tfEditUserNome.getText();
            String email = tfEditUserEmail.getText();
            String senha = tfEditUserSenha.getText();

            Resultado<Estudante> resultado = repositorioEstudante.editarConta(contaLogada.getId(), nome, email, senha);

            Alert alert;
            
            if(resultado.foiErro()){
                alert = new Alert(AlertType.ERROR, resultado.getMsg());
            }else{
                alert = new Alert(AlertType.INFORMATION, resultado.getMsg());
            }
            alert.showAndWait();
        }
        
    }
    private void setCriarSolucao(Projeto projeto){
        lbCriarSlcProjNome.setText(projeto.getNome());
        lbCriarSlcProjArea.setText(projeto.getAreaEmpresa());
        lbCriarSlcProJUser.setText("Criado por: "+projeto.getEmpresaProjeto().getNome());
        txtDescricaoProj.setText(projeto.getDescricao());
        projetoAtual = projeto;
    }
    @FXML
    private void criarSolucao(ActionEvent event){
        String nome = tfNomeSolucao.getText();
        String descricao = taDescricaoSolucao.getText();

        contaLogada = repositorioEstudante.contaLogada();

        Resultado<Solucao> resultado = repositorioSolucao.criar(nome, descricao, contaLogada, projetoAtual);

        Alert alert;
        if (resultado.foiErro()) {
            alert = new Alert(AlertType.ERROR, resultado.getMsg());
            
        }
        else{
            alert = new Alert(AlertType.INFORMATION, resultado.getMsg());
        } 
        atualizar();
        abaMinhasSolucoes.toFront();
        alert.showAndWait();
    }
    private void setEditarSolucao(Solucao solucao){
        tfEditNomeSolucao.setText(solucao.getTitulo());
        taEditDescricaoSolucao.setText(solucao.getDescricao());
        idSolucaoAtual = solucao.getId();
    }
    @FXML
    private void editarSolucao(ActionEvent event){
        if (msgConfirmacao("Confirmação", 
                           "Esta ação é irreversível", 
                           "Tem certeza que deseja editar essa solução?")) 
        {
            String nome = tfEditNomeSolucao.getText();
            String descricao = taEditDescricaoSolucao.getText();

            Resultado<Solucao> resultado = repositorioSolucao.editarSolucao(idSolucaoAtual, nome, descricao);

            Alert alert;
            
            if(resultado.foiErro()){
                alert = new Alert(AlertType.ERROR, resultado.getMsg());
            }else{
                alert = new Alert(AlertType.INFORMATION, resultado.getMsg());
            }
            atualizar();
            abaMinhasSolucoes.toFront();
            alert.showAndWait();
        }
        
    }
    private void setExcluirSolucao(Solucao solucao){
        lbExcluirNomeSolucao.setText(solucao.getTitulo());
        txtExcluirSolucao.setText(solucao.getDescricao());
        idSolucaoAtual = solucao.getId();
    }
    @FXML
    private void excluirSolucao(ActionEvent event){
        if (msgConfirmacao("Confirmação", 
                           "Esta ação é irreversível", 
                           "Tem certeza que deseja excluir essa solução?")) 
        {
            Resultado<Solucao> resultado = repositorioSolucao.excluirSolucao(idSolucaoAtual);
            Alert alert;
        
            if(resultado.foiErro()){
                alert = new Alert(AlertType.ERROR, resultado.getMsg());
            }else{
                alert = new Alert(AlertType.INFORMATION, resultado.getMsg());
            }
            atualizar();
            abaMinhasSolucoes.toFront();
            alert.showAndWait();
        }
        
    }
    private static boolean msgConfirmacao(String titulo, String cabecalho, String conteudo){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(cabecalho);
        alert.setContentText(conteudo);

        ButtonType botaoSim = new ButtonType("Sim");
        ButtonType botaoNao = new ButtonType("Não");
        alert.getButtonTypes().setAll(botaoSim, botaoNao);

        alert.showAndWait();

        return alert.getResult() == botaoSim;
    }
    @FXML 
    private void desconectar(ActionEvent event){
        if (msgConfirmacao("Confirmação", 
                           "Desconectar", 
                           "Quer mesmo sair?")) 
        {
            App.pushScreen("LOGIN");
        }
        
    }
    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        verMinhasSolucoes();
        listarProjetosRecentes();
    }

}
