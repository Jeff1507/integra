package com.integra.model.dao;

import java.util.ArrayList;

import com.github.hugoperlin.results.Resultado;
import com.integra.model.entities.Empresa;
import com.integra.model.entities.Projeto;

public interface ProjetoDAO {
    Resultado<Projeto> criar(Projeto projeto, Empresa contaLogada);

    Resultado<ArrayList<Projeto>> listarPorNome(String nome);
    Resultado<Projeto> getByid(int id);
    Resultado<ArrayList<Projeto>> listarProjetoEmpresa(int empresaId);
    Resultado<ArrayList<Projeto>> listarProjetosRecentes();

    Resultado<Projeto> editar(int id, Projeto novo);

    Resultado<Projeto> excluir(int id);
}
