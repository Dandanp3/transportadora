package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import models.ItemEntrega;
import db.ConnectionFactory;

public class ItemEntregaDAO {

    // salva o item associado a entrega
    public int salvar(ItemEntrega itemEntrega) {
        String sql = "INSERT INTO transportadora.item_entrega (entrega_id, produto_id, quantidade, valor_unitario) VALUES (?, ?, ?, ?)";
        int idGerado = -1;

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, itemEntrega.getEntregaId());
            stmt.setInt(2, itemEntrega.getProdutoId());
            stmt.setInt(3, itemEntrega.getQuantidade());
            stmt.setDouble(4, itemEntrega.getValorUnitario());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    idGerado = generatedKeys.getInt(1);
                }
            }
            System.out.println("item da entrega salvo com sucesso");

        } catch (SQLException e) {
            System.out.println("deu merda ao salvar item da entrega");
            e.printStackTrace();
        }
        return idGerado;
    }

    // apelido pro metodo antigo n quebrar chamadas antigas
    public int salvarItemEntrega(ItemEntrega itemEntrega) {
        return salvar(itemEntrega);
    }
}