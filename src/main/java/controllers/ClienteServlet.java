package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
            boolean excluido = clienteDAO.excluirComEnderecos(id);
            resp.sendRedirect(req.getContextPath() + "/clientes?status=" + (excluido ? "excluido" : "erro_exclusao"));
            return;
        }

        // apagar so um endereco especifico da lista
        if ("excluirEndereco".equals(acaoForm)) {
            try {
                System.out.println("=== ENTROU NO BLOCO EXCLUIR ENDEREÇO ===");
                
                String idEndStr = req.getParameter("enderecoId");
                String idCliStr = req.getParameter("clienteId");
                
                System.out.println("Parametros brutos -> enderecoId: " + idEndStr + " | clienteId: " + idCliStr);
                
                int idEnd = Integer.parseInt(idEndStr);
                int idCli = Integer.parseInt(idCliStr);
                
                List<Endereco> enderecosDoCliente = enderecoDAO.buscarTodosPorClienteId(idCli);
                System.out.println("Quantidade de endereços achados no banco: " + enderecosDoCliente.size());
                
                if (enderecosDoCliente.size() <= 1) {
                    System.out.println("BLOQUEADO: Cliente so tem 1 endereco ou menos.");
                    guardarFlash(req, "flashErros", List.of("Não é possível excluir este endereço. Todo cliente precisa ter obrigatoriamente pelo menos 1 endereço cadastrado."));
                    resp.sendRedirect(req.getContextPath() + "/clientes?acao=editar&id=" + idCli + "&status=erro");
                    return;
                }
                
                boolean sucesso = enderecoDAO.deletarPorId(idEnd); 
                System.out.println("Resultado da exclusao no DAO: " + sucesso);
                
                if (sucesso) {
                    resp.sendRedirect(req.getContextPath() + "/clientes?acao=editar&id=" + idCli + "&status=end_excluido");
                } else {
                    guardarFlash(req, "flashErros", List.of("Este endereço não pode ser excluído pois está vinculado a uma entrega."));
                    resp.sendRedirect(req.getContextPath() + "/clientes?acao=editar&id=" + idCli + "&status=erro");
                }
            } catch (Exception e) {
                System.out.println("EXCECAO CAPTURADA NA EXCLUSAO DE ENDERECO:");
                e.printStackTrace();
            }
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
        List<String> erros = new ArrayList<>(validador.validar(novoCliente));

        // atualizar dados pessoais na tela de edicao
        if ("atualizarCliente".equals(acaoForm)) {
            int idCli = Integer.parseInt(idParam);
            novoCliente.setId(idCli);

            // doc ja usado
            if (erros.isEmpty() && clienteDAO.existeDocumentoDeOutro(docLimpo, idCli)) {
                erros.add("Já existe outro cliente cadastrado com esse CPF/CNPJ.");
            }

            if (!erros.isEmpty()) {
                // guarda erros + o que foi digitado e redireciona. assim a url fica com status=erro
                guardarFlash(req, "flashErros", erros);
                guardarFlash(req, "flashCliente", novoCliente);
                resp.sendRedirect(req.getContextPath() + "/clientes?acao=editar&id=" + idCli + "&status=erro");
                return;
            }

            if (clienteDAO.atualizar(novoCliente)) {
                // mandando pra tela principal
                resp.sendRedirect(req.getContextPath() + "/clientes?status=atualizado");
            } else {
                guardarFlash(req, "flashErros", List.of("Não foi possível atualizar o cliente no banco."));
                guardarFlash(req, "flashCliente", novoCliente);
                resp.sendRedirect(req.getContextPath() + "/clientes?acao=editar&id=" + idCli + "&status=erro");
            }
            return;
        }
        
        // cadastro de cliente novo /se nao tem acaoForm, eh pq veio do clienteCadastro.jsp
        String cep = req.getParameter("cep");
        String logradouro = req.getParameter("logradouro");
        String complemento = req.getParameter("complemento");
        String bairro = req.getParameter("bairro");
        String cidade = req.getParameter("cidade");
        String uf = req.getParameter("uf");
        
        // s/n
        int numero = 0;
        try {
            String numParam = req.getParameter("numero");
            if (numParam != null && !numParam.trim().isEmpty() && !numParam.equalsIgnoreCase("S/N")) {
                numero = Integer.parseInt(numParam);
            }
        } catch (NumberFormatException e) {
            numero = 0;
        }

        // editando endereço
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

            // se ta td certo, salva e volta pra tela de ediçao do cliente
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
        
        // CPF/CNPJ repetido 
        if (erros.isEmpty() && clienteDAO.existeDocumento(docLimpo)) {
            erros.add("Já existe um cliente cadastrado com esse CPF/CNPJ.");
        }

        erros.addAll(validadorEndereco.getErros());

        if (!erros.isEmpty()) {
            voltarCadastroComErro(req, resp, erros);
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
            voltarCadastroComErro(req, resp, List.of("Não foi possível salvar o cliente. Tente novamente."));
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String acao = req.getParameter("acao");

        // clicou em novo, vai pra tela limpa
        if ("novo".equals(acao)) {
            req.setAttribute("erros", pegarFlash(req, "flashErros"));
            req.setAttribute("form", pegarFlash(req, "flashForm"));
            req.getRequestDispatcher("/clienteCadastro.jsp").forward(req, resp);
            return; 
        }

        // clicou no lapis, manda pra tela nova de clienteEditar
        if ("editar".equals(acao)) {
            int id = Integer.parseInt(req.getParameter("id"));
            // se veio de um erro de validação, mostra o que a pessoa digitou em vez doq ta no banco
            Cliente cliente = (Cliente) pegarFlash(req, "flashCliente");
            if (cliente == null || cliente.getId() != id) {
                cliente = clienteDAO.buscarPorID(id);
            }
            req.setAttribute("erros", pegarFlash(req, "flashErros"));
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

    // erro + redirect / a URL mostra status=erro e o forms n perde o que foi digitado

    private static final String[] CAMPOS_FORM = {
        "documento", "telefone", "nome", "nomeFantasia", "email",
        "cep", "logradouro", "numero", "complemento", "bairro", "cidade", "uf"
    };

    private void voltarCadastroComErro(HttpServletRequest req, HttpServletResponse resp, List<String> erros) throws IOException {
        Map<String, String> form = new HashMap<>();
        for (String campo : CAMPOS_FORM) {
            form.put(campo, req.getParameter(campo));
        }
        guardarFlash(req, "flashErros", erros);
        guardarFlash(req, "flashForm", form);
        resp.sendRedirect(req.getContextPath() + "/clientes?acao=novo&status=erro");
    }

    // guarda na sessão só até a próxima requisição
    private void guardarFlash(HttpServletRequest req, String nome, Object valor) {
        req.getSession().setAttribute(nome, valor);
    }

    // lê e já apaga da sessão (usa uma vez só)
    private Object pegarFlash(HttpServletRequest req, String nome) {
        HttpSession session = req.getSession(false);
        if (session == null) return null;
        Object valor = session.getAttribute(nome);
        session.removeAttribute(nome);
        return valor;
    }
}