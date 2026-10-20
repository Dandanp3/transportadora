package controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.ClienteDAO;
import dao.DocumentoFiscalDAO;
import dao.EnderecoDAO;
import dao.EntregaDAO;
import dao.ItemDocumentoDAO;
import dao.ItemEntregaDAO;
import dao.ProdutoDAO;

import models.Cliente;
import models.DocumentoFiscal;
import models.Endereco;
import models.Entrega;
import models.ItemDocumento;
import models.ItemEntrega;
import models.Produto;

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
    private ProdutoDAO produtoDAO = new ProdutoDAO();
    private ItemDocumentoDAO itemDocDAO = new ItemDocumentoDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        try {
            // recebendo dados
            int remetenteId = Integer.parseInt(req.getParameter("remetenteId"));
            int destinatarioId = Integer.parseInt(req.getParameter("destinatarioId"));
            int produtoId = Integer.parseInt(req.getParameter("produtoId")); 
            int quantidade = Integer.parseInt(req.getParameter("quantidade")); 

            // dados da caixa
            double peso = Double.parseDouble(req.getParameter("peso"));
            int altura = Integer.parseInt(req.getParameter("altura"));
            int largura = Integer.parseInt(req.getParameter("largura"));
            int comprimento = Integer.parseInt(req.getParameter("comprimento"));

            // pegando os objetos
            Cliente remetente = clienteDAO.buscarPorID(remetenteId);
            Cliente destinatario = clienteDAO.buscarPorID(destinatarioId);
            Produto produto = produtoDAO.buscarPorID(produtoId);

            List<Endereco> enderecosOrigem = enderecoDAO.buscarPorClienteId(remetenteId);
            List<Endereco> enderecosDestino = enderecoDAO.buscarPorClienteId(destinatarioId);

            // segurança
            if (remetente == null || destinatario == null || produto == null || enderecosOrigem.isEmpty() || enderecosDestino.isEmpty()) {
                resp.sendRedirect(req.getContextPath() + "/entregas?status=erro_dados");
                return;
            }

            Endereco origem = enderecosOrigem.get(0);
            Endereco destino = enderecosDestino.get(0);

            // calculo do frete
            double freteCalculado = CalculadorFreteUtils.calcularFrete(peso, altura, largura, comprimento);

            // salvandoa  entrega
            String numRastreio = CodBarrasUtils.gerarCodBarras(); 
            String dataEmissao = LocalDate.now().toString(); 
            
            Entrega novaEntrega = new Entrega(remetente, destinatario, origem, destino, numRastreio, "Pendente", freteCalculado, dataEmissao);
            int idEntrega = entregaDAO.salvarEntrega(novaEntrega);

            if (idEntrega != -1) {
                
                // gera o codigo unico com 8 caracteres e salva itemEntrega
                String codEtiquetaCaixa = "ENV-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
                ItemEntrega itemEntrega = new ItemEntrega(idEntrega, codEtiquetaCaixa, peso, altura, largura, comprimento);
                itemDAO.salvarItemEntrega(itemEntrega);

                // gerando a NF-e
                double valorTotalCarga = produto.getValorPadrao() * quantidade;
                
                DocumentoFiscal doc = new DocumentoFiscal();
                doc.setEntregaId(idEntrega);
                doc.setValorTotalDeclarado(valorTotalCarga);

                if ("J".equals(remetente.getTipoPessoa())) { 
                    doc.setTipoDocumento("NF-e");
                    doc.setNumeroDocumento("NF-" + (int)(Math.random() * 100000));
                    String chave = GeradorNfeUtils.gerarChaveAcesso(remetente.getDocumento(), origem.getUf(), dataEmissao);
                    doc.setChaveAcesso(chave);
                } else { 
                    doc.setTipoDocumento("Declaracao");
                    doc.setNumeroDocumento("DEC-" + (int)(Math.random() * 100000));
                    doc.setChaveAcesso(null); 
                }
                
                int idDocumento = docDAO.salvarDocumento(doc);

                // salvando
                if (idDocumento != -1) {
                    ItemDocumento itemDoc = new ItemDocumento(idDocumento, produto, quantidade, produto.getValorPadrao());
                    itemDocDAO.salvarItemDocumento(itemDoc);
                }

                System.out.printf("Entrega salva! Frete: R$ %.2f\n", freteCalculado);
                resp.sendRedirect(req.getContextPath() + "/entregas?status=sucesso");
            } else {
                resp.sendRedirect(req.getContextPath() + "/entregas?status=erro");
            }

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect(req.getContextPath() + "/entregas?status=erro_excecao");
        }
    }
}