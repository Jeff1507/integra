package com.integra.model.repositories;

import com.github.hugoperlin.results.Resultado;
import com.integra.model.dao.EmpresaDAO;
import com.integra.model.entities.Empresa;

public class RepositorioEmpresa {
    private EmpresaDAO empresaDAO;

    public RepositorioEmpresa(EmpresaDAO empresaDAO){
        this.empresaDAO = empresaDAO;
    }
    public Resultado<Empresa> cadastrar(String nome, String email, String senha){
        if (nome.isEmpty() || nome.isBlank()) {
            return Resultado.erro("Nome em branco!");
        }
        if (email.isEmpty() || email.isBlank()) {
            return Resultado.erro("Nome em branco!");
        }
        if (senha.isEmpty() || senha.isBlank()) {
            return Resultado.erro("Senha em branco!");
        }
        if (!empresaDAO.validarCadastro(nome, email).equals("Sucesso")) {
            return Resultado.erro(empresaDAO.validarCadastro(nome, email));
        }

        Empresa empresa = new Empresa(nome, email, senha);
        return empresaDAO.cadastrar(empresa);
    }
    public Resultado<Empresa> login(String nome, String senha){
        if (nome.isEmpty() || nome.isBlank()) {
            return Resultado.erro("Nome em branco!");
        }
        if (senha.isEmpty() || senha.isBlank()) {
            return Resultado.erro("Senha em branco!");
        }
        return empresaDAO.logar(nome, senha);

    }
}
