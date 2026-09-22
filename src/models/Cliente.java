package src.models;

public class Cliente {
    private String nome;
    private String cpf;
    private String telefone;
    private Endereco endereco; 

    public Cliente(String nome, String cpf, String telefone, Endereco endereco) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.endereco = endereco;
    }

    public Cliente() {}

    //getters e setters 
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCPF() {return cpf; }
    public void setCPF(String cpf) { this.cpf = cpf; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    public Endereco getEndereco() { return endereco; }
    public void setEndereco(Endereco endereco ) {this.endereco = endereco; }


}
