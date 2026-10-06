package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.ConnectionFactory;
import models.Cliente;

public class ClienteDAO {
    public int salvarCliente(Cliente cliente) {
        String sql = "INSERT INTO transportadora.cliente (tipo_pessoa, nome, nome_fantasia, documento, email, telefone, created_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
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
    
}
