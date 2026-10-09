package controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


import models.Cliente;
import models.Entrega;
import models.ItemEntrega;
import dao.ClienteDAO;
import dao.EnderecoDAO;
import dao.EntregaDAO;
import models.Endereco;

public class EntregaController {
    private List<Entrega> entregas = new ArrayList<>();
    private Scanner scanner;
    DocumentoFiscalController docController = new DocumentoFiscalController(scanner);
    ItemEntregaController itemController = new ItemEntregaController(scanner);
    ClienteDAO clienteDAO = new ClienteDAO();
    EnderecoDAO enderecoDAO = new EnderecoDAO();

    public EntregaController(Scanner scanner) {
        this.scanner = scanner;
    }
    
    // CADASTRO DE ENTREGA
    public void cadastrarEntrega() {
        List<Cliente> clientesBanco = clienteDAO.buscarClientes();

        // Verifica se tem plmns 2 clientes para fazer uma entrega
        if (clientesBanco.size() < 2) {
            System.out.println("Aviso: Você precisa cadastrar ao menos 2 clientes no banco primeiro (um remetente e um destinatário).");
            return;
        }
        System.out.print("=== Cadastrando nova Entrega ===\n");

        // listar os clientes
        System.out.println("Clientes disponíveis:");
        int contC = 1;
        for (Cliente c : clientesBanco) {
            System.out.println(contC + "- Nome: " + c.getNome() + " | Doc: " + c.getDocumento());
            contC++;
        }

        // pegar o Remetente
        System.out.println("Selecione o Remetente: ");
        int indexRemetente = scanner.nextInt();
        scanner.nextLine();
        Cliente remetente = clientesBanco.get(indexRemetente - 1);

        // pegar destinatario
        System.out.println("Selecione o Destinatário: ");
        int indexDestinatario = scanner.nextInt();
        scanner.nextLine();
        if (indexDestinatario == indexRemetente) {
            System.out.println("O destinatário não pode ser o remetente.");
        }
        Cliente destinatario = clientesBanco.get(indexDestinatario - 1);
        
        // restante das informações
        System.out.print("Status atual do pedido: ");
        String status = scanner.nextLine();

        System.out.print("Número de rastreio: ");
        String numRastreio = scanner.nextLine();

        System.out.print("Data de emissão (YYY-MM-DD): ");
        String dataEmissao = scanner.nextLine();

        List<Endereco> enderecosOrigem = enderecoDAO.buscarPorClienteId(remetente.getId());
        if (enderecosOrigem.isEmpty()) {
            System.out.println("O remetente não possui endereços cadastrados!");
            return;
        }

        Endereco origem = enderecosOrigem.get(0);

        // pegando destino
        List<Endereco> enderecosDestino = enderecoDAO.buscarPorClienteId(destinatario.getId());
        if (enderecosDestino.isEmpty()) {
            System.out.println("O destinatário não possui endereços cadastrados!");
            return;
        }

        Endereco destino = enderecosDestino.get(0);

        // criando a entrega
        Entrega novaEntrega = new Entrega(remetente, destinatario, origem, destino, numRastreio, status, 0.0, dataEmissao);

        EntregaDAO entregaDAO = new EntregaDAO();
        int idEntrega = entregaDAO.salvarEntrega(novaEntrega);

        if (idEntrega != -1) {
            novaEntrega.setId(idEntrega); 
            entregas.add(novaEntrega); 

            ItemEntrega itemCadastrado = itemController.cadastrarItem(idEntrega);

            double freteCalculado = utils.CalculadorFreteUtils.calcularFrete(
                itemCadastrado.getPesoKg(), 
                itemCadastrado.getAlturaCm(), 
                itemCadastrado.getLarguraCm(), 
                itemCadastrado.getComprimentoCm()
            );
            System.out.printf("-> Frete calculado por cubagem: R$ %.2f\n", freteCalculado);
            
            // criar um metodo para atualizar frete dps...
            // entregaDAO.atualizarFrete(idEntrega, freteCalculado);

            // 3. CHAMA O DOCUMENTO FISCAL
            DocumentoFiscalController docController = new DocumentoFiscalController(scanner);
            docController.cadastrarDocumentoFiscal(idEntrega, remetente, origem.getUf(), dataEmissao);

            System.out.println("\n✅ Cadastro da Entrega, Item e Documento finalizado com sucesso!");
        } else {
            System.out.println("Falha ao salvar a entrega.");
        }
    }

    public List<Entrega> getEntregas() {
        return entregas;
    }
}