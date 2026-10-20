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
        String sql = "INSERT INTO transportadora.entrega (remetente_id, destinatario_id, endereco_origem_id, endereco_destino_id, num_rastreio, status_entrega, valor_frete, data_emissao) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        int idGerado = -1;

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            int index = 0;
            stmt.setInt(++index, entrega.getRemetente().getId());
            stmt.setInt(++index, entrega.getDestinatario().getId());
            stmt.setInt(++index, entrega.getEnderecoOrigem().getId());
            stmt.setInt(++index, entrega.getEnderecoDestino().getId());
            stmt.setString(++index, entrega.getNumRastreio());
            stmt.setString(++index, entrega.getStatusEntrega());
            stmt.setDouble(++index, entrega.getValorFrete());
            stmt.setDate(++index, java.sql.Date.valueOf((entrega.getDataEmissao())));
            
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
        String sql = "SELECT * FROM transportadora.entrega";
        
        ClienteDAO clienteDAO = new ClienteDAO();
        EnderecoDAO enderecoDAO = new EnderecoDAO();

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Entrega entrega = new Entrega();
                entrega.setId(rs.getInt("id"));
                entrega.setNumRastreio(rs.getString("num_rastreio"));
                entrega.setStatusEntrega(rs.getString("status_entrega"));
                entrega.setValorFrete(rs.getDouble("valor_frete"));
                entrega.setDataEmissao(rs.getDate("data_emissao").toString());

                /* 
                    a partir do id da linha que o rs está usando
                    busco pelo id esse cliente
                    e devolvo em remetente
                    crio o objeto e guardo dentro da lista
                */
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
}