package controllers;

import java.io.IOException;
import java.text.Normalizer;
import java.time.LocalDate;
import java.util.ArrayList;
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

        if ("excluirEntrega".equals(acaoForm)) {
            int id = Integer.parseInt(req.getParameter("id"));
            entregaDAO.deletarEntrega(id);
            resp.sendRedirect(req.getContextPath() + "/entregas?status=excluido");
            return;
        }

        if ("atualizarStatus".equals(acaoForm)) {
            int id = Integer.parseInt(req.getParameter("id"));
            String novoStatus = req.getParameter("novoStatus");
            
            entregaDAO.atualizarStatus(id, novoStatus);
            resp.sendRedirect(req.getContextPath() + "/entregas?status=status_atualizado");
            return;
        }

        try {
            int remetenteId = Integer.parseInt(req.getParameter("remetenteId"));
            int destinatarioId = Integer.parseInt(req.getParameter("destinatarioId"));
            int enderecoOrigemId = Integer.parseInt(req.getParameter("enderecoOrigemId"));
            int enderecoDestinoId = Integer.parseInt(req.getParameter("enderecoDestinoId"));
            
            int produtoId = Integer.parseInt(req.getParameter("produtoId")); 
            int quantidade = Integer.parseInt(req.getParameter("quantidade"));

            Produto produto = produtoDAO.buscarPorID(produtoId);
            if (produto == null) {
                resp.sendRedirect(req.getContextPath() + "/entregas?status=erro_produto");
                return;
            }
            
            // Calculo do frete 
            double freteCalculado = produto.getValorPadrao() * (quantidade / 12.0); // O 12.0 evita que 1/12 dê zero absoluto no Java

            String dataEmissao = LocalDate.now().toString(); 
            
            Cliente remetente = clienteDAO.buscarPorID(remetenteId);
            Cliente destinatario = clienteDAO.buscarPorID(destinatarioId);
            Endereco origem = enderecoDAO.buscarPorID(enderecoOrigemId);
            Endereco destino = enderecoDAO.buscarPorID(enderecoDestinoId);

            // gera nfe
            String numeroNotaFiscal = utils.GeradorNfeUtils.gerarChaveAcesso(remetente.getDocumento(), origem.getUf(), dataEmissao);

            Entrega novaEntrega = new Entrega(remetente, destinatario, origem, destino, "PENDENTE", freteCalculado, numeroNotaFiscal, dataEmissao);
            
            int idEntrega = entregaDAO.salvarEntrega(novaEntrega);

            if (idEntrega != -1) {
                ItemEntrega novoItem = new ItemEntrega(idEntrega, produtoId, quantidade, produto.getValorPadrao());
                itemDAO.salvarItemEntrega(novoItem);
                resp.sendRedirect(req.getContextPath() + "/entregas?status=sucesso");
            } else {
                resp.sendRedirect(req.getContextPath() + "/entregas?status=erro_banco");
            }

        } catch (Exception e) {
            System.out.println("Deu merda ao salvar a entrega");
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/entregas?status=erro_excecao");
        }
    }

    @Override 
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String acao = req.getParameter("acao");

        if ("novo".equals(acao)) {
            List<Cliente> clientes = clienteDAO.buscarClientes();
            List<Produto> produtos = produtoDAO.buscarTodos();
            List<Endereco> enderecos = enderecoDAO.buscarEnderecos();
            
            req.setAttribute("enderecos", enderecos);
            req.setAttribute("clientes", clientes);
            req.setAttribute("produtos", produtos);
            req.getRequestDispatcher("/entregaCadastro.jsp").forward(req, resp);
            return;
        }

        List<Entrega> entregas = entregaDAO.buscarEntregas();
        
        //  Varre as entregas anexando os produtos correspondentes a ela
        for (Entrega entrega : entregas) {
            List<ItemEntrega> itens = itemDAO.buscarPorEntregaId(entrega.getId());
            for (ItemEntrega item : itens) {
                Produto prod = produtoDAO.buscarPorID(item.getProdutoId());
                item.setProduto(prod); 
            }
            entrega.setItens(itens); 
        }

        // cards de resumo sempre contam TODAS as entregas; a lista respeita a busca (?q=)
        List<Entrega> todas = entregas;
        String q = req.getParameter("q");
        if (q != null && !q.trim().isEmpty()) {
            entregas = filtrar(entregas, q.trim());
        }

        req.setAttribute("todasEntregas", todas);
        req.setAttribute("entregas", entregas);
        req.getRequestDispatcher("/entregaPage.jsp").forward(req, resp);
    }

    // busca por NFe, status, nomes, cidade, UF, CEP e documentos 
    private List<Entrega> filtrar(List<Entrega> entregas, String q) {
        String termo = normalizar(q);
        String termoNumerico = q.replaceAll("[^0-9]", "");
        List<Entrega> resultado = new ArrayList<>();

        for (Entrega e : entregas) {
            StringBuilder texto = new StringBuilder();
            StringBuilder numeros = new StringBuilder();

            texto.append(e.getNumeroNotaFiscal()).append(' ').append(e.getStatusEntrega()).append(' ');
            numeros.append(e.getNumeroNotaFiscal());

            if (e.getRemetente() != null) {
                texto.append(e.getRemetente().getNome()).append(' ');
                numeros.append(e.getRemetente().getDocumento());
            }
            if (e.getDestinatario() != null) {
                texto.append(e.getDestinatario().getNome()).append(' ');
                numeros.append(e.getDestinatario().getDocumento());
            }
            if (e.getEnderecoDestino() != null) {
                texto.append(e.getEnderecoDestino().getCidade()).append(' ')
                     .append(e.getEnderecoDestino().getUf()).append(' ');
                numeros.append(e.getEnderecoDestino().getCep());
            }

            boolean achouTexto = normalizar(texto.toString()).contains(termo);
            boolean achouNumero = termoNumerico.length() >= 3
                    && numeros.toString().replaceAll("[^0-9]", "").contains(termoNumerico);

            if (achouTexto || achouNumero) {
                resultado.add(e);
            }
        }
        return resultado;
    }

    private String normalizar(String s) {
        if (s == null) return "";
        return Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll("\\p{M}", "").toLowerCase();
    }
}
