package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
            System.out.println("Erro ao salvar item da entrega");
            e.printStackTrace();
        }
        return idGerado;
    }

    public int salvarItemEntrega(ItemEntrega itemEntrega) {
        return salvar(itemEntrega);
    }


    // pega todos os itens relacionados a uma entrega 
    public List<ItemEntrega> buscarPorEntregaId(int entregaId) {
        List<ItemEntrega> listaItens = new ArrayList<>();
        String sql = "SELECT * FROM transportadora.item_entrega WHERE entrega_id = ?";

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, entregaId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ItemEntrega item = new ItemEntrega();
                    item.setId(rs.getInt("id"));
                    item.setEntregaId(rs.getInt("entrega_id"));
                    item.setProdutoId(rs.getInt("produto_id"));
                    item.setQuantidade(rs.getInt("quantidade"));
                    item.setValorUnitario(rs.getDouble("valor_unitario"));
                    
                    listaItens.add(item);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar os itens da entrega ID: " + entregaId);
            e.printStackTrace();
        }
        return listaItens;
    }

    // Busca um item especifico pelo seu ID 
    public ItemEntrega buscarPorId(int id) {
        String sql = "SELECT * FROM transportadora.item_entrega WHERE id = ?";
        ItemEntrega item = null;

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    item = new ItemEntrega();
                    item.setId(rs.getInt("id"));
                    item.setEntregaId(rs.getInt("entrega_id"));
                    item.setProdutoId(rs.getInt("produto_id"));
                    item.setQuantidade(rs.getInt("quantidade"));
                    item.setValorUnitario(rs.getDouble("valor_unitario"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar o item ID: " + id);
            e.printStackTrace();
        }
        return item;
    }
}