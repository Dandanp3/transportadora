package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;

import db.ConnectionFactory;
import models.Endereco;

public class EnderecoDAO {
    
    public int salvarEndereco(Endereco endereco) {
        String sql = "INSERT INTO transportadora.endereco (cliente_id, cep, logradouro, numero, complemento, bairro, cidade, uf) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        int idGerado = -1;

        try (Connection conn = ConnectionFactory.conectar(); 
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            int index = 0;
            stmt.setInt(++index, endereco.getClienteId());
            stmt.setString(++index, endereco.getCep());
            stmt.setString(++index, endereco.getLogradouro());
            stmt.setInt(++index, endereco.getNumero());
            stmt.setString(++index, endereco.getComplemento());
            stmt.setString(++index, endereco.getBairro());
            stmt.setString(++index, endereco.getCidade());
            stmt.setString(++index, endereco.getUf());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    idGerado = generatedKeys.getInt(1);
                }
            }

        } catch(SQLException e) {
            System.out.println("erro ao salvar endereco.");
            e.printStackTrace();
        }
        return idGerado;
    }

    // metodo para atualizar
    public void atualizar(Endereco endereco) {
        String sql = "UPDATE transportadora.endereco SET cep = ?, logradouro = ?, numero = ?, complemento = ?, bairro = ?, cidade = ?, uf = ? WHERE id = ?";
        
        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            int index = 0;
            stmt.setString(++index, endereco.getCep());
            stmt.setString(++index, endereco.getLogradouro());
            stmt.setInt(++index, endereco.getNumero());
            stmt.setString(++index, endereco.getComplemento());
            stmt.setString(++index, endereco.getBairro());
            stmt.setString(++index, endereco.getCidade());
            stmt.setString(++index, endereco.getUf());
            stmt.setInt(++index, endereco.getId()); 

            stmt.executeUpdate();
            
        } catch(SQLException e) {
            System.out.println("deu merda no update de endereco");
            e.printStackTrace();
        }
    }

    public Endereco buscarPorID(int id) {
        String sql = "SELECT * FROM transportadora.endereco WHERE id = ?";
        Endereco endereco = null;

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    endereco = new Endereco();
                    endereco.setId(rs.getInt("id"));
                    endereco.setClienteId(rs.getInt("cliente_id"));
                    endereco.setCep(rs.getString("cep"));
                    endereco.setLogradouro(rs.getString("logradouro"));
                    endereco.setNumero(rs.getInt("numero")); 
                    endereco.setComplemento(rs.getString("complemento"));
                    endereco.setBairro(rs.getString("bairro"));
                    endereco.setCidade(rs.getString("cidade"));
                    endereco.setUf(rs.getString("uf"));
                }
            }
        } catch (SQLException e) {
            System.out.println("erro ao buscar endereco por ID.");
            e.printStackTrace();
        }
        return endereco;
    }

    public Endereco buscarPorClienteId(int idDoCliente) {
        String sql = "SELECT * FROM transportadora.endereco WHERE cliente_id = ?";
        Endereco endereco = null;

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idDoCliente);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    endereco = new Endereco();
                    endereco.setId(rs.getInt("id"));
                    endereco.setClienteId(rs.getInt("cliente_id"));
                    endereco.setCep(rs.getString("cep"));
                    endereco.setLogradouro(rs.getString("logradouro"));
                    endereco.setNumero(rs.getInt("numero"));
                    endereco.setComplemento(rs.getString("complemento"));
                    endereco.setBairro(rs.getString("bairro"));
                    endereco.setCidade(rs.getString("cidade"));
                    endereco.setUf(rs.getString("uf"));
                }
            }
        } catch(SQLException e) {
            System.out.println("erro ao buscar endereco unico por cliente.");
            e.printStackTrace();
        }
        
        return endereco;
    }

    public List<Endereco> buscarEnderecos() {
        List<Endereco> listaEnderecos = new ArrayList<>();
        String sql = "SELECT * FROM transportadora.endereco";

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Endereco endereco = new Endereco();
                endereco.setId(rs.getInt("id"));
                endereco.setClienteId(rs.getInt("cliente_id"));
                endereco.setCep(rs.getString("cep"));
                endereco.setLogradouro(rs.getString("logradouro"));
                endereco.setNumero(rs.getInt("numero"));
                endereco.setComplemento(rs.getString("complemento"));
                endereco.setBairro(rs.getString("bairro"));
                endereco.setCidade(rs.getString("cidade"));
                endereco.setUf(rs.getString("uf"));

                listaEnderecos.add(endereco);
            }

        } catch(SQLException e) {
            System.out.println("erro ao buscar enderecos.");
            e.printStackTrace();
        }
        
        return listaEnderecos;
    }

    public void deletarEnderecoPorCliente(int id) {
        String sql = "DELETE FROM transportadora.endereco WHERE cliente_id = ?";
        
        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("endereco excluido com sucesso.");
            } else {
                System.out.println("nenhum cliente encontrado com o ID: " + id);
            }

        } catch (SQLException e) {
            System.out.println("erro ao tentar excluir endereco.");
        }
    } 

    // agr sim, esse aq puxa TODOS os enderecos vinculados ao cliente
    public List<Endereco> buscarTodosPorClienteId(int idDoCliente) {
        List<Endereco> listaEnderecos = new ArrayList<>();
        String sql = "SELECT * FROM transportadora.endereco WHERE cliente_id = ?";

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idDoCliente);

            try (ResultSet rs = stmt.executeQuery()) {
                // while pq agora a gnt quer varrer todos os resultados q o banco achar
                while (rs.next()) {
                    Endereco endereco = new Endereco();
                    endereco.setId(rs.getInt("id"));
                    endereco.setClienteId(rs.getInt("cliente_id"));
                    endereco.setCep(rs.getString("cep"));
                    endereco.setLogradouro(rs.getString("logradouro"));
                    endereco.setNumero(rs.getInt("numero"));
                    endereco.setComplemento(rs.getString("complemento"));
                    endereco.setBairro(rs.getString("bairro"));
                    endereco.setCidade(rs.getString("cidade"));
                    endereco.setUf(rs.getString("uf"));

                    listaEnderecos.add(endereco);
                }
            }
        } catch(SQLException e) {
            System.out.println("deu ruim ao buscar a lista de enderecos do cliente.");
            e.printStackTrace();
        }
        
        return listaEnderecos;
    }

    // metodo pra apagar UM endereco so da lista (usado no fluxo 2 do servlet)
    public void deletarPorId(int id) {
        String sql = "DELETE FROM transportadora.endereco WHERE id = ?";
        
        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println("erro ao tentar apagar o endereco especifico.");
            e.printStackTrace();
        }
    }
}