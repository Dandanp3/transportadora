package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.ConnectionFactory;
import models.Produto;

public class ProdutoDAO {
    public int salvarProduto(Produto produto) {
        String sql = "INSERT INTO transportadora.produto (cliente_id, descricao, valor_padrao) VALUES (?, ?, ?)";
        int idGerado = -1;

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setInt(1, produto.getClienteId());
            stmt.setString(2, produto.getDescricao());
            stmt.setDouble(3, produto.getValorPadrao());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    idGerado = generatedKeys.getInt(1);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao salvar produto.");
            e.printStackTrace();
        }
        return idGerado;
    }

    public List<Produto> buscarPorClienteId(int clienteId) {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM transportadora.produto WHERE cliente_id = ?";

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, clienteId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Produto p = new Produto();
                    p.setId(rs.getInt("id"));
                    p.setClienteId(rs.getInt("cliente_id"));
                    p.setDescricao(rs.getString("descricao"));
                    p.setValorPadrao(rs.getDouble("valor_padrao"));
                    produtos.add(p);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar produtos do cliente.");
            e.printStackTrace();
        }
        return produtos;
    }
    
    public Produto buscarPorID(int id) {
        String sql = "SELECT * FROM transportadora.produto WHERE id = ?";
        Produto produto = null;

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    produto = new Produto();
                    produto.setId(rs.getInt("id"));
                    produto.setClienteId(rs.getInt("cliente_id"));
                    produto.setDescricao(rs.getString("descricao"));
                    produto.setValorPadrao(rs.getDouble("valor_padrao"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar produto por ID.");
            e.printStackTrace();
        }
        return produto;
    }
}