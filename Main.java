import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Sistema sistema = new Sistema(scanner);
        
        int opcao = 0;
        System.out.println("=== BEM-VINDO AO SISTEMA ===");

        do {
            System.out.println("O que você deseja fazer?");
            System.out.println("1. Cadastrar Cliente.");
            System.out.println("2. Cadastrar Produto.");
            System.out.println("3. Criar nova entrega.");
            System.out.println("4. Listar relatório / Resumo.");
            System.out.println("0. Sair.");
            System.out.print("Sua escolha: ");

            opcao = scanner.nextInt();
            scanner.nextLine();
            
            switch (opcao) {
                
                case 1:
                    sistema.cadastrarCliente(); //
                    break;
                case 2:
                   sistema.cadastrarProduto();
                   break;
                case 3:
                    sistema.cadastrarEntrega();
                    break;
                case 4:
                    sistema.listarRelatorio();
                    break;

            }
            
        } while (opcao != 0);
        scanner.close();

    }
}