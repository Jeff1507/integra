package com.integra.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import com.github.hugoperlin.results.Resultado;
import com.integra.model.entities.Estudante;
import com.integra.model.entities.Projeto;
import com.integra.model.entities.Solucao;
import com.integra.utils.DBUtils;

public class JDBCSolucaoDAO implements SolucaoDAO{

    private ConexaoBD conexaoBD; 

    public JDBCSolucaoDAO(ConexaoBD conexaoBD){
        this.conexaoBD = conexaoBD;
    }

    @Override
    public Resultado<Solucao> criar(Solucao solucao, Estudante contaLogada, Projeto projeto) {
        try (Connection con = conexaoBD.getConnection()) {
            PreparedStatement pstm = con.
            prepareStatement("INSERT INTO solucao(nome, descricao, projeto_id, estudante_id) VALUES (?,?,?,?)", 
            Statement.RETURN_GENERATED_KEYS);

            pstm.setString(1, solucao.getTitulo());
            pstm.setString(2, solucao.getDescricao());
            pstm.setInt(3, projeto.getId());
            pstm.setInt(4, contaLogada.getId());

            int ret = pstm.executeUpdate();

            if (ret == 1) {
                int id = DBUtils.getLastId(pstm);

                solucao.setId(id);

                return Resultado.sucesso("Solução criada!", solucao);
            }
            return Resultado.erro("Erro ao criar Solução!");
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
    public Resultado listarSolucaoProjeto(int idProjeto) {
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
