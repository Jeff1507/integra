package com.integra.model.entities;

public class Solucao {
    private int id;
    private String titulo;
    private String linguagemProgramacao;
    private String descricao;
    
    public Solucao(String titulo, String linguagemProgramacao, String descricao) {
        this.titulo = titulo;
        this.linguagemProgramacao = linguagemProgramacao;
        this.descricao = descricao;
    }
    public Solucao(int id, String titulo, String linguagemProgramacao, String descricao) {
        this.id = id;
        this.titulo = titulo;
        this.linguagemProgramacao = linguagemProgramacao;
        this.descricao = descricao;
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
    public String getLinguagemProgramacao() {
        return linguagemProgramacao;
    }
    public void setLinguagemProgramacao(String linguagemProgramacao) {
        this.linguagemProgramacao = linguagemProgramacao;
    }
    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    
}
