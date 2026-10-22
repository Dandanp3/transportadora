package controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Produto;
import dao.ProdutoDAO;
import utils.FieldUtils;

@WebServlet("/produtos")
public class ProdutoServlet extends HttpServlet {

    // instanciando
    private ProdutoDAO produtoDAO = new ProdutoDAO();

    @Override 
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        // formatação
        req.setCharacterEncoding("UTF-8");
        
        String acaoForm = req.getParameter("acaoForm");

        // apagar o produto
        if ("excluirProduto".equals(acaoForm)) {
            int id = Integer.parseInt(req.getParameter("id"));
            produtoDAO.deletar(id); 
            resp.sendRedirect(req.getContextPath() + "/produtos?status=excluido");
            return;
        }
    
        // pegando infos do produto como string
        String idParam = req.getParameter("id");
        String descricao = req.getParameter("descricao");
        String valorStr = req.getParameter("valorPadrao");
        String pesoStr = req.getParameter("pesoPadrao");

        // verificando se esta vazio
        FieldUtils validador = new FieldUtils();
        validador.verificarNotBlank(descricao, "A descrição é obrigatória.")
                 .verificarNotBlank(valorStr, "O valor é obrigatório.")
                 .verificarNotBlank(pesoStr, "O peso é obrigatório.");

        // devolve pra tela certa se der problema
        if (validador.temErros()) {
            req.setAttribute("erros", validador.getErros());
            if ("atualizarProduto".equals(acaoForm)) {
                req.getRequestDispatcher("/produtoEditar.jsp").forward(req, resp);
            } else {
                req.getRequestDispatcher("/produtoCadastro.jsp").forward(req, resp);
            }
            return;
        }

        // converte pra double e ja troca virgula por ponto
        double valorPadrao = 0.0;
        double pesoPadrao = 0.0;

        System.out.println("Valor recebido: [" + valorStr + "]");
        System.out.println("Peso recebido: [" + pesoStr + "]");

        try {
            if (valorStr != null && !valorStr.isBlank()) {
                valorPadrao = Double.parseDouble(
                    valorStr.trim().replace(".", "").replace(",", ".")
                );
            }

            if (pesoStr != null && !pesoStr.isBlank()) {
                pesoPadrao = Double.parseDouble(
                    pesoStr.trim().replace(".", "").replace(",", ".")
                );
            }

            if (!Double.isFinite(valorPadrao) || !Double.isFinite(pesoPadrao)) {
                throw new NumberFormatException("Valor ou peso inválido.");
            }

        } catch (NumberFormatException e) {
            req.setAttribute("erros", List.of(
                "Os campos de Valor e Peso devem conter apenas números válidos."
            ));

            if ("atualizarProduto".equals(acaoForm)) {
                req.getRequestDispatcher("/produtoEditar.jsp").forward(req, resp);
            } else {
                req.getRequestDispatcher("/produtoCadastro.jsp").forward(req, resp);
            }
            return;
        }
        // cria objeto
        Produto novoProduto = new Produto(descricao, valorPadrao, pesoPadrao);

        // atualiza dados do produto
        if ("atualizarProduto".equals(acaoForm)) {
            novoProduto.setId(Integer.parseInt(idParam));
            produtoDAO.atualizar(novoProduto);
            resp.sendRedirect(req.getContextPath() + "/produtos?status=atualizado");
            return;
        }

        // salva no banco
        boolean sucesso = produtoDAO.salvar(novoProduto);
        if (sucesso) {
            resp.sendRedirect(req.getContextPath() + "/produtos?status=sucesso");
        } else {
            // erro interno pra salvar no banco
            req.setAttribute("erros", List.of("erro interno pra salvar no banco"));
            req.getRequestDispatcher("/produtoCadastro.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String acao = req.getParameter("acao");

        // clicou em novo, vai pra tela limpa
        if ("novo".equals(acao)) {
            req.getRequestDispatcher("/produtoCadastro.jsp").forward(req, resp);
            return;
        }

        // tela de ediçao clicando no lapis
        if ("editar".equals(acao)) {
            int id = Integer.parseInt(req.getParameter("id"));
            Produto produto = produtoDAO.buscarPorID(id);
            
            req.setAttribute("produto", produto);
            req.getRequestDispatcher("/produtoEditar.jsp").forward(req, resp);
            return;
        }

        // listagem padrao da tela principal
        List<Produto> produtos = produtoDAO.buscarTodos();
        req.setAttribute("produtos", produtos);
        req.getRequestDispatcher("/produtoPage.jsp").forward(req, resp);
    }
}