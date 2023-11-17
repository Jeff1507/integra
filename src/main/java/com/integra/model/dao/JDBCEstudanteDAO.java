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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'atualizar'");
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
    
}
