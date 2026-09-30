package src.models;

import java.util.ArrayList;

public class ItemEntrega {
    private Entrega entrega;
    private int itemEntregaId;
    private Produto produto;
    private int quantidade;



    public ItemEntrega(Entrega entrega, Produto produto, int quantidade) {
    this.produto = produto;
    this.entrega = entrega;
    this.quantidade = quantidade;
    }

    public int getItemProdutoID() {return itemEntregaId;}
    public void setItemProdutoID(int itemEntregaId) {this.itemEntregaId = itemEntregaId;}

    public Produto getProdutos() {return produto;}
    public void setProdutos(Produto produto) {this.produto = produto;}

}

