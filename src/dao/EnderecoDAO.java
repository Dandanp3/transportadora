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
            // guardando o id chave primaria
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
                stmt.setString(1, endereco.getLogradouro());
                stmt.setInt(2, endereco.getNumero());
                stmt.setString(3, endereco.getComplemento());
                stmt.setString(4, endereco.getBairro());
                stmt.setString(5, endereco.getCidade());
                stmt.setString(6, endereco.getUF());
                stmt.setString(7, endereco.getCEP());

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
