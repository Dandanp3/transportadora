package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;

import db.ConnectionFactory;
import models.DocumentoFiscal;

public class DocumentoFiscalDAO {
    public int salvarDocumento(DocumentoFiscal documento) {
        String sql = "INSERT INTO transportadora.documento_fiscal (entrega_id, tipo_documento, numero_documento, chave_acesso, valor_total_declarado) VALUES (?, ?, ?, ?, ?)";
        int idGerado = -1;
    
        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            int index = 0;
            stmt.setInt(++index, documento.getEntregaId());
            stmt.setString(++index, documento.getTipoDocumento());
            stmt.setString(++index, documento.getNumeroDocumento());
            stmt.setString(++index, documento.getChaveAcesso());
            stmt.setDouble(++index, documento.getValorTotalDeclarado());

            stmt.executeUpdate();

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