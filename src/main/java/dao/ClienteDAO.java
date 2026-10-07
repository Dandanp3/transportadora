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
                
                stmt.setString(1, cliente.getTipoPessoa());
                stmt.setString(2, cliente.getNome());
                stmt.setString(3, cliente.getNomeFantasia());
                stmt.setString(4, cliente.getDocumento());
                stmt.setString(5, cliente.getEmail());
                stmt.setString(6, cliente.getTelefone());
                

                stmt.executeUpdate();

                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        idGerado = generatedKeys.getInt(1);
                    }
                }
            
            } catch(SQLException e) {
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

            // enquanto houver linhas, o loop vai passar por elas
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getInt("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setDocumento(rs.getString("documento"));
                cliente.setEmail(rs.getString("telefone"));
                cliente.setTelefone(rs.getString("email"));

                listaClientes.add(cliente);
            }
            
        } catch(SQLException e) {
            System.out.println("Erro ao buscar clientes.");
            e.printStackTrace();
        }

        return listaClientes;
    }
}
