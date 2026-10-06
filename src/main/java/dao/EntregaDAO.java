package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.ConnectionFactory;
import models.Entrega;

public class EntregaDAO {
    public int salvarEntrega(Entrega entrega) {
    String sql = "INSERT INTO transportadora.entrega (remetente_id, destinatario_id, endereco_origem_id, endereco_destino_id, num_rastreio, status_entrega, valor_frete, data_emissao) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    Connection conn = ConnectionFactory.conectar();
    int idGerado = -1;

    try {
        // mensageiro
        PreparedStatement stmt = conn.prepareStatement(sql);

        // preenche as lacunas com ? de acordo com os numeros
        stmt.setString(5, entrega.getNumRastreio());
        stmt.setString(6, entrega.getStatusEntrega());
        stmt.setDouble(7, entrega.getValorFrete());

        int linhasAfetadas = stmt.executeUpdate();
        System.out.println("Linhas afetadas: " + linhasAfetadas);
        stmt.close();
        conn.close();

    } catch(SQLException e) {
        System.out.println("Erro ao salvar o produto.");
        e.printStackTrace();
    }
    return idGerado;
    }
    
}
