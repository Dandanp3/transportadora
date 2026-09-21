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
}
