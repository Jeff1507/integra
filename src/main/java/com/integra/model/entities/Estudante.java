package com.integra.model.entities;

import java.util.List;

public class Estudante {
    private int id;
    private String nome;
    private String email;
    private String telefone;
    private String senha;
    private boolean logado;
    private List<Solucao> solucoes;
    
    public Estudante(String nome, String email, String telefone, String senha) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.senha = senha;
        this.logado = false;
    }
    public Estudante(int id, String nome, String email, String telefone, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.senha = senha;
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
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getTelefone() {
        return telefone;
    }
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    public String getSenha() {
        return senha;
    }
    public void setSenha(String senha) {
        this.senha = senha;
    }
    public boolean isLogado() {
        return logado;
    }
    public void setLogado(boolean logado) {
        this.logado = logado;
    }
    public List<Solucao> getSolucoes() {
        return solucoes;
    }
    public void setSolucoes(List<Solucao> solucoes) {
        this.solucoes = solucoes;
    }
    
    
}
