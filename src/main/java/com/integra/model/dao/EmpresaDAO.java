package com.integra.model.dao;

import com.github.hugoperlin.results.Resultado;
import com.integra.model.entities.Empresa;

public interface EmpresaDAO {
    Resultado cadastrar(Empresa empresa);

    Resultado atualizar(int id, Empresa nova);

}
