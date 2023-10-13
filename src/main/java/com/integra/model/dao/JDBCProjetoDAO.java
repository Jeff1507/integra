package com.integra.model.dao;

import com.github.hugoperlin.results.Resultado;
import com.integra.model.entities.Projeto;

public class JDBCProjetoDAO implements ProjetoDAO{

    @Override
    public Resultado criar(Projeto projeto) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'criar'");
    }

    @Override
    public Resultado listarPorNome(String Nome) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listarPorNome'");
    }

    @Override
    public Resultado getByid(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getByid'");
    }

    @Override
    public Resultado listarProjetoEmpresa(int idEmpresa) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listarProjetoEmpresa'");
    }

    @Override
    public Resultado editar(int id, Projeto novo) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'editar'");
    }

    @Override
    public Resultado excluir() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'excluir'");
    }
    
}
