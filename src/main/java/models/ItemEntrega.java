package models;

public class ItemEntrega {
    private int id;
    private int entregaId;
    private String codigoBarras;
    private double pesoKg;
    private double alturaCm;
    private double larguraCm;
    private double comprimentoCm;

    public ItemEntrega() {}

    public ItemEntrega(int entregaId, String codigoBarras, double pesoKg, double alturaCm, double larguraCm, double comprimentoCm) {
        this.entregaId = entregaId;
        this.codigoBarras = codigoBarras;
        this.pesoKg = pesoKg;
        this.alturaCm = alturaCm;
        this.larguraCm = larguraCm;
        this.comprimentoCm = comprimentoCm;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getEntregaId() { return entregaId; }
    public void setEntregaId(int entregaId) { this.entregaId = entregaId; }

    public String getCodigoBarras() { return codigoBarras; }
    public void setCodigoBarras(String codigoBarras) { this.codigoBarras = codigoBarras; }

    public double getPesoKg() { return pesoKg; }
    public void setPesoKg(double pesoKg) { this.pesoKg = pesoKg; }

    public double getAlturaCm() { return alturaCm; }
    public void setAlturaCm(double alturaCm) { this.alturaCm = alturaCm; }

    public double getLarguraCm() { return larguraCm; }
    public void setLarguraCm(double larguraCm) { this.larguraCm = larguraCm; }

    public double getComprimentoCm() { return comprimentoCm; }
    public void setComprimentoCm(Double comprimentoCm) { this.comprimentoCm = comprimentoCm; }
}