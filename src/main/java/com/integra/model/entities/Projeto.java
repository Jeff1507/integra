package com.integra.model.entities;

import java.util.List;

public class Projeto {
    private int id;
    private String nome;
    private String descricao;
    private String areaEmpresa;
    private Empresa empresaProjeto;
    private List<Solucao> solucoes;
    
    public Projeto(int id, String nome, String descricao, String areaEmpresa, Empresa empresaProjeto) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.areaEmpresa = areaEmpresa;
        this.empresaProjeto = empresaProjeto;
    }

    public Projeto(String nome, String descricao, String areaEmpresa, Empresa empresaProjeto) {
        this.nome = nome;
        this.descricao = descricao;
        this.areaEmpresa = areaEmpresa;
        this.empresaProjeto = empresaProjeto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getAreaEmpresa() {
        return areaEmpresa;
    }

    public void setAreaEmpresa(String areaEmpresa) {
        this.areaEmpresa = areaEmpresa;
    }

    public List<Solucao> getSolucoes() {
        return solucoes;
    }

    public int getNumSolucoes(){
        return solucoes.size();
    }

    public void setSolucoes(List<Solucao> solucoes) {
        this.solucoes = solucoes;
    }
    
    @Override
    public String toString() {
        return "Projeto [id=" + id + ", nome=" + nome + ", descricao=" + descricao + ", areaEmpresa=" + areaEmpresa
                + ", solucoes=" + solucoes + "]";
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Projeto outro = (Projeto) obj;
        if (id != outro.id) {
            return false;
        }
        if (nome == null) {
            if (outro.nome != null) {
                return false;
            }
        }
        else if (!nome.equals(outro.nome)) {
            return false;
        }

        if (areaEmpresa == null) {
            if (outro.areaEmpresa != null) {
                return false;
            }
        }
        else if (!areaEmpresa.equals(outro.areaEmpresa)) {
            return false;
        }

        if (descricao == null) {
            if (outro.descricao != null) {
                return false;
            }
        }
        else if (!descricao.equals(outro.descricao)) {
            return false;
        }
        return true;
    }

    public Empresa getEmpresaProjeto() {
        return empresaProjeto;
    }

    public void setEmpresaProjeto(Empresa empresaProjeto) {
        this.empresaProjeto = empresaProjeto;
    }
    
    
    
}
