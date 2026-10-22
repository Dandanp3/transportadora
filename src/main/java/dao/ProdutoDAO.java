package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Produto;
import db.ConnectionFactory;

public class ProdutoDAO {

    // 1. CREATE - Salvar um produto novo no catálogo
    public boolean salvar(Produto produto) {
        String sql = "INSERT INTO transportadora.produto (descricao, valor_padrao, peso_padrao) VALUES (?, ?, ?)";
        
        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, produto.getDescricao());
            stmt.setDouble(2, produto.getValorPadrao());
            stmt.setDouble(3, produto.getPesoPadrao());

            stmt.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            System.out.println("Erro ao salvar produto.");
            e.printStackTrace();
            return false;
        }
    }

    // 2. READ - Listar todos pra mostrar naquela tela produtoPage.jsp
    public List<Produto> buscarTodos() {
        List<Produto> produtos = new ArrayList<>();
        // Trazendo os mais recentes primeiro
        String sql = "SELECT * FROM transportadora.produto ORDER BY id DESC";

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Produto p = new Produto();
                p.setId(rs.getInt("id"));
                p.setDescricao(rs.getString("descricao"));
                p.setValorPadrao(rs.getDouble("valor_padrao"));
                p.setPesoPadrao(rs.getDouble("peso_padrao")); // O campo novo
                produtos.add(p);
            }
            
        } catch (SQLException e) {
            System.out.println("Erro ao listar");
            e.printStackTrace();
        }
        return produtos;
    }

    // 3. READ - Buscar 1 só (usado na hora de clicar em Editar)
    public Produto buscarPorID(int id) {
        String sql = "SELECT * FROM transportadora.produto WHERE id = ?";
        Produto p = null;

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    p = new Produto();
                    p.setId(rs.getInt("id"));
                    p.setDescricao(rs.getString("descricao"));
                    p.setValorPadrao(rs.getDouble("valor_padrao"));
                    p.setPesoPadrao(rs.getDouble("peso_padrao"));
                }
            }
            
        } catch (SQLException e) {
            System.out.println("Erro ao buscar o produto por ID");
            e.printStackTrace();
        }
        return p;
    }

    // 4. UPDATE - Salvar as edições
    public boolean atualizar(Produto produto) {
        String sql = "UPDATE transportadora.produto SET descricao = ?, valor_padrao = ?, peso_padrao = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, produto.getDescricao());
            stmt.setDouble(2, produto.getValorPadrao());
            stmt.setDouble(3, produto.getPesoPadrao());
            stmt.setInt(4, produto.getId());

            stmt.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar o produto");
            e.printStackTrace();
            return false;
        }
    }

    // 5. DELETE - Apagar o produto do catálogo
    public boolean deletar(int id) {
        String sql = "DELETE FROM transportadora.produto WHERE id = ?";

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
            
        } catch (SQLException e) {
            System.out.println("Erro ao excluir o produto");
            e.printStackTrace();
            return false;
        }
    }
}