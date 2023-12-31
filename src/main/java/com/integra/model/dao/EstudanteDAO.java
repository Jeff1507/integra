package com.integra.model.dao;

import com.github.hugoperlin.results.Resultado;
import com.integra.model.entities.Estudante;

public interface EstudanteDAO {
    Resultado<Estudante> cadastrar(Estudante estudante);

    String validarConta(String nome, String email);

    String validarEmail(String email);

    Resultado<Estudante> logar(String nome, String senha);

    Resultado<Estudante> atualizar(int id, Estudante novo);

    public String validarAtualizar(String nome, String email, int id);

    Resultado<Estudante> getById(int id);

    Resultado<Estudante> estudanteSolucao(int solucaoId);
}
