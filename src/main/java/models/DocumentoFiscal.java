package models;

public class DocumentoFiscal {
    private int id;
    private int entregaId;
    private String tipoDocumento; 
    private String numeroDocumento;
    private String chaveAcesso;
    private double valorTotalDeclarado;

    public DocumentoFiscal() {}

    public DocumentoFiscal(int entregaId, String tipoDocumento, String numeroDocumento, String chaveAcesso, double valorTotalDeclarado) {
        this.entregaId = entregaId;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.chaveAcesso = chaveAcesso;
        this.valorTotalDeclarado = valorTotalDeclarado;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getEntregaId() { return entregaId; }
    public void setEntregaId(int entregaId) { this.entregaId = entregaId; }

    public String getTipoDocumento() { return tipoDocumento; }
    public void setTipoDocumento(String tipoDocumento) { this.tipoDocumento = tipoDocumento; }

    public String getNumeroDocumento() { return numeroDocumento; }
    public void setNumeroDocumento(String numeroDocumento) { this.numeroDocumento = numeroDocumento; }

    public String getChaveAcesso() { return chaveAcesso; }
    public void setChaveAcesso(String chaveAcesso) { this.chaveAcesso = chaveAcesso; }

    public double getValorTotalDeclarado() { return valorTotalDeclarado; }
    public void setValorTotalDeclarado(double valorTotalDeclarado) { this.valorTotalDeclarado = valorTotalDeclarado; }
}