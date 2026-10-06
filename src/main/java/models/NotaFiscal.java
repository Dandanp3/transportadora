package models;

public class NotaFiscal {
    private int id;
    private int entregaId;
    private String numeroNf;
    private String chaveAcesso;
    private double valorMercadoria;

    public NotaFiscal() {}

    public NotaFiscal(int entregaId, String numeroNf, String chaveAcesso, double valorMercadoria) {
        this.entregaId = entregaId;
        this.numeroNf = numeroNf;
        this.chaveAcesso = chaveAcesso;
        this.valorMercadoria = valorMercadoria;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getEntregaId() { return entregaId; }
    public void setEntregaId(int entregaId) { this.entregaId = entregaId; }

    public String getNumeroNf() { return numeroNf; }
    public void setNumeroNf(String numeroNf) { this.numeroNf = numeroNf; }

    public String getChaveAcesso() { return chaveAcesso; }
    public void setChaveAcesso(String chaveAcesso) { this.chaveAcesso = chaveAcesso; }

    public double getValorMercadoria() { return valorMercadoria; }
    public void setValorMercadoria(double valorMercadoria) { this.valorMercadoria = valorMercadoria; }
}