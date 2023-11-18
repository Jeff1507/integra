package com.integra.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
    public Resultado<Solucao> getById(int id) {
        try (Connection con = conexaoBD.getConnection()) {
            PreparedStatement pstm = con.prepareStatement("SELECT * FROM solucao WHERE id=?");

            pstm.setInt(1, id);

            ResultSet resultSet = pstm.executeQuery();

            while (resultSet.next()) {
                String nome = resultSet.getString("nome");
                String descricao = resultSet.getString("descricao");

                Solucao solucao = new Solucao(id, nome, descricao, null, null);

                return Resultado.sucesso("Solução encontrada", solucao);
            }
            return Resultado.erro("Solução não encontrada");
        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }

    @Override
    public Resultado<ArrayList<Solucao>> listarSolucaoProjeto(int idProjeto) {
        try (Connection con = conexaoBD.getConnection()) {
            PreparedStatement pstm = con.
            prepareStatement("SELECT id FROM solucao WHERE projeto_id=?");

            pstm.setInt(1, idProjeto);

            ResultSet resultSet = pstm.executeQuery();

            ArrayList<Solucao> solucoes = new ArrayList<>();

            while(resultSet.next()){
                int solucaoId = resultSet.getInt("id");

                Resultado<Solucao> resultado = getById(solucaoId);

                if (resultado.foiSucesso()) {
                    Solucao solucao = (Solucao) resultado.comoSucesso().getObj();
                    solucoes.add(solucao);
                }
                else{
                    return resultado.comoErro();
                }
            }
            return Resultado.sucesso("Soluções recuperadas", solucoes);
        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }

    @Override
    public Resultado<ArrayList<Solucao>> listarSolucaoEstudante(int idEstudante) {
        try (Connection con = conexaoBD.getConnection()) {
            PreparedStatement pstm = con.
            prepareStatement("SELECT id FROM solucao WHERE estudante_id=?");

            pstm.setInt(1, idEstudante);

            ResultSet resultSet = pstm.executeQuery();

            ArrayList<Solucao> solucoes = new ArrayList<>();

            while(resultSet.next()){
                int solucaoId = resultSet.getInt("id");

                Resultado<Solucao> resultado = getById(solucaoId);

                if (resultado.foiSucesso()) {
                    Solucao solucao = (Solucao) resultado.comoSucesso().getObj();
                    solucoes.add(solucao);
                }
                else{
                    return resultado.comoErro();
                }
            }
            return Resultado.sucesso("Soluções recuperadas", solucoes);
        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }

    @Override
    public Resultado<Solucao> editar(int id, Solucao nova) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'editar'");
    }

    @Override
    public Resultado<Solucao> excluir(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'excluir'");
    }
    
}
