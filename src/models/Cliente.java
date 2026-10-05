package src.models;

public class Cliente {
    private int id;
    private String tipoPessoa;
    private String nome;
    private String nomeFantasia;
    private String documento;
    private String email;
    private String telefone;

    public Cliente() {}

    public Cliente(String tipoPessoa, String nome, String nomeFantasia, String documento, String email, String telefone) {
        this.tipoPessoa = tipoPessoa;
        this.nome = nome;
        this.nomeFantasia = nomeFantasia;
        this.documento = documento;
        this.email = email;
        this.telefone = telefone;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTipoPessoa() { return tipoPessoa; }
    public void setTipoPessoa(String tipoPessoa) { this.tipoPessoa = tipoPessoa; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getNomeFantasia() { return nomeFantasia; }
    public void setNomeFantasia(String nomeFantasia) { this.nomeFantasia = nomeFantasia; }

    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
}