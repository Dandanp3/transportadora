package models;

import java.util.List;

public class ItemEntrega {
    private int id;
    private int entregaId;
    private int produtoId;
    private int quantidade;
    private double valorUnitario;
    private Produto produto;

    // Construtor vazio padrão
    public ItemEntrega() {}

    // Construtor pra salvar no banco (o mesmo que a gente chamou no Servlet)
    public ItemEntrega(int entregaId, int produtoId, int quantidade, double valorUnitario) {
        this.entregaId = entregaId;
        this.produtoId = produtoId;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getEntregaId() { return entregaId; }
    public void setEntregaId(int entregaId) { this.entregaId = entregaId; }

    public int getProdutoId() { return produtoId; }
    public void setProdutoId(int produtoId) { this.produtoId = produtoId; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public double getValorUnitario() { return valorUnitario; }
    public void setValorUnitario(double valorUnitario) { this.valorUnitario = valorUnitario; }

    public Produto getProduto() {
        return produto;
    }
    public void setProduto(Produto produto) {
        this.produto = produto;
    }
}