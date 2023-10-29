package com.integra.model.repositories;

import com.github.hugoperlin.results.Resultado;
import com.integra.model.dao.EmpresaDAO;
import com.integra.model.entities.Empresa;

public class RepositorioEmpresa {
    private EmpresaDAO empresaDAO;

    public RepositorioEmpresa(EmpresaDAO empresaDAO){
        this.empresaDAO = empresaDAO;
    }

    public Resultado<Empresa> login(String nome, String senha){
        if (nome.isEmpty() || nome.isBlank()) {
            return Resultado.erro("Nome em branco!");
        }
        if (senha.isEmpty() || senha.isBlank()) {
            return Resultado.erro("Senha em branco!");
        }
        return empresaDAO.logar(nome, senha);

    }
}
