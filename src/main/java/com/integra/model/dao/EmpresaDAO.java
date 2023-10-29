package com.integra.model.dao;

import com.github.hugoperlin.results.Resultado;
import com.integra.model.entities.Empresa;
import com.integra.model.entities.Projeto;

public interface EmpresaDAO {
    Resultado<Empresa> cadastrar(Empresa empresa);

    Resultado<Empresa> logar(String nome, String senha);

    Resultado<Empresa> encontrarConta(Empresa empresa);

    Resultado atualizar(int id, Empresa nova);

}
