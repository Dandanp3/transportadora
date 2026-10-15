package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import db.ConnectionFactory;
import models.Entrega;

public class EntregaDAO {
    public int salvarEntrega(Entrega entrega) {
    String sql = "INSERT INTO transportadora.entrega (remetente_id, destinatario_id, endereco_origem_id, endereco_destino_id, num_rastreio, status_entrega, valor_frete, data_emissao) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    int idGerado = -1;

    try (Connection conn = ConnectionFactory.conectar();
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
        // mensageiro

        int index = 1;
        stmt.setInt(index++, entrega.getRemetente().getId());
        stmt.setInt(index++, entrega.getDestinatario().getId());
        stmt.setInt(index++, entrega.getEnderecoOrigem().getId());
        stmt.setInt(index++, entrega.getEnderecoDestino().getId());

        // preenche as lacunas com ? de acordo com os numeros
        stmt.setString(index++, entrega.getNumRastreio());
        stmt.setString(index++, entrega.getStatusEntrega());
        stmt.setDouble(index++, entrega.getValorFrete());

        //converte string de data para formato db
        stmt.setDate(index++, java.sql.Date.valueOf((entrega.getDataEmissao())));
        stmt.executeUpdate();

        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                idGerado = generatedKeys.getInt(1);
            }
        }
        System.out.println("Entrega salva.");

    } catch(SQLException e) {
        System.out.println("Erro ao salvar o produto.");
        e.printStackTrace();
    }
        return idGerado;
    }
}
