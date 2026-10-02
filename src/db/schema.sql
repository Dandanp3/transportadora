CREATE SCHEMA transportadora;

CREATE TABLE transportadora.endereco(
    id SERIAL PRIMARY KEY, 
    logradouro VARCHAR(25) NOT NULL,
    numero VARCHAR(10) NOT NULL, 
    complemento VARCHAR(20),
    bairro VARCHAR(20) NOT NULL,
    cidade VARCHAR(20) NOT NULL,
    uf VARCHAR(2) NOT NULL,
    cep VARCHAR(10) NOT NULL
);

CREATE TABLE transportadora.cliente(
    id SERIAL PRIMARY KEY, 
    nome VARCHAR(50) NOT NULL,
    cpf VARCHAR(11) UNIQUE NOT NULL,
    telefone VARCHAR(20) NOT NULL, 
    endereco_id INT NOT NULL,
    CONSTRAINT fk_endereco FOREIGN KEY (endereco_id) REFERENCES transportadora.endereco(id) 
);

CREATE TABLE transportadora.produto(
    id SERIAL PRIMARY KEY, 
    produto_nome VARCHAR(50) NOT NULL,
    peso FLOAT NOT NULL,
    preco FLOAT NOT NULL
);

CREATE TABLE transportadora.entrega(
    id SERIAL PRIMARY KEY, 
    cliente_id INT NOT NULL,
    num_rastreio VARCHAR(10) UNIQUE NOT NULL,
    destino_id INT NOT NULL, 
    valor_frete FLOAT NOT NULL,
    status_entrega VARCHAR(15) NOT NULL,
    dataSaida DATE NOT NULL,
    
    CONSTRAINT fk_cliente FOREIGN KEY (cliente_id) REFERENCES transportadora.cliente(id),
    CONSTRAINT fk_destino FOREIGN KEY (destino_id) REFERENCES transportadora.endereco(id)
);

CREATE TABLE transportadora.itemEntrega(
    id SERIAL PRIMARY KEY, 
    produto_id INT NOT NULL,
    entrega_id INT NOT NULL,
    quantidade INT NOT NULL,
    preco_unitario FLOAT NOT NULL,
  
    CONSTRAINT fk_produto FOREIGN KEY (produto_id) REFERENCES transportadora.produto(id),
    CONSTRAINT fk_entrega FOREIGN KEY (entrega_id) REFERENCES transportadora.entrega(id)
);