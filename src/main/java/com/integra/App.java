package com.integra;

import com.integra.controllers.Cadastrar;
import com.integra.controllers.CadastrarEmpresa;
import com.integra.controllers.CadastrarEstudante;
import com.integra.controllers.DashboardEmpresa;
import com.integra.controllers.Login;

import io.github.hugoperlin.navigatorfx.BaseAppNavigator;
import io.github.hugoperlin.navigatorfx.ScreenRegistryFXML;

public class App extends BaseAppNavigator{

    public static void main(String[] args) {
        launch();
    }

    @Override
    public String getAppTitle() {
        return "INTEGRA";
    }

    @Override
    public String getHome() {
        return "DASHBOARDEMPRESA";
    }

    @Override
    public void registrarTelas() {
        registraTela("LOGIN", 
                     new ScreenRegistryFXML(App.class, "login.fxml", o->new Login()));
        
        registraTela("CADASTRAR", 
                     new ScreenRegistryFXML(App.class, "cadastrar.fxml", o->new Cadastrar()));

        registraTela("CADASTRAREMPRESA", 
                     new ScreenRegistryFXML(App.class, "cadastrar_empresa.fxml", o->new CadastrarEmpresa()));

        registraTela("CADASTRARESTUDANTE", 
                     new ScreenRegistryFXML(App.class, "cadastrar_estudante.fxml", o->new CadastrarEstudante()));

        registraTela("DASHBOARDEMPRESA", 
                     new ScreenRegistryFXML(App.class, "dashboard_empresa.fxml", o->new DashboardEmpresa()));
        
        
    }
}