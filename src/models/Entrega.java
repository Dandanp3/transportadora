package src.models;

public class Entrega {
    private int id;
    private Cliente remetente;
    private Cliente destinatario;
    private Endereco enderecoOrigem;
    private Endereco enderecoDestino;
    private String numRastreio;
    private String statusEntrega;
    private double valorFrete;
    private String dataEmissao;

    public Entrega() {}

    public Entrega(Cliente remetente, Cliente destinatario, Endereco enderecoOrigem, Endereco enderecoDestino, String numRastreio, String statusEntrega, double valorFrete, String dataEmissao) {
        this.remetente = remetente;
        this.destinatario = destinatario;
        this.enderecoOrigem = enderecoOrigem;
        this.enderecoDestino = enderecoDestino;
        this.numRastreio = numRastreio;
        this.statusEntrega = statusEntrega;
        this.valorFrete = valorFrete;
        this.dataEmissao = dataEmissao;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Cliente getRemetente() { return remetente; }
    public void setRemetente(Cliente remetente) { this.remetente = remetente; }

    public Cliente getDestinatario() { return destinatario; }
    public void setDestinatario(Cliente destinatario) { this.destinatario = destinatario; }

    public Endereco getEnderecoOrigem() { return enderecoOrigem; }
    public void setEnderecoOrigem(Endereco enderecoOrigem) { this.enderecoOrigem = enderecoOrigem; }

    public Endereco getEnderecoDestino() { return enderecoDestino; }
    public void setEnderecoDestino(Endereco enderecoDestino) { this.enderecoDestino = enderecoDestino; }

    public String getNumRastreio() { return numRastreio; }
    public void setNumRastreio(String numRastreio) { this.numRastreio = numRastreio; }

    public String getStatusEntrega() { return statusEntrega; }
    public void setStatusEntrega(String statusEntrega) { this.statusEntrega = statusEntrega; }

    public double getValorFrete() { return valorFrete; }
    public void setValorFrete(double valorFrete) { this.valorFrete = valorFrete; }

    public String getDataEmissao() { return dataEmissao; }
    public void setDataEmissao(String dataEmissao) { this.dataEmissao = dataEmissao; }
}