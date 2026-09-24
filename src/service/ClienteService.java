package src.service;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

import src.models.Cliente;
import src.models.Endereco;
import src.validators.ClienteValidador;

public class ClienteService {
    private List<Cliente> clientes = new ArrayList<>();
    private ClienteValidador validador = new ClienteValidador();
    private Scanner scanner;

    public ClienteService(Scanner scanner) {
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

        List<String> erros = validador.validar(novoCliente);

        if (!erros.isEmpty()) {
            erros.forEach(System.out::println);
            return;
        }
        clientes.add(novoCliente);
        System.out.println("Cliente cadastrado!");
    }

    public List<Cliente> getClientes() {
        return clientes;
    }
}
