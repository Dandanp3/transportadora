package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import db.ConnectionFactory;
import models.NotaFiscal;

public class DocumentoFiscalDAO {
    public int salvarDocumento(NotaFiscal documentoFiscal) {
        String sql = "INSERT INTO transportadora.documentoFiscal (entrega_id, numero_nf, chave_acesso, valor_mercadoria) VALUES (?, ?, ?, ?)";
        int idGerado = -1;
    
        try (Connection conn = ConnectionFactory.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, documentoFiscal.getEntregaId());
            stmt.setString(2, documentoFiscal.getNumeroNf());
            stmt.setString(3, documentoFiscal.getChaveAcesso());
            stmt.setDouble(4, documentoFiscal.getValorMercadoria());

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    idGerado = generatedKeys.getInt(1);
                }
            }

            
            } catch (SQLException e) {
                System.out.println("Erro ao salvar o documento fiscal.");
                e.printStackTrace();

            }
        return idGerado;
    }
    
}
