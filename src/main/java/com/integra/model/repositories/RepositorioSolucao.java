package com.integra.model.repositories;

import com.github.hugoperlin.results.Resultado;
import com.integra.model.dao.SolucaoDAO;
import com.integra.model.entities.Estudante;
import com.integra.model.entities.Projeto;
import com.integra.model.entities.Solucao;

public class RepositorioSolucao {
    private SolucaoDAO solucaoDAO;

    public RepositorioSolucao(SolucaoDAO solucaoDAO){
        this.solucaoDAO = solucaoDAO;
    }
    public Resultado<Solucao> criar(String nome, String descricao, Estudante contaLogada, Projeto projeto){
        if(nome.isBlank() || nome.isEmpty()){
            return Resultado.erro("Nome em branco!");
        }
        if(descricao.isBlank() || descricao.isEmpty()){
            return Resultado.erro("Descrição em branco!");
        }
        Solucao solucao = new Solucao(nome, descricao, contaLogada, projeto);
        return solucaoDAO.criar(solucao, contaLogada, projeto);
    }

}
