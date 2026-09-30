package src.models;

import java.util.ArrayList;

public class Entrega {
    private Cliente cliente;
    private ArrayList<Entrega> destino;
    private String status;
    private double valorFrete;
    private String dataSaida;
    private String numRastreio;

    public Entrega(Cliente cliente, ArrayList<Entrega> destino, String status, double valorFrete, String dataSaida, String numRastreio) {
        this.cliente = cliente;
        this.destino = destino;
        this.status = status;
        this.valorFrete = valorFrete;
        this.dataSaida = dataSaida;
    }
    public Entrega(){}


    // devolve o cliente
    public Cliente getCliente() {return cliente;}
    public void setCliente(Cliente cliente) {this.cliente = cliente;}

    public ArrayList<Entrega> getDestino(){return destino;}
    public void setDestino( ArrayList<Entrega> destino ){this.destino = destino;}

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getValorFrete() { return valorFrete; }
    public void setValorFrete(double valorFrete) { this.valorFrete = valorFrete; }

    public String getDataSaida() {return dataSaida;}
    public void setDataSaida( String dataSaida ){this.dataSaida = dataSaida;}

    public String getNumRastreio() {return numRastreio;}
    public void setNumRasrtreio( String numRastreio) {this.numRastreio = numRastreio;}
    
}

