package com.integra.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.github.hugoperlin.results.Resultado;
import com.integra.model.entities.Projeto;
import com.integra.utils.DBUtils;

public class JDBCProjetoDAO implements ProjetoDAO{

    private ConexaoBD conexao;

    public JDBCProjetoDAO(ConexaoBD conexao){
        this.conexao = conexao;
    }

    @Override
    public Resultado<Projeto> criar(Projeto projeto, int empresaId) {
        try (Connection con = conexao.getConnection()) {

            PreparedStatement pstm = con.
            prepareStatement("INSERT INTO projeto(nome, area_atuacao, descricao) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);

            pstm.setString(1, projeto.getNome());
            pstm.setString(2, projeto.getAreaEmpresa());
            pstm.setString(3, projeto.getDescricao());

            int ret = pstm.executeUpdate();

            if(ret == 1){

                int id = DBUtils.getLastId(pstm);
                projeto.setId(id);

                PreparedStatement pstm2 = con.
                prepareStatement("INSERT INTO empresa_projeto(empresa_id, projeto_id) VALUES (?,?)");

                pstm2.setInt(1, empresaId);
                pstm2.setInt(2, id);

                pstm2.executeUpdate();

                return Resultado.sucesso("Projeto criado com sucesso!", projeto);
            }
            return Resultado.erro("Erro ao criar Projeto!");
        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }

    @Override
    public Resultado<ArrayList<Projeto>> listarProjetosRecentes() {
        try (Connection con = conexao.getConnection()) {
            
            PreparedStatement pstm = con.prepareStatement("SELECT * FROM projeto ORDER BY data_criacao DESC");

            ResultSet resultSet = pstm.executeQuery();
            ArrayList <Projeto> projetosRecentes = new ArrayList<>();

            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String nome = resultSet.getString("nome");
                String areaEmpresa = resultSet.getString("area_atuacao");
                String descricao = resultSet.getString("descricao");

                Projeto projeto = new Projeto(id, nome, descricao, areaEmpresa);
                projetosRecentes.add(projeto);
            }
            return Resultado.sucesso("Projetos listados!", projetosRecentes);
        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }

    @Override
    public Resultado listarPorNome(String Nome) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listarPorNome'");
    }

    @Override
    public Resultado<Projeto> getByid(int id) {
        try (Connection con = conexao.getConnection()) {
            PreparedStatement pstm = con.prepareStatement("SELECT * FROM projeto WHERE id=?");

            pstm.setInt(1, id);

            ResultSet resultSet = pstm.executeQuery();

            while (resultSet.next()) {
                String nome = resultSet.getString("nome");
                String areaEmpresa = resultSet.getString("area_atuacao");
                String descricao = resultSet.getString("descricao");

                Projeto projeto = new Projeto(id, nome, descricao, areaEmpresa);

                return Resultado.sucesso("Projeto encontrado!", projeto);
            }
            return Resultado.erro("Projeto não encontrado!");
        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }

    @Override
    public Resultado<ArrayList<Projeto>> listarProjetoEmpresa(int empresaId) {
        try (Connection con = conexao.getConnection()) {
            PreparedStatement pstm = con.
            prepareStatement("SELECT projeto_id FROM empresa_projeto WHERE empresa_id=?");

            pstm.setInt(1, empresaId);

            ResultSet resultSet = pstm.executeQuery();

            ArrayList<Projeto> projetos = new ArrayList<>();

            while (resultSet.next()) {
                int projetoId = resultSet.getInt("projeto_id");

                Resultado<Projeto> resultado = getByid(projetoId);

                if (resultado.foiSucesso()) {
                    Projeto projeto = (Projeto) resultado.comoSucesso().getObj();
                    projetos.add(projeto);
                }
                else{
                    return resultado.comoErro();
                }
            }
            return Resultado.sucesso("Projetos recuperados", projetos);
        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
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
