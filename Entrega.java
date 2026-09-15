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
    
}

