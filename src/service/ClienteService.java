package src.service;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

import src.models.Cliente;
import src.models.Endereco;
import src.utils.CpfUtils;
import src.utils.FieldUtils;

public class ClienteService {
    FieldUtils campos = new FieldUtils();
    private List<Cliente> clientes = new ArrayList<>();
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

        campos.verificarNotBlank(nome, "Campo Nome é obrigatório.")
                    .verificarNotBlank(cpf, "Campo CPF é obrigatório.")
                    .verificarNotBlank(telefone, "Campo Telefone é obrigatório.")
                    .verificarNotBlank(logradouro, "Campo Logradouro é obrigatório.")
                    .verificarNotBlank(numero, "Campo Número é obrigatório.")
                    .verificarNotBlank(complemento, "Campo Complemento é obrigatório.")
                    .verificarNotBlank(bairro, "Campo Bairro é obrigatório")
                    .verificarNotBlank(cidade, "Campo Cidade é obrigatório.")
                    .verificarNotBlank(uf, "Campo UF é obrigatório.")
                    .verificarNotBlank(cep, "Campo CEP é obrigatório.");

        if (campos.temErros()) {
                campos.getErros().forEach(System.out::println);
                return;
            } else {

                if (!CpfUtils.validarCPF(cpf)) {
                    System.out.println("CPF inserido inválido.");
                    return;
                } else {

                    // Criando o cliente
                    Endereco enderecoCliente = new Endereco(logradouro, numero, complemento, bairro, cidade, uf, cep);
                    Cliente novoCliente = new Cliente(nome, cpf, telefone, enderecoCliente);
                    clientes.add(novoCliente);
                    System.out.println("Cliente cadastrado!");
                }
            }
    }

    public List<Cliente> getClientes() {
        return clientes;
    }
}
