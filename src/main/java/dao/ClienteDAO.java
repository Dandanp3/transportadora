package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.ConnectionFactory;
import models.Cliente;

public class ClienteDAO {
    
    public int salvarCliente(Cliente cliente) {
        String sql = "INSERT INTO transportadora.cliente (tipo_pessoa, nome, nome_fantasia, documento, email, telefone) VALUES (?, ?, ?, ?, ?, ?)";
        int idGerado = -1;

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                
            int index = 0;
            stmt.setString(++index, cliente.getTipoPessoa());
            stmt.setString(++index, cliente.getNome());
            stmt.setString(++index, cliente.getNomeFantasia());
            stmt.setString(++index, cliente.getDocumento());
            stmt.setString(++index, cliente.getEmail());
            stmt.setString(++index, cliente.getTelefone());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    idGerado = generatedKeys.getInt(1);
                }
            }
            
        } catch (SQLException e) {
            System.out.println("Erro ao salvar cliente.");
            e.printStackTrace();
        }
        
        return idGerado;
    }

    public List<Cliente> buscarClientes() {
        List<Cliente> listaClientes = new ArrayList<>();
        String sql = "SELECT * FROM transportadora.cliente";

        try (Connection conn = ConnectionFactory.conectar(); 
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setTipoPessoa(rs.getString("tipo_pessoa"));
                cliente.setDocumento(rs.getString("documento"));
                cliente.setEmail(rs.getString("email"));
                cliente.setTelefone(rs.getString("telefone"));

                listaClientes.add(cliente);
            }
            
        } catch (SQLException e) {
            System.out.println("Erro ao buscar clientes.");
            e.printStackTrace();
        }

        System.out.println("Clientes puxados");
        return listaClientes;
    }

    public Cliente buscarPorID(int id) {
        String sql = "SELECT * FROM clientes WHERE id = ?";
        Cliente cliente = null;


        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                // Se achou o cliente, monta o objeto
                if (rs.next()) {
                    cliente = new Cliente();
                    cliente.setId(rs.getInt("id"));
                    cliente.setNome(rs.getString("nome"));
                    cliente.setNomeFantasia(rs.getString("nome_fantasia"));
                    cliente.setDocumento(rs.getString("documento"));
                    cliente.setEmail(rs.getString("email"));
                    cliente.setTelefone(rs.getString("telefone"));
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar cliente por ID: " + e.getMessage());
        }

 
        return cliente;


    }
}