package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.ConnectionFactory;
import models.ItemDocumento;

public class ItemDocumentoDAO {
    public int salvarItemDocumento(ItemDocumento item) {
        String sql = "INSERT INTO transportadora.item_documento (documento_fiscal_id, produto_id, quantidade, valor_unitario) VALUES (?, ?, ?, ?)";
        int idGerado = -1;

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, item.getDocumentoFiscalId());
            stmt.setInt(2, item.getProduto().getId()); 
            stmt.setInt(3, item.getQuantidade());
            stmt.setDouble(4, item.getValorUnitario());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    idGerado = generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao salvar item do documento.");
            e.printStackTrace();
        }
        return idGerado;
    }
}