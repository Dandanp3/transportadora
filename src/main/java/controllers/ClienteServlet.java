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

@WebServlet("/clientes")
public class ClienteServlet extends HttpServlet {
    
    // instanciando 
    private ClienteValidador validador = new ClienteValidador();
    private ClienteDAO clienteDAO = new ClienteDAO();
    private EnderecoDAO enderecoDAO = new EnderecoDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String acaoForm = req.getParameter("acaoForm");
        
        //  apagar o cliente inteiro 
        if ("excluirCliente".equals(acaoForm)) {
            int id = Integer.parseInt(req.getParameter("id"));
            enderecoDAO.deletarEnderecoPorCliente(id); 
            clienteDAO.deletarCliente(id);              
            resp.sendRedirect(req.getContextPath() + "/clientes?status=excluido");
            return;
        }

        // apagar so um endereco especifico da lista
        if ("excluirEndereco".equals(acaoForm)) {
            int idEnd = Integer.parseInt(req.getParameter("enderecoId"));
            int idCli = Integer.parseInt(req.getParameter("clienteId"));
            
            enderecoDAO.deletarPorId(idEnd); 
            
            // volta pra propria tela de edicao
            resp.sendRedirect(req.getContextPath() + "/clientes?acao=editar&id=" + idCli + "&status=end_excluido");
            return;
        }

        // pegando as info pessoais 
        String idParam = req.getParameter("id");
        String nome = req.getParameter("nome");
        String nomeFantasia = req.getParameter("nomeFantasia");
        String documento = req.getParameter("documento");
        String email = req.getParameter("email");
        String telefone = req.getParameter("telefone");

        // tira ponto e traço
        String docLimpo = documento != null ? documento.replaceAll("[^0-9]", "") : "";
        String tipoPessoa = docLimpo.length() > 11 ? "J" : "F";

        Cliente novoCliente = new Cliente(tipoPessoa, nome, nomeFantasia, docLimpo, email, telefone);
        List<String> erros = validador.validar(novoCliente);

        // atualizar dados pessoais na tela de edicao
        if ("atualizarCliente".equals(acaoForm)) {
            if (!erros.isEmpty()) {
                req.setAttribute("erros", erros);
                // devolve pra tela nova se der bo
                req.setAttribute("cliente", novoCliente);
                
                // puxa os enderecos dnv pra renderizar a lista em baixo msm com erro
                Endereco endereco = enderecoDAO.buscarPorClienteId(Integer.parseInt(idParam)); 
                List<Endereco> listaEnderecos = endereco != null ? java.util.Arrays.asList(endereco) : new java.util.ArrayList<>();
                req.setAttribute("enderecos", listaEnderecos);
                
                req.getRequestDispatcher("/clienteEditar.jsp").forward(req, resp);
                return;
            }
            
            novoCliente.setId(Integer.parseInt(idParam));
            clienteDAO.atualizar(novoCliente);
            resp.sendRedirect(req.getContextPath() + "/clientes?acao=editar&id=" + novoCliente.getId() + "&status=atualizado");
            return;
        }

        // cadastro de cliente novo (se nao tem acaoForm, eh pq veio do clienteCadastro.jsp)
        String cep = req.getParameter("cep");
        String logradouro = req.getParameter("logradouro");
        String complemento = req.getParameter("complemento");
        String bairro = req.getParameter("bairro");
        String cidade = req.getParameter("cidade");
        String uf = req.getParameter("uf");

        // gambiarra do s/n
        int numero = 0;
        try {
            String numParam = req.getParameter("numero");
            if (numParam != null && !numParam.trim().isEmpty() && !numParam.equalsIgnoreCase("S/N")) {
                numero = Integer.parseInt(numParam);
            }
        } catch (NumberFormatException e) {
            numero = 0;
        }

