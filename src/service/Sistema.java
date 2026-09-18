package src.service;
import java.util.Scanner;

import src.models.Cliente;
import src.models.Endereco;
import src.models.Entrega;
import src.models.Produto;

import java.util.ArrayList;
import java.util.List;

public class Sistema {
    private List<Cliente> clientes = new ArrayList<>();
    private List<Produto> produtos = new ArrayList<>();
    private List<Entrega> entregas = new ArrayList<>();
    private Scanner scanner;

    public Sistema(Scanner scanner) {
        this.scanner = scanner;
    }
    
    // CADASTRO DE CLIENTE
    public void cadastrarCliente() {
        // cliente cadastro
        System.out.println("=== Cadastro de Cliente ===\n");
        System.out.print("Nome do Cliente: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Número/Phone: (+55) ");
        String telefone = scanner.nextLine();

        // endereço
        System.out.println("=== Endereço ===\n");
        System.out.print("Logradouro: ");
        String logradouro = scanner.nextLine();
        System.out.print("Número: ");
        String numero = scanner.nextLine();
        System.out.print("Complemento: ");
        String complemento = scanner.nextLine();
        System.out.print("Bairro: ");
        String bairro = scanner.nextLine();
        System.out.print("Cidade: ");
        String cidade = scanner.nextLine();
        System.out.print("UF: ");
        String uf = scanner.nextLine();
        System.out.print("CEP: ");
        String cep = scanner.nextLine();

        // Criando o cliente
        Endereco enderecoCliente = new Endereco(logradouro, numero, complemento, bairro, cidade, uf, cep);
        Cliente novoCliente = new Cliente(nome, cpf, telefone, enderecoCliente);
        clientes.add(novoCliente);

        System.out.println("Cliente cadastrado!");

    }

    // CADASTRO DE PRODUTO
    public void cadastrarProduto() {
        System.out.println("=== Cadastro de Produto ===\n");
        System.out.print("Nome do item: ");
        String produtoNome = scanner.nextLine();
        System.out.print("Peso (Kg): ");
        double produtoPeso = scanner.nextDouble();
        System.out.print("Preço: R$");
        double produtoPreco = scanner.nextDouble();

        // Criando o produto
        Produto novoProduto = new Produto(produtoNome, produtoPeso, produtoPreco);
        produtos.add(novoProduto);

        System.out.println("Produto cadastrado!\n");

    }

    // CADASTRO DE ENTREGA
    public void cadastrarEntrega() {
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


    // RELATÓRIO
    public void listarRelatorio() {
        System.out.println("\nRelatório geral de Entregas.");

        // trava de segurança
        if (entregas.isEmpty()) {
            System.out.println("Nenhuma entrega cadastrada.");
            return;
        }

        // passa por todas as entregadas ja cadastradas
        
        int contE = 0;
        for (Entrega e : entregas) {
            //Entrega entregaAtual = entregas.get(i);
            
            System.out.println("\n--- Pedido #" + (contE) + "---");
            System.out.println("Cliente: "+ e.getCliente().getNome());
            System.out.println("CPF: "+ e.getCliente().getCPF());

            // metodo de endereço
            System.out.println("Destino: "+ e.getCliente().getEndereco().obterEnderecoCompleto());

            System.out.println("Status de envio: "+ e.getStatus());
            System.out.println("Frete: R$"+ e.getValorFrete());

            // mini loop para varios itens da mesma pessoa
            System.out.println("Itens do pacote:");
            for (Produto p : e.getProdutos()) {
                System.out.println("  - " + p.getNome() + " (Peso: " + p.getPeso() + "kg | Preço: R$" + p.getPreco() + ")");
            }
        }
    }


}
