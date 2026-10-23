package controllers;

import java.io.IOException;
import java.util.ArrayList;
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
import utils.FieldUtils;

@WebServlet("/enderecos")
public class EnderecoServlet extends HttpServlet {
    private EnderecoDAO enderecoDAO = new EnderecoDAO();
    private ClienteDAO clienteDAO = new ClienteDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String acao = req.getParameter("acao");

        // Roteamento para a tela de cadastro ou edição
        if ("novo".equals(acao) || "editar".equals(acao)) {
            // Manda a lista de clientes para o combobox da tela de cadastro de endereço
            List<Cliente> clientes = clienteDAO.buscarClientes();
            req.setAttribute("clientes", clientes);

            if ("editar".equals(acao)) {
                int id = Integer.parseInt(req.getParameter("id"));
                Endereco endereco = enderecoDAO.buscarPorID(id);
                req.setAttribute("endereco", endereco);
            }
            // acao=novo&clienteId=X: o JSP lê param.clienteId e já deixa o cliente selecionado

            req.getRequestDispatcher("/enderecoCadastro.jsp").forward(req, resp);
            return;
        }

        // Ação padrão (URL limpa): Listar todos os endereços
        List<Endereco> enderecos = enderecoDAO.buscarEnderecos();

        // Laço para atrelar o dono (Cliente) a cada Endereço
        for (Endereco end : enderecos) {
            Cliente dono = clienteDAO.buscarPorID(end.getClienteId());
            end.setCliente(dono);
        }

        req.setAttribute("enderecos", enderecos);
        req.setAttribute("totalEnderecos", enderecos.size());

        req.getRequestDispatcher("/enderecoPage.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String acaoForm = req.getParameter("acaoForm");

        // Salvar ou atualizar 
        if ("salvarEndereco".equals(acaoForm)) {
            salvar(req, resp);
            return;
        }


        if ("excluirEndereco".equals(acaoForm)) {
            try {
                int id = Integer.parseInt(req.getParameter("id"));
                boolean sucesso = enderecoDAO.deletarPorId(id);
                
                if (sucesso) {
                    resp.sendRedirect(req.getContextPath() + "/enderecos?status=excluido");
                } else {
                    resp.sendRedirect(req.getContextPath() + "/enderecos?status=erro_exclusao");
                }
            } catch (Exception e) {
                e.printStackTrace();
                resp.sendRedirect(req.getContextPath() + "/enderecos?status=erro_exclusao");
            }
            return;
        }
        // ação desconhecida / volta para a lista em vez de devolver uma pag em branco
        resp.sendRedirect(req.getContextPath() + "/enderecos");
    }

    private void salvar(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        String clienteIdStr = req.getParameter("clienteId");
        String cep = req.getParameter("cep");
        String logradouro = req.getParameter("logradouro");
        String numeroStr = req.getParameter("numero");
        String complemento = req.getParameter("complemento");
        String bairro = req.getParameter("bairro");
        String cidade = req.getParameter("cidade");
        String uf = req.getParameter("uf");

        FieldUtils validador = new FieldUtils();
        validador.verificarNotBlank(clienteIdStr, "Selecione o cliente dono do endereço.")
                 .verificarNotBlank(cep, "O CEP é obrigatório.")
                 .verificarNotBlank(logradouro, "O Logradouro é obrigatório.")
                 .verificarNotBlank(numeroStr, "O Número é obrigatório (marque \"Sem número\" se não houver).")
                 .verificarNotBlank(bairro, "O Bairro é obrigatório.")
                 .verificarNotBlank(cidade, "A Cidade é obrigatória.")
                 .verificarNotBlank(uf, "O Estado (UF) é obrigatório.");
        List<String> erros = new ArrayList<>(validador.getErros());

        int clienteId = 0;
        if (clienteIdStr != null && !clienteIdStr.trim().isEmpty()) {
            try {
                clienteId = Integer.parseInt(clienteIdStr.trim());
            } catch (NumberFormatException e) {
                erros.add("Cliente inválido.");
            }
        }

        // "S/N" vira 0 
        int numero = 0;
        if (numeroStr != null && !numeroStr.trim().isEmpty() && !numeroStr.trim().equalsIgnoreCase("S/N")) {
            try {
                numero = Integer.parseInt(numeroStr.trim());
            } catch (NumberFormatException e) {
                erros.add("O Número deve conter apenas dígitos (ou S/N).");
            }
        }

        boolean edicao = idStr != null && !idStr.trim().isEmpty();
        Endereco endereco = new Endereco(clienteId, cep, logradouro, numero, complemento, bairro, cidade, uf);

        try {
            if (edicao) {
                endereco.setId(Integer.parseInt(idStr.trim()));
            }

            if (!erros.isEmpty()) {
                voltarComErro(req, resp, erros, edicao ? endereco : null);
                return;
            }

            if (edicao) {
                enderecoDAO.atualizar(endereco);
                resp.sendRedirect(req.getContextPath() + "/enderecos?status=atualizado");
            } else {
                int idGerado = enderecoDAO.salvarEndereco(endereco);
                if (idGerado == -1) {
                    voltarComErro(req, resp, List.of("Não foi possível salvar o endereço. Tente novamente."), null);
                    return;
                }
                resp.sendRedirect(req.getContextPath() + "/enderecos?status=sucesso");
            }
        } catch (Exception e) {
            System.out.println("Erro ao tentar guardar o endereço.");
            e.printStackTrace();
            voltarComErro(req, resp, List.of("Erro inesperado ao salvar o endereço."), edicao ? endereco : null);
        }
    }

    // devolve o forms com a lista de erros e o que a pessoa digitou (os campos leem param.* ou o endereço de edição)
    private void voltarComErro(HttpServletRequest req, HttpServletResponse resp, List<String> erros, Endereco enderecoEdicao)
            throws ServletException, IOException {
        req.setAttribute("erros", erros);
        req.setAttribute("clientes", clienteDAO.buscarClientes());
        if (enderecoEdicao != null) {
            req.setAttribute("endereco", enderecoEdicao);
        }
        req.getRequestDispatcher("/enderecoCadastro.jsp").forward(req, resp);
    }
}
