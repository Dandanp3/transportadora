create SCHEMA transportadora;

CREATE TABLE transportadora.endereco(
    endereco_id SERIAL PRIMARY KEY,
    logradouro VARCHAR(25) NOT NULL,
    numero INT NOT NULL,
    complemento VARCHAR(20),
    bairro VARCHAR(20) NOT NULL,
    cidade VARCHAR(20) NOT NULL,
    uf VARCHAR(2) NOT NULL,
    cep VARCHAR(10) NOT NULL
);

CREATE TABLE transportadora.cliente(
	cliente_id SERIAL PRIMARY KEY NOT NULL,
    nome VARCHAR(50) NOT NULL,
    cpf VARCHAR(11) UNIQUE NOT NULL,
    telefone VARCHAR(11) NOT NULL, 
	endereco_id INT NOT NULL,
	CONSTRAINT fk_entrega FOREIGN KEY (endereco_id) REFERENCES transportadora.endereco(endereco_id)
);

CREATE TABLE transportadora.produto(
    produto_id SERIAL PRIMARY KEY,
    produto_nome VARCHAR(50) NOT NULL,
    peso FLOAT NOT NULL,
    preco FLOAT NOT NULL
);

CREATE TABLE transportadora.entrega(
	entrega_id SERIAL PRIMARY KEY NOT NULL,
    cliente_id INT NOT NULL,
	num_rastreio VARCHAR(10) UNIQUE NOT NULL,
	destino INT NOT NULL,
	valor_frete FLOAT NOT NULL,
	status_entrega VARCHAR(15) NOT NULL,
    dataSaida DATE NOT NULL,
    CONSTRAINT fk_cliente FOREIGN KEY (cliente_id) REFERENCES transportadora.cliente(cliente_id),
	CONSTRAINT fk_destino FOREIGN KEY (destino) REFERENCES transportadora.endereco(endereco_id)
);

CREATE TABLE transportadora.itemEntrega(
	item_id SERIAL PRIMARY KEY,
	produto_id INT NOT NULL,
    entrega_id INT NOT NULL,
    quantidade INT NOT NULL,
    preco_unitario FLOAT NOT NULL,
	CONSTRAINT fk_produto FOREIGN KEY (produto_id) REFERENCES transportadora.produto(produto_id),
    CONSTRAINT fk_entrega FOREIGN KEY (entrega_id) REFERENCES transportadora.entrega(entrega_id)
);

DROP TABLE transportadora.entrega, transportadora.produto, transportadora.cliente, transportadora.endereco CASCADE;

DROP TABLE transportadora.itemEntrega

TRUNCATE TABLE transportadora.cliente, transportadora.endereco CASCADE
TRUNCATE TABLE transportadora.endereco;

SELECT * FROM transportadora.cliente
SELECT * FROM transportadora.endereco