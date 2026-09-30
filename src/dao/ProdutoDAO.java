package src.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import src.db.ConexaoBanco;
import src.models.Produto;
//import src.controllers.ProdutoControllers;

public class ProdutoDAO {
    //private List<Produto> produtos = new ArrayList<>();

    public void salvarProduto() {
    // Fase 1: 3 colunas e 3 valores
    String sql = "INSERT INTO transportadora.produto (produto_nome, peso, preco) VALUES (?, ?, ?)";

    Connection conn = ConexaoBanco.conectar();

    try {
        // mensageiro
        PreparedStatement stmt = conn.prepareStatement(sql);

        // preenche as lacunas com ? de acordo com os numeros
        stmt.setString(1, "RTX 5060");
        stmt.setDouble(2, 3);
        stmt.setDouble(3, 2400);

        int linhasAfetadas = stmt.executeUpdate();
        System.out.println("Linhas afetadas: " + linhasAfetadas);
        stmt.close();
        conn.close();

    } catch(SQLException e) {
        System.out.println("Erro ao salvar o produto.");
        e.printStackTrace();
    }
    }
    
}
