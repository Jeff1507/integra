package com.integra.model.dao;

import com.github.hugoperlin.results.Resultado;
import com.integra.model.entities.Estudante;

public interface EstudanteDAO {
    Resultado cadastrar(Estudante estudante);

    Resultado atualizar(int id, Estudante novo);
}
