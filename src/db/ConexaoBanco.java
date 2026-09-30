package src.db;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoBanco {

    public static Connection conectar() {
        String url = "jdbc:postgresql://localhost:5432/daniel";
        String usuario = "daniel";
        String senha = "123456789";

        try {
            Connection conn = DriverManager.getConnection(url, usuario, senha);
            System.out.println("Conexão feita.");
            return conn;

        } catch (SQLException e) {
            System.out.println("Erro ao se conectar ao banco.");
            e.printStackTrace();
            return null;

        }
    }
}
