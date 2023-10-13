package com.integra.model.dao;

import com.github.hugoperlin.results.Resultado;
import com.integra.model.entities.Projeto;

public interface ProjetoDAO {
    Resultado criar(Projeto projeto);

    Resultado listarPorNome(String Nome);
    Resultado getByid(int id);
    Resultado listarProjetoEmpresa(int idEmpresa);

    Resultado editar(int id, Projeto novo);

    Resultado excluir();
}
