package dao;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import db.ConnectionFactory;
import models.ItemEntrega;

public class itemEntrega {
    public int salvarItemEntrega(ItemEntrega itemEntrega) {
        String sql = "INSERT INTO transportadora.itemEntrega (entrega_id, codigo_barras, peso_kg, altura_cm, largura_cm, comprimento_cm) VALUES (?, ?, ?, ?, ?, ?)";
        
        int idGerado = -1;
    
    try (Connection conn = ConnectionFactory.conectar();
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
        
        stmt.setInt(1, itemEntrega.getEntregaId());
        stmt.setString(2, itemEntrega.getCodigoBarras());
        stmt.setDouble(3, itemEntrega.getPesoKg());
        stmt.setDouble(4, itemEntrega.getAlturaCm());
        stmt.setDouble(5, itemEntrega.getLarguraCm());
        stmt.setDouble(6, itemEntrega.getComprimentoCm());

        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                idGerado = generatedKeys.getInt(1);
            }
        }
        System.out.println("Item salvo.");
        } catch(SQLException e) {
        System.out.println("Erro ao salvar Item da entrega.");
    }
    return idGerado;
    }
}