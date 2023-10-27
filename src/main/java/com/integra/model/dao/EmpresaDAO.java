package com.integra.model.dao;

import com.github.hugoperlin.results.Resultado;
import com.integra.model.entities.Empresa;
import com.integra.model.entities.Projeto;

public interface EmpresaDAO {
    Resultado<Empresa> cadastrar(Empresa empresa);

    Resultado atualizar(int id, Empresa nova);

}
