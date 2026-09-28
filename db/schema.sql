CREATE TABLE endereco(
    logradouro VARCHAR(25) NOT NULL,
    numero INT,
    complemento VARCHAR(20),
    bairro VARCHAR(20) NOT NULL,
    cidade VARCHAR(20) NOT NULL,
    uf VARCHAR(2) NOT NULL,
    cep VARCHAR(10) NOT NULL
);

