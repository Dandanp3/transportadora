package src.service;

import java.util.List;

import src.utils.CpfUtils;

import src.models.Entrega;
import src.models.Produto;

public class RelatorioService {
    private EntregaService entregaService;

    public RelatorioService(EntregaService entregaService) {
        this.entregaService = entregaService;
    }

    // RELATÓRIO
    public void listarRelatorio() {
        System.out.println("\nRelatório geral de Entregas.");

        List<Entrega> entregas = entregaService.getEntregas();

        // trava de segurança
        if (entregas.isEmpty()) {
            System.out.println("Nenhuma entrega cadastrada.");
            return;
        }

        // passa por todas as entregas 
        int contE = 0;
        for (Entrega e : entregas) {
            System.out.println("\n--- Pedido #" + (contE) + "---");
            System.out.println("Cliente: "+ e.getCliente().getNome());
            System.out.println("CPF: "+ CpfUtils.formatarCPF(e.getCliente().getCPF()));

            // metodo de endereço
            System.out.println("Destino: "+ e.getCliente().getEndereco().obterEnderecoCompleto());

            System.out.println("Status de envio: "+ e.getStatus());
            System.out.println("Frete: R$"+ e.getValorFrete());

            // mini loop para varios itens da mesma pessoa
            System.out.println("Itens do pacote:");
            for (Produto p : e.getProdutos()) {
                System.out.println(" - " + p.getNome() + " (Peso: " + p.getPeso() + "kg | Preço: R$" + p.getPreco() + ")");
            }
        }
    }
    
}
