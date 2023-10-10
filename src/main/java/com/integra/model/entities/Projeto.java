package com.integra.model.entities;

public class Projeto {
    private int id;
    private String titulo;
    private String descricao;
    private String liguagemProgramacao;

    public Projeto(int id, String titulo, String descricao, String liguagemProgramacao) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.liguagemProgramacao = liguagemProgramacao;
    }

    public Projeto(String titulo, String descricao, String liguagemProgramacao) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.liguagemProgramacao = liguagemProgramacao;
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

    public String getLiguagemProgramacao() {
        return liguagemProgramacao;
    }

    public void setLiguagemProgramacao(String liguagemProgramacao) {
        this.liguagemProgramacao = liguagemProgramacao;
    }
    
    
}
