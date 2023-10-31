package com.integra.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.github.hugoperlin.results.Resultado;
import com.integra.model.entities.Empresa;
import com.integra.model.entities.Projeto;
import com.integra.utils.DBUtils;

public class JDBCEmpresaDAO implements EmpresaDAO{

    private ConexaoBD conexaoBD;

    public JDBCEmpresaDAO(ConexaoBD conexaoBD){
        this.conexaoBD = conexaoBD;
    }

    @Override
    public Resultado<Empresa> cadastrar(Empresa empresa) {
        try (Connection con = conexaoBD.getConnection()) {

            PreparedStatement pstm = con.
            prepareStatement("INSERT INTO empresa(nome, email, senha) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS);

            pstm.setString(1, empresa.getNome());
            pstm.setString(2, empresa.getEmail());
            pstm.setString(3, empresa.getSenha());

            int ret = pstm.executeUpdate();

            if (ret == 1) {
                int id = DBUtils.getLastId(pstm);

                empresa.setId(id);

                return Resultado.sucesso("Conta cadastrada com sucesso!", empresa);
            }
            return Resultado.erro("Não foi possível cadastrar a conta!");
        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }

    @Override
    public Resultado atualizar(int id, Empresa nova) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'atualizar'");
    }

    @Override
    public Resultado<Empresa> logar(String nome, String senha) {
        try (Connection con = conexaoBD.getConnection()) {
            
            PreparedStatement pstm = con.prepareStatement("SELECT * FROM empresa WHERE nome=? AND senha=?");

            pstm.setString(1, nome);
            pstm.setString(2, senha);

            ResultSet resultSet = pstm.executeQuery();

            if (resultSet.next()) {
                String nomeEmpresa = resultSet.getString("nome");
                String email = resultSet.getString("email");
                String senhaEmpresa = resultSet.getString("senha");

                Empresa empresa = new Empresa(nomeEmpresa, email, senhaEmpresa);
                contaLogada(empresa);

                return Resultado.sucesso("Bem Vindo De Volta " +nomeEmpresa+ "!", empresa);
            }
            return Resultado.erro("Essa Conta não existe!");

        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }

    @Override
    public Empresa contaLogada(Empresa empresa) {
        empresa.setLogado(true);
        return empresa;
    }

    @Override
    public String validarCadastro(String nome, String email) {
        try (Connection con = conexaoBD.getConnection()) {
            PreparedStatement pstm = con.prepareStatement("SELECT * FROM empresa WHERE nome=?");

            pstm.setString(1, nome);

            ResultSet resultSet = pstm.executeQuery();

            if(resultSet.next()){ 
                return "Esse nome já existe!";
            }

            PreparedStatement pstm2 = con.prepareStatement("SELECT * FROM empresa WHERE email=?");

            pstm2.setString(1, email);

            ResultSet resultSet2 = pstm2.executeQuery();
            if (resultSet2.next()) {
                return "Esse E-mail já existe!";
            }
            return "Sucesso";
        } catch (SQLException e) {
            return e.getMessage();
        }
    }
    
}
