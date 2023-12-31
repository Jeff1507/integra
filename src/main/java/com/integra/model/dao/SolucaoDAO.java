package com.integra.model.dao;

import java.util.ArrayList;

import com.github.hugoperlin.results.Resultado;
import com.integra.model.entities.Estudante;
import com.integra.model.entities.Projeto;
import com.integra.model.entities.Solucao;

public interface SolucaoDAO {
    Resultado<Solucao> criar(Solucao solucao, Estudante contaLogada, Projeto projeto);

    Resultado<Solucao> getById(int id);
    Resultado<ArrayList<Solucao>> listarSolucaoProjeto(int idProjeto);
    Resultado<ArrayList<Solucao>> listarSolucaoEstudante(int idEstudante);

    Resultado<Solucao> editar(int id, Solucao nova);

    Resultado<Solucao> excluir(int id);
}
