public class Endereco {
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private String cidade;
    private String uf;
    private String cep;

    //construtor
    public Endereco(String logradouro, String numero, String complemento, String bairro, String cidade, String uf, String cep) {
        this.logradouro = logradouro;
        this.numero = numero;
        this.complemento = complemento;
        this.bairro = bairro;
        this.cidade = cidade;
        this.uf = uf;
        this.cep = cep;
    } 

    //getters e setters
    public String getLogradouro() { return logradouro; }
    public void setLogradouro(String logradouro) { this.logradouro = logradouro; }

    public String getNumero() { return numero; }
    public void setNumero(String numero ) { this.numero = numero; }

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