        // FLUXO DE EDITAR ENDERECO (ISOLADO)
        if ("atualizarEndereco".equals(acaoForm)) {
            int idEnd = Integer.parseInt(req.getParameter("enderecoId"));
            int idCli = Integer.parseInt(req.getParameter("clienteId"));

            // validador so do endereco
            utils.FieldUtils validadorEnd = new utils.FieldUtils();
            validadorEnd.verificarNotBlank(cep, "O CEP do endereço é obrigatório.")
                        .verificarNotBlank(logradouro, "O Logradouro é obrigatório.")
                        .verificarNotBlank(bairro, "O Bairro é obrigatório.")
                        .verificarNotBlank(cidade, "A Cidade é obrigatória.")
                        .verificarNotBlank(uf, "O Estado (UF) é obrigatório.");

            if (validadorEnd.temErros()) {
                req.setAttribute("erros", validadorEnd.getErros());
                
                // recria o objeto pra n apagar oq digitou na tela
                Endereco endErro = new Endereco(idCli, cep, logradouro, numero, complemento, bairro, cidade, uf);
                endErro.setId(idEnd);
                
                req.setAttribute("endereco", endErro);
                req.setAttribute("clienteId", idCli);
                req.getRequestDispatcher("/enderecoEditar.jsp").forward(req, resp);
                return;
            }

            // se ta td certo, salva e volta pra tela de edicao do cliente
            Endereco endParaSalvar = new Endereco(idCli, cep, logradouro, numero, complemento, bairro, cidade, uf);
            endParaSalvar.setId(idEnd);
            
            enderecoDAO.atualizar(endParaSalvar); 
            
            resp.sendRedirect(req.getContextPath() + "/clientes?acao=editar&id=" + idCli + "&status=end_atualizado");
            return;
        }

        // validando endereco tb pq eh o cadastro 
        utils.FieldUtils validadorEndereco = new utils.FieldUtils();
        validadorEndereco.verificarNotBlank(cep, "O CEP do endereço é obrigatório.")
                         .verificarNotBlank(logradouro, "O Logradouro é obrigatório.")
                         .verificarNotBlank(bairro, "O Bairro é obrigatório.")
                         .verificarNotBlank(cidade, "A Cidade é obrigatória.")
                         .verificarNotBlank(uf, "O Estado (UF) é obrigatório.");
        
        erros.addAll(validadorEndereco.getErros());

        if (!erros.isEmpty()) {
            req.setAttribute("erros", erros);
            req.getRequestDispatcher("/clienteCadastro.jsp").forward(req, resp);
            return;
        }

        // salva no banco
        int idCliente = clienteDAO.salvarCliente(novoCliente);
        if (idCliente != -1) {
            novoCliente.setId(idCliente);
            Endereco enderecoCliente = new Endereco(idCliente, cep, logradouro, numero, complemento, bairro, cidade, uf);
            enderecoDAO.salvarEndereco(enderecoCliente);
            resp.sendRedirect(req.getContextPath() + "/clientes?status=sucesso");
        } else {
            req.setAttribute("erros", List.of("erro interno pra salvar no banco"));
            req.getRequestDispatcher("/clienteCadastro.jsp").forward(req, resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String acao = req.getParameter("acao");

        // clicou em novo, vai pra tela limpa
        if ("novo".equals(acao)) {
            req.getRequestDispatcher("/clienteCadastro.jsp").forward(req, resp);
            return; 
        }

        // clicou no lapis, manda pra tela nova de clienteEditar
        if ("editar".equals(acao)) {
            int id = Integer.parseInt(req.getParameter("id"));
            Cliente cliente = clienteDAO.buscarPorID(id); 
            
            List<Endereco> listaEnderecos = enderecoDAO.buscarTodosPorClienteId(id);
            
            req.setAttribute("cliente", cliente);
            req.setAttribute("enderecos", listaEnderecos); 
            req.getRequestDispatcher("/clienteEditar.jsp").forward(req, resp);
            return;
        }

        // clicou no editar do card de endereco
        if ("editarEndereco".equals(acao)) {
            int idEnd = Integer.parseInt(req.getParameter("id"));
            int idCli = Integer.parseInt(req.getParameter("clienteId"));
            
            Endereco endereco = enderecoDAO.buscarPorID(idEnd);
            
            req.setAttribute("endereco", endereco);
            req.setAttribute("clienteId", idCli);
            req.getRequestDispatcher("/enderecoEditar.jsp").forward(req, resp);
            return;
        }

        // listagem padrao da tela principal
        List<Cliente> clientes = clienteDAO.buscarClientes();
        
        int totalClientes = clientes.size();
        long totalFisica = clientes.stream().filter(c -> "F".equals(c.getTipoPessoa())).count();
        long totalJuridica = clientes.stream().filter(c -> "J".equals(c.getTipoPessoa())).count();

        req.setAttribute("clientes", clientes);
        req.setAttribute("totalClientes", totalClientes);
        req.setAttribute("totalFisica", totalFisica);
        req.setAttribute("totalJuridica", totalJuridica);
        
        req.getRequestDispatcher("/clientePage.jsp").forward(req, resp);
    }
}