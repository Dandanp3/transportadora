package src.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import src.db.ConexaoBanco;
import src.models.Cliente;

public class ClienteDAO {
    public int salvarCliente(Cliente cliente) {
        String sql = "INSERT INTO transportadora.cliente (nome, cpf, telefone) VALUES (?, ?, ?)";
        int idGerado = -1;

        try (Connection conn = ConexaoBanco.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                
                stmt.setString(1, cliente.getNome());
                stmt.setString(2, cliente.getCPF());
                stmt.setString(3, cliente.getNome());

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
