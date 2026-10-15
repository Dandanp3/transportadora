package controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ClienteDAO;
import dao.DocumentoFiscalDAO;
import dao.EnderecoDAO;
import dao.EntregaDAO;
import dao.ItemEntregaDAO;
import models.Cliente;
import models.Endereco;
import models.Entrega;
import models.ItemEntrega;
import models.NotaFiscal;
import utils.CalculadorFreteUtils;
import utils.CodBarrasUtils;
import utils.GeradorNfeUtils;

@WebServlet("/entregas")
public class EntregaServlet extends HttpServlet {

    private ClienteDAO clienteDAO = new ClienteDAO();
    private EnderecoDAO enderecoDAO = new EnderecoDAO();
    private EntregaDAO entregaDAO = new EntregaDAO();
    private ItemEntregaDAO itemDAO = new ItemEntregaDAO();
    private DocumentoFiscalDAO docDAO = new DocumentoFiscalDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        try {
            // Recebe os dados enviados pelo forms
            int remetenteId = Integer.parseInt(req.getParameter("remetenteId"));
            int destinatarioId = Integer.parseInt(req.getParameter("destinatarioId"));
            String status = req.getParameter("status");
            String numRastreio = req.getParameter("numRastreio");
            String dataEmissao = req.getParameter("dataEmissao");

            double peso = Double.parseDouble(req.getParameter("peso"));
            double altura = Double.parseDouble(req.getParameter("altura"));
            double largura = Double.parseDouble(req.getParameter("largura"));
            double comprimento = Double.parseDouble(req.getParameter("comprimento"));
            double valorMercadoria = Double.parseDouble(req.getParameter("valorMercadoria"));

            // buscaendereços cadastrados dos clientes
            List<Endereco> enderecosOrigem = enderecoDAO.buscarPorClienteId(remetenteId);
            List<Endereco> enderecosDestino = enderecoDAO.buscarPorClienteId(destinatarioId);

            if (enderecosOrigem.isEmpty() || enderecosDestino.isEmpty()) {
                resp.sendRedirect(req.getContextPath() + "/entregas.jsp?status=erro_endereco");
                return;
            }

            Endereco origem = enderecosOrigem.get(0);
            Endereco destino = enderecosDestino.get(0);

            // verificar CPF/CNPJ
            List<Cliente> clientes = clienteDAO.buscarClientes();
            Cliente remetente = clientes.stream()
                    .filter(c -> c.getId() == remetenteId)
                    .findFirst()
                    .orElse(null);

            Cliente destinatario = new Cliente();
            destinatario.setId(destinatarioId);

            if (remetente == null) {
                resp.sendRedirect(req.getContextPath() + "/entregas.jsp?status=erro_cliente");
                return;
            }

            // salva a entrega no banco
            Entrega novaEntrega = new Entrega(remetente, destinatario, origem, destino, numRastreio, status, 0.0, dataEmissao);
            int idEntrega = entregaDAO.salvarEntrega(novaEntrega);

            if (idEntrega != -1) {
                // salva o item da entrega com cod de barras gerado
                String codBarras = CodBarrasUtils.gerarCodBarras();
                ItemEntrega item = new ItemEntrega(idEntrega, codBarras, peso, altura, largura, comprimento);
                itemDAO.salvarItemEntrega(item);

                // calcula o frete por cubagem
                double freteCalculado = CalculadorFreteUtils.calcularFrete(peso, altura, largura, comprimento);
                System.out.printf("Frete calculado via Web: R$ %.2f\n", freteCalculado);

                // gera doc fiscal
                NotaFiscal doc = new NotaFiscal();
                doc.setEntregaId(idEntrega);
                doc.setValorMercadoria(valorMercadoria);

                if (remetente.getDocumento().length() > 11) { // PJ
                    String numNf = req.getParameter("numNf");
                    if (numNf == null || numNf.isBlank()) numNf = "1001";

                    String chave = GeradorNfeUtils.gerarChaveAcesso(remetente.getDocumento(), origem.getUf(), dataEmissao);
                    doc.setNumeroNf(numNf);
                    doc.setChaveAcesso(chave);
                } else { // PF
                    doc.setNumeroNf("DEC-CONT");
                    doc.setChaveAcesso(null);
                }

                docDAO.salvarDocumento(doc);

                resp.sendRedirect(req.getContextPath() + "/entregas.jsp?status=sucesso");
            } else {
                resp.sendRedirect(req.getContextPath() + "/entregas.jsp?status=erro");
            }

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/entregas.jsp?status=erro");
        }
    }
}