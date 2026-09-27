package src.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import src.models.Produto;
import src.validators.ProdutoValidador;

public class ProdutoService {
    private List<Produto> produtos = new ArrayList<>();
    private ProdutoValidador validador = new ProdutoValidador();
    private Scanner scanner;

    public ProdutoService(Scanner scanner) {
        this.scanner = scanner;
    }

    public void cadastrarProduto() {
        System.out.println("=== Cadastro de Produto ===\n");
        System.out.print("Nome do item: ");
        String produtoNome = scanner.nextLine();
        System.out.print("Peso (Kg): ");
        String produtoPeso = scanner.nextLine();
        System.out.print("Preço: R$");
        String produtoPreco = scanner.nextLine();

        // criando o produto
        Produto novoProduto = new Produto(produtoNome, produtoPeso, produtoPreco);
        
        // Validando
        List<String> erros = validador.validar(novoProduto);

        // mostra os erros
        if (!erros.isEmpty()) {
            erros.forEach(System.out::println);
            return;
        }
        
        // apenas adiciona se nao tiver erros
        produtos.add(novoProduto);
        System.out.println("Produto cadastrado com sucesso!\n");
    }

    public List<Produto> getProdutos() {
        return produtos;
    }
}