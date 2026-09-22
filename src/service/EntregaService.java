package src.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import src.models.Cliente;
import src.models.Entrega;
import src.models.Produto;

public class EntregaService {
    private ClienteService clienteService;
    private ProdutoService produtoService;
    private List<Entrega> entregas = new ArrayList<>();
    private Scanner scanner;

    public EntregaService(Scanner scanner, ClienteService clienteService, ProdutoService produtoService) {
        this.scanner = scanner;
        this.clienteService = clienteService;
        this.produtoService = produtoService;
    }
    

    // CADASTRO DE ENTREGA
    public void cadastrarEntrega() {
        List<Cliente> clientes = clienteService.getClientes();
        List<Produto> produtos = produtoService.getProdutos();

        // segurança para caso nao tenha clientes.
        if (clientes.isEmpty() || produtos.isEmpty()) {
            System.out.println("Aviso: Cadastre ao menos um cliente e um produto primeiro");
        }
        System.out.print("=== Cadastrando nova Entrega ===\n");

        // lista de clientes
        int contC = 1;
        for (Cliente c : clientes) {
            System.out.println(contC + " - " + c.getNome());
            contC++;
        }
        
        // pega o cliente 
        System.out.print("Selecione o Cliente (Digite o número correspondente): ");
        int indexCliente = scanner.nextInt();
        scanner.nextLine();
        Cliente clienteEscolhido = clientes.get(indexCliente - 1);

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

        // criando uma caixa de produtos
        ArrayList<Produto> pacoteDeProdutos = new ArrayList<>();
        pacoteDeProdutos.add(produtoEscolhido);

        Entrega novaEntrega = new Entrega(clienteEscolhido, pacoteDeProdutos, status, valorFrete);
        entregas.add(novaEntrega);
        System.out.println("Entrega cadastrada\n");
    }

    public List<Entrega> getEntregas() {
        return entregas;
    }
}
