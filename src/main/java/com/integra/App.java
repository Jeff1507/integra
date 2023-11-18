package com.integra;

import com.integra.controllers.Cadastrar;
import com.integra.controllers.CadastrarEmpresa;
import com.integra.controllers.CadastrarEstudante;
import com.integra.controllers.DashboardEmpresa;
import com.integra.controllers.DashboardEstudante;
import com.integra.controllers.Login;
import com.integra.model.dao.ConexaoBD;
import com.integra.model.dao.EmpresaDAO;
import com.integra.model.dao.EstudanteDAO;
import com.integra.model.dao.JDBCEmpresaDAO;
import com.integra.model.dao.JDBCEstudanteDAO;
import com.integra.model.dao.JDBCProjetoDAO;
import com.integra.model.dao.JDBCSolucaoDAO;
import com.integra.model.dao.ProjetoDAO;
import com.integra.model.dao.SolucaoDAO;
import com.integra.model.repositories.RepositorioEmpresa;
import com.integra.model.repositories.RepositorioEstudante;
import com.integra.model.repositories.RepositorioProjeto;
import com.integra.model.repositories.RepositorioSolucao;

import io.github.hugoperlin.navigatorfx.BaseAppNavigator;
import io.github.hugoperlin.navigatorfx.ScreenRegistryFXML;

public class App extends BaseAppNavigator{

    EmpresaDAO empresaDAO = new JDBCEmpresaDAO(ConexaoBD.getInstance());
    RepositorioEmpresa repositorioEmpresa = new RepositorioEmpresa(empresaDAO);

    EstudanteDAO estudanteDAO = new JDBCEstudanteDAO(ConexaoBD.getInstance());
    RepositorioEstudante repositorioEstudante = new RepositorioEstudante(estudanteDAO);

    ProjetoDAO projetoDAO = new JDBCProjetoDAO(ConexaoBD.getInstance());
    RepositorioProjeto repositorioProjeto = new RepositorioProjeto(projetoDAO, empresaDAO);

    SolucaoDAO solucaoDAO = new JDBCSolucaoDAO(ConexaoBD.getInstance());
    RepositorioSolucao repositorioSolucao = new RepositorioSolucao(solucaoDAO, estudanteDAO, projetoDAO);

    public static void main(String[] args) {
        launch();
    }

    @Override
    public String getAppTitle() {
        return "INTEGRA";
    }

    @Override
    public String getHome() {
        return "LOGIN";
    }

    @Override
    public void registrarTelas() {
        registraTela("LOGIN", 
                     new ScreenRegistryFXML(App.class, "login.fxml", o->new Login(repositorioEmpresa, repositorioEstudante)));
        
        registraTela("CADASTRAR", 
                     new ScreenRegistryFXML(App.class, "cadastrar.fxml", o->new Cadastrar()));

        registraTela("CADASTRAREMPRESA", 
                     new ScreenRegistryFXML(App.class, "cadastrar_empresa.fxml", o->new CadastrarEmpresa(repositorioEmpresa)));

        registraTela("CADASTRARESTUDANTE", 
                     new ScreenRegistryFXML(App.class, "cadastrar_estudante.fxml", o->new CadastrarEstudante(repositorioEstudante)));

        registraTela("DASHBOARDEMPRESA", 
                     new ScreenRegistryFXML(App.class, "dashboard_empresa.fxml", o->new DashboardEmpresa(repositorioProjeto, repositorioEmpresa)));
        
        registraTela("DASHBOARDESTUDANTE", 
                     new ScreenRegistryFXML(App.class, "dashboard_estudante.fxml", o->new DashboardEstudante(repositorioEstudante,
                     repositorioProjeto, repositorioSolucao)));
        
    }
}