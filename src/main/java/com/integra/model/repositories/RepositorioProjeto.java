package com.integra.model.repositories;

import java.util.ArrayList;
import java.util.List;

import com.github.hugoperlin.results.Resultado;
import com.integra.model.dao.ProjetoDAO;
import com.integra.model.entities.Empresa;
import com.integra.model.entities.Projeto;

public class RepositorioProjeto {
    private ProjetoDAO projetoDAO;

    public RepositorioProjeto(ProjetoDAO projetoDAO){
        this.projetoDAO = projetoDAO;
    }

    public Resultado<Projeto> criarProjeto(Empresa contaLogada, String nome, String areaEmpresa, String descricao){

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
        /* 
        List<Projeto> projetos = new ArrayList<>();
        projetos.add(projeto);
        contaLogada.setProjetos(projetos);
        */
        return projetoDAO.criar(projeto, contaLogada.getId());
    }

    public Resultado<ArrayList<Projeto>> listarProjetosEmpresa(Empresa empresa){

        Resultado<ArrayList<Projeto>> resultado = projetoDAO.listarProjetoEmpresa(empresa.getId());
        List<Projeto> projetos = (List<Projeto>) resultado.comoSucesso().getObj();

        empresa.setProjetos(projetos);

        return resultado;
    }

    public Resultado<ArrayList<Projeto>> listarProjetosRecentes(){
        return projetoDAO.listarProjetosRecentes();
    }
}
