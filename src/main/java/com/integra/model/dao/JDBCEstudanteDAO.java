package com.integra.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.github.hugoperlin.results.Resultado;
import com.integra.model.entities.Empresa;
import com.integra.model.entities.Estudante;
import com.integra.utils.DBUtils;

public class JDBCEstudanteDAO implements EstudanteDAO{

    private ConexaoBD conexaoBD;

    public JDBCEstudanteDAO(ConexaoBD conexaoBD){
        this.conexaoBD = conexaoBD;
    }

    @Override
    public Resultado<Estudante> cadastrar(Estudante estudante) {
        try (Connection con = conexaoBD.getConnection()) {
            PreparedStatement pstm = con.
            prepareStatement("INSERT INTO estudante(nome, email, senha) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);

            pstm.setString(1, estudante.getNome());
            pstm.setString(2, estudante.getEmail());
            pstm.setString(3, estudante.getSenha());

            int ret = pstm.executeUpdate();

            if (ret == 1) {
                int id = DBUtils.getLastId(pstm);

                estudante.setId(id);

                return Resultado.sucesso("Conta cadastrada com sucesso!", estudante);
            }
            return Resultado.erro("Não foi possível cadastrar a conta!");
        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }

    @Override
    public Resultado<Estudante> atualizar(int id, Estudante novo) {
        try (Connection con = conexaoBD.getConnection()) {
            
            PreparedStatement pstm = con.prepareStatement("UPDATE estudante SET nome=?, email=?, senha=? WHERE id=?");

            pstm.setString(1, novo.getNome());
            pstm.setString(2, novo.getEmail());
            pstm.setString(3, novo.getSenha());
            pstm.setInt(4, id);

            int ret = pstm.executeUpdate();

            if (ret == 1) {
                return Resultado.sucesso("Perfil atualizado!\nFeche o aplicativo e abra denovo", novo);
            }
            return Resultado.erro("Não foi possível atualizar a conta!");
        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }

    @Override
    public String validarConta(String nome, String email) {
        try (Connection con = conexaoBD.getConnection()) {
            PreparedStatement pstm = con.prepareStatement("SELECT validar_cadastro(?,?)");
            pstm.setString(1, nome);
            pstm.setString(2, email);

            ResultSet resultSet = pstm.executeQuery();

            if (resultSet.next() && resultSet.getInt(1) != 1) {
                return "Essa conta já cadastrada!";
            }
            return "Sucesso";
        } catch (SQLException e) {
            return e.getMessage();
        }
    }

    @Override
    public String validarEmail(String email) {
        try (Connection con = conexaoBD.getConnection()) {
            PreparedStatement pstm = con.prepareStatement("SELECT validar_email(?)");
            pstm.setString(1, email);

            ResultSet resultSet = pstm.executeQuery();

            if (resultSet.next() && resultSet.getInt(1) != 1) {
                return "Formato de E-mail invalido";
            }
            return "Sucesso";
        } catch (SQLException e) {
            return e.getMessage();
        }
    }

    @Override
    public Resultado<Estudante> logar(String nome, String senha) {
        try (Connection con = conexaoBD.getConnection()) {
            
            PreparedStatement pstm = con.prepareStatement("SELECT * FROM estudante WHERE nome=? AND senha=?");

            pstm.setString(1, nome);
            pstm.setString(2, senha);

            ResultSet resultSet = pstm.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nomeEstudante = resultSet.getString("nome");
                String email = resultSet.getString("email");
                String senhaEstudante = resultSet.getString("senha");

                Estudante estudante = new Estudante(id, nomeEstudante, email, senhaEstudante);

                return Resultado.sucesso("Bem Vindo De Volta " +nomeEstudante+ "!", estudante);
            }
            return Resultado.erro("Credenciais inválidas!");

        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }

    @Override
    public String validarAtualizar(String nome, String email, int id) {
        try (Connection con = conexaoBD.getConnection()) {
            PreparedStatement pstm = con.
            prepareStatement("SELECT nome, email FROM estudante WHERE (nome=? OR email=?) AND id <> ?");

            pstm.setString(1, nome);
            pstm.setString(2, email);
            pstm.setInt(3, id);

            ResultSet resultSet = pstm.executeQuery();

            if (resultSet.next()) {
                String nomeExistente = resultSet.getString("nome");
                String emailExistente = resultSet.getString("email");

                if (nomeExistente.equals(nome) && emailExistente.equals(email)) {
                    return "Esse Nome e E-mail já existem!";
                }
                if (nomeExistente.equals(nome)) {
                    return "Esse nome já existe!";
                }
                if (emailExistente.equals(email)) {
                    return "Esse E-mail já existe!";
                }
                
            }
            return "Sucesso";
        } catch (SQLException e) {
            return e.getMessage();
        }
    }

    @Override
    public Resultado<Estudante> getById(int id) {
        try (Connection con = conexaoBD.getConnection()) {
            PreparedStatement pstm = con.prepareStatement("SELECT * FROM estudante WHERE id=?");

            pstm.setInt(1, id);
            ResultSet resultSet = pstm.executeQuery();

            if (resultSet.next()) {
                String nome = resultSet.getString("nome");
                String email = resultSet.getString("email");
                String senha = resultSet.getString("senha");

                Estudante estudante = new Estudante(nome, email, senha);
                return Resultado.sucesso("Estudante encontrado", estudante);
            }
            return Resultado.erro("Estudante não encontrado!");
        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }

    @Override
    public Resultado<Estudante> estudanteSolucao(int solucaoId) {
        try (Connection con = conexaoBD.getConnection()) {
            PreparedStatement pstm = con.prepareStatement("SELECT estudante_id FROM solucao WHERE id=?");
            pstm.setInt(1, solucaoId);

            ResultSet resultSet = pstm.executeQuery();
            resultSet.next();

            int estudanteId = resultSet.getInt("estudante_id");

            return getById(estudanteId);

        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }
    
    
}
