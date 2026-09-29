create SCHEMA transportadora;
CREATE TABLE transportadora.endereco(
    id SERIAL PRIMARY KEY,
    logradouro VARCHAR(25) NOT NULL,
    numero INT,
    complemento VARCHAR(20),
    bairro VARCHAR(20) NOT NULL,
    cidade VARCHAR(20) NOT NULL,
    uf VARCHAR(2) NOT NULL,
    cep VARCHAR(10) NOT NULL
);

CREATE TABLE transportadora.cliente(
    nome VARCHAR(50) NOT NULL,
    cpf VARCHAR(11) NOT NULL PRIMARY KEY,
    telefone INT NOT NULL, 
	endereco_id INT NOT NULL,
	CONSTRAINT fk_entrega FOREIGN KEY (endereco_id) REFERENCES transportadora.endereco(id)
);

CREATE TABLE transportadora.produto(
    produto_id SERIAL PRIMARY KEY,
    peso FLOAT NOT NULL,
    preco FLOAT NOT NULL
);

CREATE TABLE transportadora.entrega(
    cliente_cpf VARCHAR(11) NOT NULL,
    CONSTRAINT fk_cliente FOREIGN KEY (cliente_cpf) REFERENCES transportadora.cliente(cpf),
    id_produto INT NOT NULL,
    CONSTRAINT fk_entrega FOREIGN KEY(id_produto) REFERENCES transportadora.produto(produto_id)
);

DROP TABLE entrega, produto, cliente, endereco;