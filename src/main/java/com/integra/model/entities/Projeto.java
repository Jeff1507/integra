package com.integra.model.entities;

import java.util.List;

public class Projeto {
    private int id;
    private String titulo;
    private String descricao;
    private String areaEmpresa;
    private List<Solucao> solucoes;
    
    public Projeto(int id, String titulo, String descricao, String areaEmpresa) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.areaEmpresa = areaEmpresa;
    }

    public Projeto(String titulo, String descricao, String areaEmpresa) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.areaEmpresa = areaEmpresa;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
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
