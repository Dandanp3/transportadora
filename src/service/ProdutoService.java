package src.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import src.models.Produto;

public class ProdutoService {
    private List<Produto> produtos = new ArrayList<>();
    private Scanner scanner;

    public ProdutoService(Scanner scanner) {
        this.scanner = scanner;
    }

    public void cadastrarProduto() {
        System.out.println("=== Cadastro de Produto ===\n");
        System.out.print("Nome do item: ");
        String produtoNome = scanner.nextLine();
        System.out.print("Peso (Kg): ");
        double produtoPeso = scanner.nextDouble();
        System.out.print("Preço: R$");
        double produtoPreco = scanner.nextDouble();

        // Criando o produto
        Produto novoProduto = new Produto(produtoNome, produtoPeso, produtoPreco);
        produtos.add(novoProduto);

        System.out.println("Produto cadastrado!\n");
    }

    public List<Produto> getProdutos() {
        return produtos;
    }
}
