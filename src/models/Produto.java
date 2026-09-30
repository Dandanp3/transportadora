package src.models;
public class Produto {
    private int produtoId;
    private String nome;
    private String peso;
    private String preco;

    public Produto(String nome, String peso, String preco) {
        this.nome = nome;
        this.peso = peso;
        this.preco = preco;
    }

    public Produto(){}

    // getters e seters
    public int getProdutoID() {return produtoId;}
    public void setProdutoID(int produtoId) {this.produtoId = produtoId;}

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getPeso() { return peso; }
    public void setPeso(String peso) { this.peso = peso; }

    public String getPreco() { return preco; }
    public void setPreco(String preco) { this.preco = preco; }
}
