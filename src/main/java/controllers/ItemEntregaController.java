package controllers;

import java.util.Scanner;
import dao.ItemEntregaDAO;
import models.ItemEntrega;

import utils.CodBarrasUtils;

public class ItemEntregaController {
    private Scanner scanner;

    public ItemEntregaController(Scanner scanner) {
        this.scanner = scanner;
    }

    public ItemEntrega cadastrarItem(int entregaId) {
        System.out.println("==== Cadastrando Item da entrega ====");
        System.out.println("Peso (kg): ");
        Double peso = scanner.nextDouble();
        System.out.println("Altura (cm): ");
        Double altura = scanner.nextDouble();
        System.out.println("Largura (cm): ");
        Double largura = scanner.nextDouble();
        System.out.println("Comprimento (cm): ");
        Double comprimento = scanner.nextDouble();
        scanner.nextLine();

        ItemEntrega novoItem = new ItemEntrega(entregaId, CodBarrasUtils.gerarCodBarras(), peso, altura, largura, comprimento);
        
        ItemEntregaDAO itemDAO = new ItemEntregaDAO();
        itemDAO.salvarItemEntrega(novoItem);
        return novoItem;
    }
}
