package src.models;
public class Endereco {
    private String logradouro;
    private int numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;
    private int enderecoId;

    //construtor
    public Endereco(String logradouro, int numero, String complemento, String bairro, String cidade, String uf, String cep) {
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
        this.cep = cep;
    } 
    public Endereco(){}

    public int getEnderecoID() {return enderecoId;}
    public void setEnderecoID(int enderecoId) {this.enderecoId = enderecoId;}

    //getters e setters
    public String getLogradouro() { return logradouro; }
    public void setLogradouro(String logradouro) { this.logradouro = logradouro; }

    public int getNumero() { return numero; }
    public void setNumero(int numero ) { this.numero = numero; }

    public String getComplemento() { return complemento; }
    public void setComplemento(String complemento) { this.complemento = complemento; }

    public String getBairro() { return bairro; }
    public void setBairro(String bairro) { this.bairro = bairro; }

    public String getCidade() { return cidade; }
    public void setCidade(String cidade) { this.cidade = cidade; }

    public String getUF() { return uf; }
    public void setUF(String uf) { this.uf = uf; }

    public String getCEP() { return cep; }
    public void setCEP(String cep) { this.cep = cep; }

    // para imprimir futuros relatorios
    public String obterEnderecoCompleto() {
        return logradouro + ", " + numero + " - " + complemento + " | " + bairro + ", " + cidade + "-" + uf + " | CEP: " +  cep;
    }

}
