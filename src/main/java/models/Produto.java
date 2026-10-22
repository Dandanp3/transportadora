package models;

public class Produto {
    private int id;
    private String descricao;
    private double valorPadrao;
    private double pesoPadrao; 

    public Produto() {}

    public Produto(String descricao, double valorPadrao, double pesoPadrao) {
        this.descricao = descricao;
        this.valorPadrao = valorPadrao;
        this.pesoPadrao = pesoPadrao;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public double getValorPadrao() { return valorPadrao; }
    public void setValorPadrao(double valorPadrao) { this.valorPadrao = valorPadrao; }

    public double getPesoPadrao() { return pesoPadrao; }
    public void setPesoPadrao(double pesoPadrao) { this.pesoPadrao = pesoPadrao; }
}