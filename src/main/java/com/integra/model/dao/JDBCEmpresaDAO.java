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
    public String validarEmail(String email){
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
    public String validarConta(String nome, String email){
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
    public Resultado<Empresa> atualizar(int id, Empresa nova) {
        try (Connection con = conexaoBD.getConnection()) {
            
            PreparedStatement pstm = con.prepareStatement("UPDATE empresa SET nome=?, email=?, senha=? WHERE id=?");

            pstm.setString(1, nova.getNome());
            pstm.setString(2, nova.getEmail());
            pstm.setString(3, nova.getSenha());
            pstm.setInt(4, id);

            int ret = pstm.executeUpdate();

            if (ret == 1) {
                return Resultado.sucesso("Perfil atualizado!\nFeche o aplicativo e abra denovo", nova);
            }
            return Resultado.erro("Não foi possível atualizar a conta!");
        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }
    @Override
    public String validarAtualizar(String nome, String email, int id){
        try (Connection con = conexaoBD.getConnection()) {
            PreparedStatement pstm = con.
            prepareStatement("SELECT nome, email FROM empresa WHERE (nome=? OR email=?) AND id <> ?");

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
    public Resultado<Empresa> logar(String nome, String senha) {
        try (Connection con = conexaoBD.getConnection()) {
            
            PreparedStatement pstm = con.prepareStatement("SELECT * FROM empresa WHERE nome=? AND senha=?");

            pstm.setString(1, nome);
            pstm.setString(2, senha);

            ResultSet resultSet = pstm.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nomeEmpresa = resultSet.getString("nome");
                String email = resultSet.getString("email");
                String senhaEmpresa = resultSet.getString("senha");

                Empresa empresa = new Empresa(id, nomeEmpresa, email, senhaEmpresa);

                return Resultado.sucesso("Bem Vindo De Volta " +nomeEmpresa+ "!", empresa);
            }
            return Resultado.erro("Credenciais inválidas!");

        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }

    @Override
    public String validarCadastro(String nome, String email) {
        try (Connection con = conexaoBD.getConnection()) {
            PreparedStatement pstm = con.prepareStatement("SELECT nome, email FROM empresa WHERE nome=? OR email=?");

            pstm.setString(1, nome);
            pstm.setString(2, email);

            ResultSet resultSet = pstm.executeQuery();

            if (resultSet.next()) {
                String nomeExistente = resultSet.getString("nome");
                String emailExistente = resultSet.getString("email");

                if (nomeExistente.equals(nome) && emailExistente.equals(email)) {
                    return "Nome e E-mail já está cadastrados!";
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
    public Resultado<Empresa> getById(int id) {
        try (Connection con = conexaoBD.getConnection()) {
            PreparedStatement pstm = con.prepareStatement("SELECT * FROM empresa WHERE id=?");

            pstm.setInt(1, id);
            ResultSet resultSet = pstm.executeQuery();

            if (resultSet.next()) {
                String nome = resultSet.getString("nome");
                String email = resultSet.getString("email");
                String senha = resultSet.getString("email");

                Empresa empresa = new Empresa(nome, email, senha);
                return Resultado.sucesso("Empresa encontrada", empresa);
            }
            return Resultado.erro("Empresa não encontrada!");
        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }

    @Override
    public Resultado<Empresa> empresaProjeto(int projetoId) {
        try (Connection con = conexaoBD.getConnection()) {
            PreparedStatement pstm = con.prepareStatement("SELECT empresa_Id FROM projeto WHERE id=?");
            pstm.setInt(1, projetoId);

            ResultSet resultSet = pstm.executeQuery();
            resultSet.next();

            int empresaId = resultSet.getInt("empresa_id");

            return getById(empresaId);

        } catch (SQLException e) {
            return Resultado.erro(e.getMessage());
        }
    }
    
}
