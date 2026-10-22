package db; // Mantenha o nome do pacote que condiz com a sua pasta

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    // trabalhoprivate static final String URL = "jdbc:postgresql://localhost:5432/daniel";
    // trabalho private static final String USUARIO = "daniel";
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres"; // pc
    private static final String USUARIO = "postgres"; // pc
    private static final String SENHA = "123456789";

    public static Connection conectar() {
        try {
            // Garante que o driver seja carregado pelo Tomcat
            Class.forName("org.postgresql.Driver");
            
            Connection conn = DriverManager.getConnection(URL, USUARIO, SENHA);
            System.out.println("Conexão com o PostgreSQL realizada com sucesso!");
            return conn;

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("driver postgreSQL não foi encontrado pelo gradle", e);
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao conectar ao banco de dados: " + e.getMessage(), e);
        }
    }
}