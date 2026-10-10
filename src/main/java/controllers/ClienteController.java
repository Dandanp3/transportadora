package controllers;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

import dao.EnderecoDAO;
import dao.ClienteDAO;
import models.Cliente;
import models.Endereco;
import validators.ClienteValidador;

public class ClienteController {
    private List<Cliente> clientes = new ArrayList<>();
    private ClienteValidador validador = new ClienteValidador();
    private Scanner scanner;

    public ClienteController(Scanner scanner) {
        this.scanner = scanner;
    }

    public void cadastrarCliente() {
        // cliente cadastro
        System.out.println("=== Cadastro de Cliente ===\n");
        
        System.out.print("Nome / Razão social: ");
        String nome = scanner.nextLine();
        
        System.out.print("Nome fantasia (se não houver, aperte Enter): ");
        String nomeFantasia = scanner.nextLine();
        
        System.out.print("CPF / CNPJ: ");
        String documento = scanner.nextLine();
        
        String docLimpo = documento.replaceAll("[^0-9]", "");
        // Se tiver mais de 11 digis, eh PJ
        String tipoPessoa = docLimpo.length() > 11 ? "J" : "F";
        
        System.out.print("Email: ");
        String email = scanner.nextLine();
        
        System.out.print("Número/Phone: (+55) ");
        String telefone = scanner.nextLine();

        // endereço
        System.out.println("\n=== Endereço ===");
        System.out.print("Tipo do endereço (ex: Residencial, Comercial): ");
        String tipoEndereco = scanner.nextLine();
        System.out.print("Logradouro: ");
        String logradouro = scanner.nextLine();
        System.out.print("Número: ");
        int numero = Integer.parseInt(scanner.nextLine());
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

        Cliente novoCliente = new Cliente(tipoPessoa, nome, nomeFantasia, documento, email, telefone);

        List<String> erros = validador.validar(novoCliente);
        if (!erros.isEmpty()) {
            erros.forEach(System.out::println);
            return;
        }
        
        // salva o cliente no banco e pega o id
        ClienteDAO clienteDAO = new ClienteDAO();
        int idCliente = clienteDAO.salvarCliente(novoCliente);

        if (idCliente != -1) {
            // guarda o id no objeto para uso futuro
            novoCliente.setId(idCliente);
            // cria o endereço com o id coletado
            Endereco enderecoCliente = new Endereco(idCliente, tipoEndereco, cep, logradouro, numero, complemento, bairro, cidade, uf);
            EnderecoDAO enderecoDAO = new EnderecoDAO();
            enderecoDAO.salvarEndereco(enderecoCliente);

            //clientes.add(novoCliente); -- lista memoria para usar depois

            System.out.println("\nCliente e Endereço cadastrados com sucesso! Tipo definido como: " + tipoPessoa);
        } else {
            System.out.println("Falha ao salvar o cliente.");
        }
    }

    public List<Cliente> getClientes() {
        return clientes;
    }
}