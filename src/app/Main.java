package src.app;
import java.util.Scanner;
import src.service.ClienteService;
import src.service.EntregaService;
import src.service.ProdutoService;
import src.service.RelatorioService;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ClienteService clienteService = new ClienteService(scanner);
        ProdutoService produtoService = new ProdutoService(scanner);
        EntregaService entregaService = new EntregaService(scanner, clienteService, produtoService);
        RelatorioService relatorioService = new RelatorioService(entregaService);
        
        
        int opcao = 0;
        System.out.println("=== BEM-VINDO AO SISTEMA ===");

        do {
            System.out.println("\nO que você deseja fazer?");
            System.out.println("1. Cadastrar Cliente.");
            System.out.println("2. Cadastrar Produto.");
            System.out.println("3. Criar nova entrega.");
            System.out.println("4. Listar relatório / Resumo.");
            System.out.println("0. Sair.");
            System.out.print("Sua escolha: ");

            opcao = scanner.nextInt();
            scanner.nextLine();
            
            switch (opcao) {
                
                case 1: clienteService.cadastrarCliente(); 
                    break;
                case 2: produtoService.cadastrarProduto();
                   break;
                case 3: entregaService.cadastrarEntrega();
                    break;
                case 4: relatorioService.listarRelatorio(); 
                    break;

                default: System.out.println("Opção inválida! Tente novamente.");
                    break;
            }
            
        } while (opcao != 0);
        scanner.close();

    }
}