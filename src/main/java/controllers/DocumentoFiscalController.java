package controllers;

import java.util.Scanner;
import dao.DocumentoFiscalDAO;
import models.Cliente;
import models.NotaFiscal;
import utils.GeradorNfeUtils;

public class DocumentoFiscalController {
    private Scanner scanner;

    public DocumentoFiscalController(Scanner scanner) {
        this.scanner = scanner;
    }

    public void cadastrarDocumentoFiscal(int entregaId, Cliente remetente, String ufOrigem, String dataEmissao) {
        System.out.println("==== Documento Fiscal ====");
        System.out.println("Valor da mercadoria: R$");
        Double valorMercadoria = scanner.nextDouble();
        scanner.nextLine();

        NotaFiscal documento = new NotaFiscal();
        documento.setEntregaId(entregaId);
        documento.setValorMercadoria(valorMercadoria);

        // regra de negocio para PF ou PJ
        if (remetente.getDocumento().length() > 11) { // se for > 11 é CNPJ
            System.out.print("Digite o número da nota fiscal (ex: 1542): ");
            String numNF = scanner.nextLine();

            // passando para o gerador
            String chaveGerada = GeradorNfeUtils.geradorNFE(remetente.getDocumento(), ufOrigem, dataEmissao);
            System.out.println("Chave de acesso SEFAZ gerada automaticamente: " + chaveGerada);

            documento.setNumeroNf(numNF);
            documento.setChaveAcesso(chaveGerada);
        }  else {
            System.out.println("Remetente PF identificado. Gerando Declaração de conteúdo sem chave de acesso.");
            documento.setNumeroNf("DEC-CONT");
            documento.setChaveAcesso(null);
        }

        DocumentoFiscalDAO dao = new DocumentoFiscalDAO();
        dao.salvarDocumento(documento);
        
    }
}
