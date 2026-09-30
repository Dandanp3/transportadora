package src.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import src.db.ConexaoBanco;
import src.models.Endereco;

public class EnderecoDAO {
    public int salvarEndereco(Endereco endereco) {
        String sql = "INSERT INTO transportadora.endereco (logradouro, numero, complemento, bairro, cidade, uf, cep) VALUES (?, ?, ?, ?, ?, ?, ?)";
        int idGerado = -1;

        try (Connection conn = ConexaoBanco.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                
                stmt.setString(1, endereco.getLogradouro());
            }
        


    }
    
}
