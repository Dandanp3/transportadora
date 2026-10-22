package controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ClienteDAO;
import dao.EnderecoDAO;
import dao.EntregaDAO;
import dao.ItemEntregaDAO;
import dao.ProdutoDAO;

import models.Cliente;
import models.Endereco;
import models.Entrega;
import models.ItemEntrega;
import models.Produto;

@WebServlet("/entregas")
public class EntregaServlet extends HttpServlet {
    private ClienteDAO clienteDAO = new ClienteDAO();
    private EnderecoDAO enderecoDAO = new EnderecoDAO();
    private EntregaDAO entregaDAO = new EntregaDAO();
    private ItemEntregaDAO itemDAO = new ItemEntregaDAO();
    private ProdutoDAO produtoDAO = new ProdutoDAO();

    @Override 
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String acaoForm = req.getParameter("acaoForm");

        // delete entrega
        if ("excluirEntrega".equals(acaoForm)) {
            int id = Integer.parseInt(req.getParameter("id"));
            entregaDAO.deletarEntrega(id);
            resp.sendRedirect(req.getContextPath() + "/entregas?status=excluido");
            return;
        }

        try {
            // dados da entrega
            int remetenteId = Integer.parseInt(req.getParameter("remetenteId"));
            int destinatarioId = Integer.parseInt(req.getParameter("destinatarioId"));
            int enderecoOrigemId = Integer.parseInt(req.getParameter("enderecoOrigemId"));
            int enderecoDestinoId = Integer.parseInt(req.getParameter("enderecoDestinoId"));
            String numeroNotaFiscal = req.getParameter("numeroNotaFiscal"); 

            // dados da carga baseado em produto
            int produtoId = Integer.parseInt(req.getParameter("produtoId")); 
            int quantidade = Integer.parseInt(req.getParameter("quantidade"));

            // busca o produto no banco pra pegar o valor q ta tabelado
            Produto produto = produtoDAO.buscarPorID(produtoId);

            if (produto == null) {
                resp.sendRedirect(req.getContextPath() + "/entregas?status=erro_produto");
                return;
            }
            
             // calculo do frete
            double freteCalculado = produto.getValorPadrao() * (quantidade / 12);

            // salvando a entrega
            String dataEmissao = LocalDate.now().toString(); 
            
            // se precisar do objeto cliente e endereco pra passar pro construtor
            Cliente remetente = clienteDAO.buscarPorID(remetenteId);
            Cliente destinatario = clienteDAO.buscarPorID(destinatarioId);
            Endereco origem = enderecoDAO.buscarPorID(enderecoOrigemId);
            Endereco destino = enderecoDAO.buscarPorID(enderecoDestinoId);

            Entrega novaEntrega = new Entrega(remetente, destinatario, origem, destino, "PENDENTE", freteCalculado, numeroNotaFiscal, dataEmissao);
            
            // salva a entrega e pega o id q o banco gerou
            int idEntrega = entregaDAO.salvarEntrega(novaEntrega);

            // salva o item amarrado na entrega gerada
            if (idEntrega != -1) {
                ItemEntrega novoItem = new ItemEntrega(idEntrega, produtoId, quantidade, produto.getValorPadrao());
                itemDAO.salvarItemEntrega(novoItem);
                
                resp.sendRedirect(req.getContextPath() + "/entregas?status=sucesso");
            } else {
                resp.sendRedirect(req.getContextPath() + "/entregas?status=erro_banco");
            }

        } catch (Exception e) {
            System.out.println("deu merda ao salvar a entrega");
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/entregas?status=erro_excecao");
        }
    }

    @Override 
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String acao = req.getParameter("acao");

        // vai pra tela de nova entrega carregando os selects
        if ("novo".equals(acao)) {
            // puxa tudo pro cara poder escolher no combobox
            List<Cliente> clientes = clienteDAO.buscarClientes();
            List<Produto> produtos = produtoDAO.buscarTodos();
            
            req.setAttribute("clientes", clientes);
            req.setAttribute("produtos", produtos);
            req.getRequestDispatcher("/entregaCadastro.jsp").forward(req, resp);
            return;
        }

        // listagem padrao
        List<Entrega> entregas = entregaDAO.buscarEntregas();
        req.setAttribute("entregas", entregas);
        req.getRequestDispatcher("/entregaPage.jsp").forward(req, resp);
    }
}