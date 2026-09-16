import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Sistema {
    private List<Cliente> clientes = new ArrayList<>();
    private List<Produto> produtos = new ArrayList<>();
    private List<Entrega> entregas = new ArrayList<>();
    private Scanner scanner;

    // Construtor
    public Sistema(Scanner scanner) {
        this.scanner = scanner;
    }
    
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

    public void cadastrarEntrega() {
        // segurança para caso nao tenha clientes.
        if (clientes.isEmpty() || produtos.isEmpty()) {
            System.out.println("Aviso: Cadastre ao menos um cliente e um produto primeiro");
        }
        System.out.print("=== Cadastrando nova Entrega ===\n");

        // pescando os clientes ja cadastrados
        System.out.println("Selecione o Cliente (Digite o número correspondente): ");
        for (int i = 0; i < clientes.size(); i++) {
            // imprime os clientes
            System.out.println(i + " - " + clientes.get(i).getNome());
        }
        int indexCliente = scanner.nextInt();
        scanner.nextLine();
        // pegando o cliente
        Cliente clienteEscolhido = clientes.get(indexCliente);


        // pescando os produtos
        System.out.println("Selecione o Produto (Digite o número correspondente): ");
        for (int i = 0; i < produtos.size(); i++) {
            // imprimindo os produtos
            System.out.println(i + " - " + produtos.get(i).getNome());
        }
        int indexProduto = scanner.nextInt();
        scanner.nextLine();
        //pegando o produto
        Produto produtoEscolhido = produtos.get(indexProduto);

        System.out.print("Status atual do pedido: ");
        String status = scanner.nextLine();

        System.out.print("Valor do Frete: R$");
        double valorFrete = scanner.nextDouble();

        // criando uma caixa de produtos
        ArrayList<Produto> pacoteDeProdutos = new ArrayList<>();
        pacoteDeProdutos.add(produtoEscolhido);

        Entrega novaEntrega = new Entrega(clienteEscolhido, pacoteDeProdutos, status, valorFrete);
        entregas.add(novaEntrega);
    }


}
