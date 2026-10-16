package controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ClienteDAO;
import dao.EnderecoDAO;
import models.Cliente;
import models.Endereco;
import validators.ClienteValidador;

// mapeando a rota do formulario jsp
@WebServlet("/clientes")
public class ClienteServlet extends HttpServlet {

    private ClienteValidador validador = new ClienteValidador();

    // o metodo doPost eh executado quando o user clica no botao "salvar cliente" no modal
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        // faz o java entender acentuação
        req.setCharacterEncoding("UTF-8");

        //pega os dados co cliente
        String nome = req.getParameter("nome");
        String nomeFantasia = req.getParameter("nomeFantasia");
        String documento = req.getParameter("documento");
        String email = req.getParameter("email");
        String telefone = req.getParameter("telefone");

        String docLimpo = documento.replaceAll("[^0-9]", "");
        String tipoPessoa = docLimpo.length() > 11 ? "J" : "F";

        String tipoEndereco = req.getParameter("tipoEndereco");
        String cep = req.getParameter("cep");
        String logradouro = req.getParameter("logradouro");
        int numero = Integer.parseInt(req.getParameter("numero"));
        String complemento = req.getParameter("complemento");
        String bairro = req.getParameter("bairro");
        String cidade = req.getParameter("cidade");
        String uf = req.getParameter("uf");

        // montando o objeto
        Cliente novoCliente = new Cliente(tipoPessoa, nome, nomeFantasia, docLimpo, email, telefone);
        List<String> erros = validador.validar(novoCliente);

        if (!erros.isEmpty()) {
            // de der erro imprime
            System.out.println("Erros de validação:");
            erros.forEach(System.out::println);
            // volta para a pag de clientes com alerta erro na URL
            resp.sendRedirect(req.getContextPath() + "/clientes.jsp?status=erro");
            return;
        }

        // salva com DAO
        ClienteDAO clienteDAO = new ClienteDAO();
        int idCliente = clienteDAO.salvarCliente(novoCliente);

        if (idCliente != -1) {
            novoCliente.setId(idCliente);
            
            Endereco enderecoCliente = new Endereco(idCliente, tipoEndereco, cep, logradouro, numero, complemento, bairro, cidade, uf);
            EnderecoDAO enderecoDAO = new EnderecoDAO();
            enderecoDAO.salvarEndereco(enderecoCliente);

            System.out.println("Cliente salvo via Web: " + nome);
            // manda usuario para a tela de clientes com uma mensagem de sucesso
            resp.sendRedirect(req.getContextPath() + "/clientes.jsp?status=sucesso");
        } else {
            System.out.println("Falha ao salvar o cliente via Web.");
            resp.sendRedirect(req.getContextPath() + "/clientes.jsp?status=erro");
        }
    }
}