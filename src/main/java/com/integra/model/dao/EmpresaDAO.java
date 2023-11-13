package com.integra.model.dao;

import com.github.hugoperlin.results.Resultado;
import com.integra.model.entities.Empresa;
import com.integra.model.entities.Projeto;

public interface EmpresaDAO {
    Resultado<Empresa> cadastrar(Empresa empresa);

    Resultado<Empresa> getById(int id);

    Resultado<Empresa> empresaProjeto(int projetoId);

    String validarCadastro(String nome, String email);

    Resultado<Empresa> logar(String nome, String senha);

    Resultado<Empresa> atualizar(int id, Empresa nova);

    public String validarAtualizar(String nome, String email, int id);
}
