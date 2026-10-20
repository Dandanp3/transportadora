package models;

public class ItemDocumento {
    private int id;
    private int documentoFiscalId;
    private Produto produto; 
    private int quantidade;
    private double valorUnitario;

    public ItemDocumento() {}

    public ItemDocumento(int documentoFiscalId, Produto produto, int quantidade, double valorUnitario) {
        this.documentoFiscalId = documentoFiscalId;
        this.produto = produto;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getDocumentoFiscalId() { return documentoFiscalId; }
    public void setDocumentoFiscalId(int documentoFiscalId) { this.documentoFiscalId = documentoFiscalId; }

    public Produto getProduto() { return produto; }
    public void setProduto(Produto produto) { this.produto = produto; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public double getValorUnitario() { return valorUnitario; }
    public void setValorUnitario(double valorUnitario) { this.valorUnitario = valorUnitario; }
}