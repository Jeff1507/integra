package com.integra.model.repositories;

import java.util.ArrayList;

import com.github.hugoperlin.results.Resultado;
import com.integra.model.dao.ProjetoDAO;
import com.integra.model.entities.Projeto;

public class RepositorioProjeto {
    private ProjetoDAO projetoDAO;

    public RepositorioProjeto(ProjetoDAO projetoDAO){
        this.projetoDAO = projetoDAO;
    }

    public Resultado<Projeto> criarProjeto(int empresaId, String nome, String areaEmpresa, String descricao){

        if(nome.isBlank() || nome.isEmpty()){
            return Resultado.erro("Nome em branco!");
        }
        if(areaEmpresa.isBlank() || areaEmpresa.isEmpty()){
            return Resultado.erro("Area de atuação em branco!");
        }
        if(descricao.isBlank() || descricao.isEmpty()){
            return Resultado.erro("Descrição em branco!");
        }
        Projeto projeto = new Projeto(nome, descricao, areaEmpresa);
        return projetoDAO.criar(projeto, empresaId);
    }

    public Resultado<ArrayList<Projeto>> listarProjetosEmpresa(int empresaId){
        return projetoDAO.listarProjetoEmpresa(empresaId);
    }

    public Resultado<ArrayList<Projeto>> listarProjetosRecentes(){
        return projetoDAO.listarProjetosRecentes();
    }
}
