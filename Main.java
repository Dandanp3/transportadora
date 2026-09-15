import java.util.Scanner;
import java .util.ArrayList;
import java.util.List;;

public class Main {
    public static void main(String[] args) {
        
        // initial setup
        Scanner scanner = new Scanner(System.in);
        boolean loop = true;
        List<Cliente> clientes = new ArrayList<>();
        List<Produto> produtos = new ArrayList<>();
        List<Entrega> entregas = new ArrayList<>();

        int opcao = 0;
        System.out.println("=== BEM-VINDO AO SISTEMA ===");

        do {
            System.out.println("O que você deseja fazer?");
            System.out.print("1. Cadastrar Cliente.");
            System.out.println("2. Cadastrar Produto.");
            System.out.println("3. Criar nova entrega.");
            System.out.println("4. Listar relatório / Resumo.");
            System.out.println("0. Sair.");
            System.out.print("Sua escolha: ");

            opcao = scanner.nextInt();
            scanner.nextLine();
            
            switch (opcao) {
                
                case 1:
                    // client register


            }
            
        } while (opcao != 0);
        scanner.close();


    }
}