package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.ConnectionFactory;
import models.Cliente;

public class ClienteDAO {

    // código do PostgreSQL para "esse registro ainda é usado por outra tabela" (chave estrangeira)
    private static final String VIOLACAO_FK = "23503";

    public int salvarCliente(Cliente cliente) {
        String sql = "INSERT INTO transportadora.cliente (tipo_pessoa, nome, nome_fantasia, documento, email, telefone) VALUES (?, ?, ?, ?, ?, ?)";
        int idGerado = -1;

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            int index = 0;
            stmt.setString(++index, cliente.getTipoPessoa());
            stmt.setString(++index, cliente.getNome());
            stmt.setString(++index, cliente.getNomeFantasia());
            stmt.setString(++index, cliente.getDocumento());
            stmt.setString(++index, cliente.getEmail());
            stmt.setString(++index, cliente.getTelefone());

            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    idGerado = generatedKeys.getInt(1);
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao salvar cliente.");
            e.printStackTrace();
        }

        return idGerado;
    }

    // metodo pra atualizar cliente (agora devolve true/false: o servlet pode avisar se n deu certo)
    public boolean atualizar(Cliente cliente) {
        String sql = "UPDATE transportadora.cliente SET tipo_pessoa = ?, nome = ?, nome_fantasia = ?, documento = ?, email = ?, telefone = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            int index = 0;
            stmt.setString(++index, cliente.getTipoPessoa());
            stmt.setString(++index, cliente.getNome());
            stmt.setString(++index, cliente.getNomeFantasia());
            stmt.setString(++index, cliente.getDocumento());
            stmt.setString(++index, cliente.getEmail());
            stmt.setString(++index, cliente.getTelefone());
            stmt.setInt(++index, cliente.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar");
            e.printStackTrace();
            return false;
        }
    }

    public List<Cliente> buscarClientes() {
        List<Cliente> listaClientes = new ArrayList<>();
        // order by para nao devolver em qualquer ordem
        String sql = "SELECT * FROM transportadora.cliente ORDER BY id DESC";

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                listaClientes.add(mapear(rs));
            }

        } catch (SQLException e) {
            System.out.println("Erro ao buscar clientes.");
            e.printStackTrace();
        }

        System.out.println("Clientes puxados");
        return listaClientes;
    }

    public Cliente buscarPorID(int id) {
        String sql = "SELECT * FROM transportadora.cliente WHERE id = ?";
        Cliente cliente = null;

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    cliente = mapear(rs);
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar cliente por ID: " + e.getMessage());
        }
        return cliente;
    }

    // true se ja tem algum cliente com esse doc
    public boolean existeDocumento(String documento) {
        return existeDocumentoDeOutro(documento, -1);
    }

    // usado no update p/ ver se tem algum outro cliente com este mesmo documento
    public boolean existeDocumentoDeOutro(String documento, int idAtual) {
        String sql = "SELECT 1 FROM transportadora.cliente WHERE documento = ? AND id <> ?"; // diferente de ?

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, documento);
            stmt.setInt(2, idAtual);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            System.out.println("Erro ao verificar documento.");
            e.printStackTrace();
            return false;
        }
    }

    // apaga so o cliente e false para se der algum erro
    public boolean deletarCliente(int id) {
        String sql = "DELETE FROM transportadora.cliente WHERE id = ?";

        try (Connection conn = ConnectionFactory.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int linhasAfetadas = stmt.executeUpdate();
            if (linhasAfetadas > 0) {
                System.out.println("Cliente excluído com sucesso.");
                return true;
            }
            System.out.println("Nenhum cliente encontrado com o ID: " + id);
            return false;

        } catch (SQLException e) {
            if (VIOLACAO_FK.equals(e.getSQLState())) {
                System.out.println("Cliente " + id + " não pode ser excluído: \"... tem entregas ou endereços ligados a ele.\"");
            } else {
                System.out.println("Erro ao tentar excluir cliente.");
                e.printStackTrace();
            }
            return false;
        }
    }

    // apagar um cliente e todos seus endereços / se nao apagar um, nao apaga nada
    public boolean excluirComEnderecos(int id) {
        try (Connection conn = ConnectionFactory.conectar()) {
            conn.setAutoCommit(false);

            try (PreparedStatement apagaEnderecos = conn.prepareStatement("DELETE FROM transportadora.endereco WHERE cliente_id = ?");
                 PreparedStatement apagaCliente = conn.prepareStatement("DELETE FROM transportadora.cliente WHERE id = ?")) {

                apagaEnderecos.setInt(1, id);
                apagaEnderecos.executeUpdate();

                apagaCliente.setInt(1, id);
                int linhas = apagaCliente.executeUpdate();

                conn.commit();
                return linhas > 0;

            } catch (SQLException e) {
                conn.rollback();   // desfaz tb o DELETE dos endereços
                if (VIOLACAO_FK.equals(e.getSQLState())) {
                    System.out.println("Cliente " + id + " não pode ser excluído: tem entregas ligadas a ele.");
                } else {
                    System.out.println("Erro ao excluir cliente e endereços.");
                    e.printStackTrace();
                }
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    //metodo para transformar uma linha do banco em um objeto java usando Resultset
    private Cliente mapear(ResultSet rs) throws SQLException {
        Cliente cliente = new Cliente();
        cliente.setId(rs.getInt("id"));
        cliente.setTipoPessoa(rs.getString("tipo_pessoa"));
        cliente.setNome(rs.getString("nome"));
        cliente.setNomeFantasia(rs.getString("nome_fantasia"));
        cliente.setDocumento(rs.getString("documento"));
        cliente.setEmail(rs.getString("email"));
        cliente.setTelefone(rs.getString("telefone"));
        return cliente;
    }
}