package com.integra.model.repositories;

import java.util.ArrayList;
import java.util.List;

import com.github.hugoperlin.results.Resultado;
import com.integra.model.dao.EstudanteDAO;
import com.integra.model.dao.ProjetoDAO;
import com.integra.model.dao.SolucaoDAO;
import com.integra.model.entities.Empresa;
import com.integra.model.entities.Estudante;
import com.integra.model.entities.Projeto;
import com.integra.model.entities.Solucao;

public class RepositorioSolucao {
    private SolucaoDAO solucaoDAO;
    private EstudanteDAO estudanteDAO;
    private ProjetoDAO projetoDAO;

    public RepositorioSolucao(SolucaoDAO solucaoDAO, EstudanteDAO estudanteDAO, ProjetoDAO projetoDAO){
        this.solucaoDAO = solucaoDAO;
        this.estudanteDAO = estudanteDAO;
        this.projetoDAO = projetoDAO;
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
    
    public Resultado<Solucao> montarSolucao(Solucao solucao){
        Resultado<Estudante> eResultado = estudanteDAO.estudanteSolucao(solucao.getId());

        if (eResultado.foiErro()) {
            return eResultado.comoErro();
        }
        Estudante estudante = (Estudante) eResultado.comoSucesso().getObj();
        solucao.setEstudanteSolucao(estudante);

        Resultado<Projeto> pResultado = projetoDAO.projetoSolucao(solucao.getId());

        if (pResultado.foiErro()) {
            return pResultado.comoErro();
        }
        Projeto projeto = (Projeto) pResultado.comoSucesso().getObj();
        solucao.setProjetoSolucao(projeto);

        return Resultado.sucesso("Solução montada", solucao);
    }
    public Resultado<Solucao> getById(int solucaoId){
        Resultado<Solucao> resultado = solucaoDAO.getById(solucaoId);

        if (resultado.foiSucesso()) {
            Solucao solucao = (Solucao) resultado.comoSucesso().getObj();

            return montarSolucao(solucao);
        }
        return resultado;
    }

    public Resultado<ArrayList<Solucao>> listarSolucaoEstudante(Estudante estudante){
        Resultado<ArrayList<Solucao>> resultado = solucaoDAO.listarSolucaoEstudante(estudante.getId());

        if (resultado.foiSucesso()) {
            List<Solucao> solucoes = (List<Solucao>) resultado.comoSucesso().getObj();

            for (Solucao solucao : solucoes) {
                Resultado<Solucao> resultado2 = montarSolucao(solucao);

                if (resultado2.foiErro()) {
                    return resultado2.comoErro();
                }
            }
            estudante.setSolucoes(solucoes);
        }
        return resultado;
    }

    public Resultado<ArrayList<Solucao>> listarSolucaoProjeto(Projeto projeto){
        Resultado<ArrayList<Solucao>> resultado = solucaoDAO.listarSolucaoProjeto(projeto.getId());

        if (resultado.foiSucesso()) {
            List<Solucao> solucoes = (List<Solucao>) resultado.comoSucesso().getObj();

            for (Solucao solucao : solucoes) {
                Resultado<Solucao> resultado2 = montarSolucao(solucao);

                if (resultado2.foiErro()) {
                    return resultado2.comoErro();
                }
            }
            projeto.setSolucoes(solucoes);
        }
        return resultado;
    }

}
