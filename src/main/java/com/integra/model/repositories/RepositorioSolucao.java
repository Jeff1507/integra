package com.integra.model.repositories;

import com.github.hugoperlin.results.Resultado;
import com.integra.model.dao.SolucaoDAO;
import com.integra.model.entities.Solucao;

public class RepositorioSolucao {
    private SolucaoDAO solucaoDAO;

    public RepositorioSolucao(SolucaoDAO solucaoDAO){
        this.solucaoDAO = solucaoDAO;
    }

    public Resultado<Solucao> criarSolucao(String titulo, String descricao){
        if(titulo.isBlank() || titulo.isEmpty()){
            return Resultado.erro("Título em branco!");
        }
        if(descricao.isBlank() || descricao.isEmpty()){
            return Resultado.erro("Descrição em branco!");
        }
        Solucao solucao = new Solucao(titulo, descricao);
        return solucaoDAO.criar(solucao);
    }
}
