package com.integra.model.entities;

import java.util.List;

public class Projeto {
    private int id;
    private String nome;
    private String descricao;
    private String areaEmpresa;
    private List<Solucao> solucoes;
    
    public Projeto(int id, String nome, String descricao, String areaEmpresa) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.areaEmpresa = areaEmpresa;
    }

    public Projeto(String nome, String descricao, String areaEmpresa) {
        this.nome = nome;
        this.descricao = descricao;
        this.areaEmpresa = areaEmpresa;
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

    public void setSolucoes(List<Solucao> solucoes) {
        this.solucoes = solucoes;
    }  
    
    
}
