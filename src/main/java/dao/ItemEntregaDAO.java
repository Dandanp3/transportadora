package dao;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

import db.ConnectionFactory;
import models.ItemEntrega;

public class ItemEntregaDAO {
    public int salvarItemEntrega(ItemEntrega itemEntrega) {
        String sql = "INSERT INTO transportadora.itemEntrega (entrega_id, codigo_barras, peso_kg, altura_cm, largura_cm, comprimento_cm) VALUES (?, ?, ?, ?, ?, ?)";
        
        int idGerado = -1;
    
    try (Connection conn = ConnectionFactory.conectar();
        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
        
        int index = 0;
        stmt.setInt(++index, itemEntrega.getEntregaId());
        stmt.setString(++index, itemEntrega.getCodigoBarras());
        stmt.setDouble(++index, itemEntrega.getPesoKg());
        stmt.setDouble(++index, itemEntrega.getAlturaCm());
        stmt.setDouble(++index, itemEntrega.getLarguraCm());
        stmt.setDouble(++index, itemEntrega.getComprimentoCm());

        try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                idGerado = generatedKeys.getInt(1);
            }
        }
        System.out.println("Item salvo.");
        } catch(SQLException e) {
        System.out.println("Erro ao salvar Item da entrega.");
        e.printStackTrace();
    }
    return idGerado;
    }
}