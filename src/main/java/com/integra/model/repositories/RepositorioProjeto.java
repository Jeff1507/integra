package com.integra.model.repositories;

import java.util.ArrayList;
import java.util.List;

import com.github.hugoperlin.results.Resultado;
import com.integra.model.dao.EmpresaDAO;
import com.integra.model.dao.ProjetoDAO;
import com.integra.model.entities.Empresa;
import com.integra.model.entities.Projeto;

public class RepositorioProjeto {
    private ProjetoDAO projetoDAO;
    private EmpresaDAO empresaDAO;

    public RepositorioProjeto(ProjetoDAO projetoDAO, EmpresaDAO empresaDAO){
        this.projetoDAO = projetoDAO;
        this.empresaDAO = empresaDAO;
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

        Projeto projeto = new Projeto(nome, descricao, areaEmpresa, contaLogada);
       
        return projetoDAO.criar(projeto, contaLogada);
    }

    public Resultado<Projeto> montarProjeto(Projeto projeto){
        Resultado<Empresa> resultado = empresaDAO.empresaProjeto(projeto.getId());

        if (resultado.foiErro()) {
            return resultado.comoErro();
        }
        Empresa empresaProjeto = (Empresa) resultado.comoSucesso().getObj();
        projeto.setEmpresaProjeto(empresaProjeto);

        return Resultado.sucesso("Projeto associado a empresa", projeto);
    }

    public Resultado<Projeto> getById(int projetoId) {

        Resultado<Projeto> r0 = projetoDAO.getByid(projetoId);

        if (r0.foiSucesso()) {
            Projeto projeto = (Projeto) r0.comoSucesso().getObj();

            return montarProjeto(projeto);
        }
        return r0;
    }

    public Resultado<ArrayList<Projeto>> listarProjetosEmpresa(Empresa empresa){

        Resultado<ArrayList<Projeto>> resultado = projetoDAO.listarProjetoEmpresa(empresa.getId());

        if (resultado.foiErro()) {
            List<Projeto> projetos = (List<Projeto>) resultado.comoSucesso().getObj();
            for (Projeto projeto : projetos) {
                Resultado<Projeto> resultado2 = montarProjeto(projeto); 
                
                if(resultado2.foiErro()){
                    return resultado2.comoErro();
                }
            }
        }

        return resultado;
    }


    public Resultado<ArrayList<Projeto>> listarProjetosRecentes(){
        Resultado<ArrayList<Projeto>> resultado = projetoDAO.listarProjetosRecentes();
        if (resultado.foiSucesso()) {
            
            List<Projeto> projetos = (List<Projeto>) resultado.comoSucesso().getObj();

            for (Projeto projeto : projetos) {
                Resultado r1 = montarProjeto(projeto); 
                
                if(r1.foiErro()){
                    return r1;
                }

            }

        }
        return resultado;
    }
}
