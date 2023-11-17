package com.integra.model.dao;

import com.github.hugoperlin.results.Resultado;
import com.integra.model.entities.Estudante;

public interface EstudanteDAO {
    Resultado<Estudante> cadastrar(Estudante estudante);

    String validarConta(String nome, String email);

    String validarEmail(String email);

    Resultado<Estudante> atualizar(int id, Estudante novo);
}
