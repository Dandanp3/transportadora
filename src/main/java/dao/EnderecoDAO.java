package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.ConnectionFactory;
import models.Endereco;

public class EnderecoDAO {
    public int salvarEndereco(Endereco endereco) {
        String sql = "INSERT INTO transportadora.endereco (cliente_id, tipo_endereco, cep, logradouro, numero, complemento, bairro, cidade, uf) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        int idGerado = -1;


        try (Connection conn = ConnectionFactory.conectar(); 
            // guardando o id chave primaria
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
                stmt.setInt(1, endereco.getClienteId());
                stmt.setString(2, endereco.getTipoEndereco());
                stmt.setString(3, endereco.getCep());
                stmt.setString(4, endereco.getLogradouro());
                stmt.setInt(5, endereco.getNumero());
                stmt.setString(6, endereco.getComplemento());
                stmt.setString(7, endereco.getBairro());
                stmt.setString(8, endereco.getCidade());
                stmt.setString(9, endereco.getUf());

                stmt.executeUpdate();

                // guardando id em um resultset (uma caixa de resultados)
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    // se tiver alguma linha, pega o valor da primeira coluna
                    if (generatedKeys.next()) {
                        idGerado = generatedKeys.getInt(1);
                    }
                }

            } catch(SQLException e) {
                System.out.println("Erro ao salvar Endereço.");
                e.printStackTrace();
            }
            return idGerado;
    }
    
}
