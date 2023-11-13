package com.integra.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.github.hugoperlin.results.Resultado;
import com.integra.model.entities.Empresa;
import com.integra.model.entities.Projeto;
import com.integra.utils.DBUtils;

public class JDBCProjetoDAO implements ProjetoDAO{

    private ConexaoBD conexao;

    public JDBCProjetoDAO(ConexaoBD conexao){
        this.conexao = conexao;
    }

    @Override
    public Resultado<Projeto> criar(Projeto projeto, Empresa contaLogada) {
        try (Connection con = conexao.getConnection()) {

            PreparedStatement pstm = con.
            prepareStatement("INSERT INTO projeto(empresa_id, nome, area_atuacao, descricao) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

            pstm.setInt(1, contaLogada.getId());
            pstm.setString(2, projeto.getNome());
            pstm.setString(3, projeto.getAreaEmpresa());
            pstm.setString(4, projeto.getDescricao());

            int ret = pstm.executeUpdate();

            if(ret == 1){

                int id = DBUtils.getLastId(pstm);

                projeto.setId(id);

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

                Projeto projeto = new Projeto(id, nome, descricao, areaEmpresa, null);
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

                Projeto projeto = new Projeto(id, nome, descricao, areaEmpresa, null);

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
            prepareStatement("SELECT id FROM projeto WHERE empresa_id=?");

            pstm.setInt(1, empresaId);

            ResultSet resultSet = pstm.executeQuery();

            ArrayList<Projeto> projetos = new ArrayList<>();

            while (resultSet.next()) {
                int projetoId = resultSet.getInt("id");

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
    public Resultado<Projeto> editar(int id, Projeto novo) {
        try (Connection con = conexao.getConnection()) {
            PreparedStatement pstm = con.prepareStatement("UPDATE projeto SET nome=?, area_atuacao=?, descricao=? WHERE id=?");
            pstm.setString(1, novo.getNome());
            pstm.setString(2, novo.getAreaEmpresa());
            pstm.setString(id, novo.getDescricao());
            pstm.setInt(4, id);

            int ret = pstm.executeUpdate();

            if (ret == 1) {
                return Resultado.sucesso("Projeto atualizado!\nFeche o aplicativo e abra denovo", novo);
            }
            return Resultado.erro("Não foi possível atualizar o projeto!");
        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }

    @Override
    public Resultado excluir() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'excluir'");
    }

}
