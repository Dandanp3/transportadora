package controllers;

import java.util.Scanner;

public class DocumentoFiscalController {
    private Scanner scanner;

    public DocumentoFiscalController(Scanner scanner) {
        this.scanner = scanner;
    }

    public void cadastrarDocumentoFiscal() {
        System.out.println("==== Documento Fiscal ====");
        System.out.println("Valor da mercadoria: R$");
        Double valorMercadoria = scanner.nextDouble();

        
    }
}
