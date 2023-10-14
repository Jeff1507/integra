package com.integra.model.dao;

import com.github.hugoperlin.results.Resultado;
import com.integra.model.entities.Solucao;

public interface SolucaoDAO {
    Resultado<Solucao> criar(Solucao solucao);

    Resultado getById(int id);
    Resultado listarSolucaoProblema(int idProblema);
    Resultado listarSolucaoEstudante(int idEstudante);

    Resultado editar(int id, Solucao nova);

    Resultado excluir(int id);
}
