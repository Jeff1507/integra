package com.integra.model.repositories;

import com.github.hugoperlin.results.Resultado;
import com.integra.model.dao.EstudanteDAO;
import com.integra.model.entities.Empresa;
import com.integra.model.entities.Estudante;

public class RepositorioEstudante {
    private EstudanteDAO estudanteDAO;

    private Estudante contaLogada;

    public RepositorioEstudante(EstudanteDAO estudanteDAO){
        this.estudanteDAO = estudanteDAO;
    }
    public Resultado<Estudante> cadastrar(String nome, String email, String senha){
        if (nome.isEmpty() || nome.isBlank()) {
            return Resultado.erro("Nome em branco!");
        }
        if (email.isEmpty() || email.isBlank()) {
            return Resultado.erro("E-mail em branco!");
        }
        if (senha.isEmpty() || senha.isBlank()) {
            return Resultado.erro("Senha em branco!");
        }
        if (!estudanteDAO.validarEmail(email).equals("Sucesso")) {
            return Resultado.erro(estudanteDAO.validarEmail(email));
        }
        if (!estudanteDAO.validarConta(nome, email).equals("Sucesso")) {
            return Resultado.erro(estudanteDAO.validarConta(nome, email));
        }

        Estudante estudante = new Estudante(nome, email, senha);
        return estudanteDAO.cadastrar(estudante);
    }
    public void setContaLogada(Estudante estudante){
        this.contaLogada = estudante;
    }
    public Estudante contaLogada(){
        return contaLogada;
    }
    public Resultado<Estudante> login(String nome, String senha){
        if (nome.isEmpty() || nome.isBlank()) {
            return Resultado.erro("Nome em branco!");
        }
        if (senha.isEmpty() || senha.isBlank()) {
            return Resultado.erro("Senha em branco!");
        } 
        Resultado<Estudante> resultado = estudanteDAO.logar(nome, senha);
        if (resultado.foiSucesso()) {
            Estudante estudante = (Estudante) resultado.comoSucesso().getObj();
            setContaLogada(estudante);
        }
        return resultado;

    }
    
}
