package models;

public class Produto {
    private int id;
    private int clienteId; 
    private String descricao;
    private double valorPadrao;

    public Produto() {}

    public Produto(int clienteId, String descricao, double valorPadrao) {
        this.clienteId = clienteId;
        this.descricao = descricao;
        this.valorPadrao = valorPadrao;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getClienteId() { return clienteId; }
    public void setClienteId(int clienteId) { this.clienteId = clienteId; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public double getValorPadrao() { return valorPadrao; }
    public void setValorPadrao(double valorPadrao) { this.valorPadrao = valorPadrao; }
}