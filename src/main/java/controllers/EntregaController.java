package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import models.Cliente;
import models.Entrega;
import models.Produto;
import dao.ClienteDAO;
import dao.EnderecoDAO;

public class EntregaController {
    private ClienteController clienteController;
    private ProdutoControllers produtoController;
    private List<Entrega> entregas = new ArrayList<>();
    private Scanner scanner;
    ClienteDAO clienteDAO = new ClienteDAO();
    EnderecoDAO enderecoDAO = new EnderecoDAO();

    public EntregaController(Scanner scanner, ClienteController clienteController, ProdutoControllers produtoController) {
        this.scanner = scanner;
        this.clienteController = clienteController;
        this.produtoController = produtoController;
    }
    

    // CADASTRO DE ENTREGA
    public void cadastrarEntrega() {
        List<Cliente> clientes = clienteController.getClientes();
        List<Produto> produtos = produtoController.getProdutos();
         List<Cliente> clientesBanco = clienteDAO.buscarClientes();

        // segurança para caso nao tenha clientes.
        if (clientes.isEmpty() || produtos.isEmpty()) {
            System.out.println("Aviso: Cadastre ao menos um cliente e um produto primeiro");
            return;
        }
        System.out.print("=== Cadastrando nova Entrega ===\n");

        // listar os clientes
        System.out.println("Clientes disponíveis:");
        int contC = 1;
        for (Cliente c : clientesBanco) {
            System.out.println(contC + "- Nome: " + c.getNome() + " | Doc: " + c.getDocumento());
            contC++;
        }

        // pegar o Remetente
        System.out.println("Selecione o Remetente: ");
        int indexRemetente = scanner.nextInt();
        scanner.nextLine();
        Cliente remetente = clientesBanco.get(indexRemetente - 1);

        // pegar destinatario
        System.out.println("Selecione o Destinatário: ");
        int indexDestinatario = scanner.nextInt();
        scanner.nextLine();
        if (indexDestinatario == indexRemetente) {
            System.out.println("O destinatário não pode ser o remetente.");
        }
        Cliente destinatario = clientesBanco.get(indexDestinatario - 1);

        // listando produtos
        int contP = 1;
        for (Produto p : produtos) {
            System.out.println(contP + " - " + p.getNome());
            contP++;
        }

        //pegando o produto
        System.out.print("Selecione o Produto (Digite o número correspondente): ");
        int indexProduto = scanner.nextInt();
        scanner.nextLine();
        Produto produtoEscolhido = produtos.get(indexProduto -1);
        
        // restante das informações
        System.out.print("Status atual do pedido: ");
        String status = scanner.nextLine();

        System.out.print("Valor do Frete: R$");
        double valorFrete = scanner.nextDouble();

        System.out.println("Número de rastreio: ");
        String numRastreio = scanner.nextLine();

        System.out.println("Data de emissão: ");
        String dataEmissao = scanner.nextLine();

        Entrega novaEntrega = new Entrega();

        // salvar entrega
        EnderecoDAO enderecoDAO = new EnderecoDAO(indexRemetente, indexDestinatario, );
        int idEntrega = enderecoDAO.salvarEndereco(novaEntrega); 
        
    }

    public List<Entrega> getEntregas() {
        return entregas;
    }
}