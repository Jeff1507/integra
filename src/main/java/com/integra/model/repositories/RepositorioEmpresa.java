package com.integra.model.repositories;

import com.github.hugoperlin.results.Resultado;
import com.integra.model.dao.EmpresaDAO;
import com.integra.model.entities.Empresa;

public class RepositorioEmpresa {
    private EmpresaDAO empresaDAO;

    private Empresa contaLogada;

    public RepositorioEmpresa(EmpresaDAO empresaDAO){
        this.empresaDAO = empresaDAO;
    }
    public Resultado<Empresa> cadastrar(String nome, String email, String senha){
        if (nome.isEmpty() || nome.isBlank()) {
            return Resultado.erro("Nome em branco!");
        }
        if (email.isEmpty() || email.isBlank()) {
            return Resultado.erro("E-mail em branco!");
        }
        if (senha.isEmpty() || senha.isBlank()) {
            return Resultado.erro("Senha em branco!");
        }
        /*if (!empresaDAO.validarCadastro(nome, email).equals("Sucesso")) {
            return Resultado.erro(empresaDAO.validarCadastro(nome, email));
        }*/
        if (!empresaDAO.validarEmail(email).equals("Sucesso")) {
            return Resultado.erro(empresaDAO.validarEmail(email));
        }
        if (!empresaDAO.validarConta(nome, email).equals("Sucesso")) {
            return Resultado.erro(empresaDAO.validarConta(nome, email));
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
        Resultado<Empresa> resultado = empresaDAO.logar(nome, senha);
        if (resultado.foiSucesso()) {
            setContaLogada(resultado);
        }
        return resultado;

    }
    public void setContaLogada(Resultado<Empresa> resultado){
        contaLogada = (Empresa) resultado.comoSucesso().getObj();
    }
    public Empresa contaLogada(){
        return contaLogada;
    }
    public Resultado<Empresa> editarConta(int id, String nome, String email, String senha){
        if (nome.isEmpty() || nome.isBlank()) {
            return Resultado.erro("Nome em branco!");
        }
        if (email.isEmpty() || email.isBlank()) {
            return Resultado.erro("E-mail em branco!");
        }
        if (senha.isEmpty() || senha.isBlank()) {
            return Resultado.erro("Senha em branco!");
        }
        if (!empresaDAO.validarAtualizar(nome, email, id).equals("Sucesso")) {
            return Resultado.erro(empresaDAO.validarAtualizar(nome, email, id));
        }
        Empresa novaEmpresa = new Empresa(nome, email, senha);
        return empresaDAO.atualizar(id, novaEmpresa);
    }
}
