import java.util.ArrayList;

public class Entrega {
    private Cliente cliente;
    private ArrayList<Produto> produtos;
    private String status;
    private double valorFrete;

    public Entrega(Cliente cliente, ArrayList<Produto> produtos, String status, double valorFrete) {
        this.cliente = cliente;
        this.produtos = produtos;
        this.status = status;
        this.valorFrete = valorFrete;
    }

    //getters e setters

    // devolve o cliente
    public Cliente getCliente() {
        return cliente;
    }
    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    // devolve os produtos
    public ArrayList<Produto> getProdutos() {
        return produtos;
    }
    public void setProdutos(ArrayList<Produto> produtos) {
        this.produtos = produtos;
    }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public double getValorFrete() { return valorFrete; }
    public void setValorFrete(double valorFrete) { this.valorFrete = valorFrete; }
    
}

