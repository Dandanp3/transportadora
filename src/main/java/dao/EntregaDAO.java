package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;

import db.ConnectionFactory;
import models.Entrega;

public class EntregaDAO {
    
    public int salvarEntrega(Entrega entrega) {
        // Atualizado com numero_nota_fiscal
        String sql = "INSERT INTO transportadora.entrega (remetente_id, destinatario_id, endereco_origem_id, endereco_destino_id, numero_nota_fiscal, status_entrega, valor_frete, data_emissao) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        int idGerado = -1;

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            int index = 0;
            stmt.setInt(++index, entrega.getRemetente().getId());
            stmt.setInt(++index, entrega.getDestinatario().getId());
            stmt.setInt(++index, entrega.getEnderecoOrigem().getId());
            stmt.setInt(++index, entrega.getEnderecoDestino().getId());
            
            stmt.setString(++index, entrega.getNumeroNotaFiscal()); 
            
            stmt.setString(++index, entrega.getStatusEntrega());
            stmt.setDouble(++index, entrega.getValorFrete());
            stmt.setDate(++index, java.sql.Date.valueOf(entrega.getDataEmissao()));
            
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    idGerado = generatedKeys.getInt(1);
                }
            }
            System.out.println("Entrega salva.");

        } catch(SQLException e) {
            System.out.println("Erro ao salvar entrega.");
            e.printStackTrace();
        }
        return idGerado;
    }

    public List<Entrega> buscarEntregas() {
        List<Entrega> listaEntregas = new ArrayList<>();
        // Traz as entregas mais recentes primeiro
        String sql = "SELECT * FROM transportadora.entrega ORDER BY id DESC";
        
        ClienteDAO clienteDAO = new ClienteDAO();
        EnderecoDAO enderecoDAO = new EnderecoDAO();

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Entrega entrega = new Entrega();
                entrega.setId(rs.getInt("id"));
                // Puxando a nfe
                entrega.setNumeroNotaFiscal(rs.getString("numero_nota_fiscal")); 
                entrega.setStatusEntrega(rs.getString("status_entrega"));
                entrega.setValorFrete(rs.getDouble("valor_frete"));
                entrega.setDataEmissao(rs.getDate("data_emissao").toString());

                entrega.setRemetente(clienteDAO.buscarPorID(rs.getInt("remetente_id")));
                entrega.setDestinatario(clienteDAO.buscarPorID(rs.getInt("destinatario_id")));
                entrega.setEnderecoOrigem(enderecoDAO.buscarPorID(rs.getInt("endereco_origem_id")));
                entrega.setEnderecoDestino(enderecoDAO.buscarPorID(rs.getInt("endereco_destino_id")));

                listaEntregas.add(entrega);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar entregas.");
            e.printStackTrace();
        }

        return listaEntregas;
    }

    public void deletarEntrega(int id) {
        String sql = "DELETE FROM transportadora.entrega WHERE id = ?";
        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int linhasAfetas = stmt.executeUpdate();
            if (linhasAfetas > 0) {
                System.out.println("Entrega excluída.");
            } else {
                System.out.println("Nenhuma entrega encontrada para excluir.");
            }

        } catch (SQLException e) {
            System.out.println("Erro ao excluir a entrega.");
            e.printStackTrace();
        }
    }

    public void atualizarStatus(int id, String novoStatus) {
        String sql = "UPDATE transportadora.entrega SET status_entrega = ? WHERE id = ?";
        try (Connection conn = db.ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, novoStatus);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            System.out.println("Status da entrega " + id + " atualizado para " + novoStatus);

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar o status da entrega.");
            e.printStackTrace();
        }
    }

    public String buscarStatusPorId(int id) {
        String sql = "SELECT status_entrega FROM transportadora.entrega WHERE id = ?";
        try (Connection conn = db.ConnectionFactory.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setInt(1, id);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return rs.getString("status_entrega");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        return null;
    }
}