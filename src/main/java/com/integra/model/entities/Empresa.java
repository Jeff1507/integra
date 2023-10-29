package com.integra.model.entities;

import java.util.List;

public class Empresa {
    private int id;
    private String nome;
    private String telefone;
    private String email;
    private String senha;
    private boolean logado;
    private List<Projeto> projetos;

    public Empresa(int id, String nome, String telefone, String email, String senha) {
        this.id = id;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.senha = senha;
        this.logado = false;
    }
    public Empresa(String nome, String telefone, String email, String senha) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.senha = senha;
        this.logado = false;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Projeto> getProjetos() {
        return projetos;
    }

    public void setProjetos(List<Projeto> projetos) {
        this.projetos = projetos;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public boolean isLogado() {
        return logado;
    }

    public void setLogado(boolean logado) {
        this.logado = logado;
    }
    @Override
    public String toString() {
        return "Empresa [id=" + id + ", nome=" + nome + ", telefone=" + telefone + ", email=" + email + ", senha="
                + senha + ", logado=" + logado + ", projetos=" + projetos + "]";
    }
    
    
    
}
