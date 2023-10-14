package com.integra.model.dao;

import java.util.ArrayList;

import com.github.hugoperlin.results.Resultado;
import com.integra.model.entities.Projeto;

public interface ProjetoDAO {
    Resultado<Projeto> criar(Projeto projeto);

    Resultado<ArrayList<Projeto>> listarPorNome(String Nome);
    Resultado<Projeto> getByid(int id);
    Resultado<ArrayList<Projeto>> listarProjetoEmpresa(int idEmpresa);
    Resultado<ArrayList<Projeto>> listarProjetosRecentes();

    Resultado<Projeto> editar(int id, Projeto novo);

    Resultado<Projeto> excluir();
}
