package com.integra.model.entities;

public class Solucao {
    private int id;
    private String titulo;
    private String descricao;
    private Estudante estudanteSolucao;
    private Projeto projetoSolucao;
    
    public Solucao(String titulo, String descricao, Estudante estudanteSolucao, Projeto projetoSolucao) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.estudanteSolucao = estudanteSolucao;
        this.projetoSolucao = projetoSolucao;
    }
    public Solucao(int id, String titulo, String descricao, Estudante estudanteSolucao, Projeto projetoSolucao) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.estudanteSolucao = estudanteSolucao;
        this.projetoSolucao = projetoSolucao;
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
    public Estudante getEstudanteSolucao() {
        return estudanteSolucao;
    }
    public void setEstudanteSolucao(Estudante estudanteSolucao) {
        this.estudanteSolucao = estudanteSolucao;
    }
    public Projeto getProjetoSolucao() {
        return projetoSolucao;
    }
    public void setProjetoSolucao(Projeto projetoSolucao) {
        this.projetoSolucao = projetoSolucao;
    }
    @Override
    public String toString() {
        return "Solucao [id=" + id + ", titulo=" + titulo + ", descricao=" + descricao + ", estudanteSolucao="
                + estudanteSolucao + ", projetoSolucao=" + projetoSolucao + "]";
    }
    
    
}
