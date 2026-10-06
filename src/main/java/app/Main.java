package app;
import java.util.Scanner;

import controllers.ClienteController;
import controllers.EntregaController;
import controllers.ProdutoControllers;
import controllers.RelatorioControllers;

import db.ConnectionFactory;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ClienteController clienteService = new ClienteController(scanner);
        ProdutoControllers produtoService = new ProdutoControllers(scanner);
        EntregaController entregaService = new EntregaController(scanner, clienteService, produtoService);
        RelatorioControllers relatorioService = new RelatorioControllers(entregaService);

        ConnectionFactory.conectar();
        
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