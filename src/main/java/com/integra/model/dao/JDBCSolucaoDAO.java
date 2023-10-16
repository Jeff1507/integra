package com.integra.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import com.github.hugoperlin.results.Resultado;
import com.integra.model.entities.Solucao;
import com.integra.utils.DBUtils;

public class JDBCSolucaoDAO implements SolucaoDAO{

    private ConexaoBD conexaoBD; 

    public JDBCSolucaoDAO(ConexaoBD conexaoBD){
        this.conexaoBD = conexaoBD;
    }

    @Override
    public Resultado<Solucao> criar(Solucao solucao) {
        try (Connection con = conexaoBD.getConnection()) {
            
            PreparedStatement pstm = con.prepareStatement("INSERT INTO solucao(titulo, descricao) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);

            pstm.setString(1, solucao.getTitulo());
            pstm.setString(2, solucao.getDescricao());

            int ret = pstm.executeUpdate();

            if (ret == 1) {
                int id = DBUtils.getLastId(pstm);

                solucao.setId(id);

                return Resultado.sucesso("Soluçao criada com sucesso!", solucao);
            }
            return Resultado.erro("Erro ao criar Solução");
        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }

    @Override
    public Resultado getById(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getById'");
    }

    @Override
    public Resultado listarSolucaoProblema(int idProblema) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listarSolucaoProblema'");
    }

    @Override
    public Resultado listarSolucaoEstudante(int idEstudante) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listarSolucaoEstudante'");
    }

    @Override
    public Resultado editar(int id, Solucao nova) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'editar'");
    }

    @Override
    public Resultado excluir(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'excluir'");
    }
    
}
