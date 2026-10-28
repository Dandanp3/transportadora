CREATE SCHEMA transportadora;

-- CLIENTE
CREATE TABLE transportadora.cliente (
    id SERIAL PRIMARY KEY,
    tipo_pessoa CHAR(1) NOT NULL, -- 'F' ou 'J'
    nome VARCHAR(100) NOT NULL,
    nome_fantasia VARCHAR(100),
    documento VARCHAR(14) UNIQUE NOT NULL,
    email VARCHAR(100),
    telefone VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

-- ENDEREÇO
CREATE TABLE transportadora.endereco (
    id SERIAL PRIMARY KEY,
    cliente_id INTEGER NOT NULL,
    cep VARCHAR(10) NOT NULL,
    logradouro VARCHAR(100) NOT NULL,
    numero INT NOT NULL, 
    complemento VARCHAR(50),
    bairro VARCHAR(50) NOT NULL,
    cidade VARCHAR(50) NOT NULL,
    uf VARCHAR(2) NOT NULL,

    CONSTRAINT fk_cliente FOREIGN KEY (cliente_id) REFERENCES transportadora.cliente(id)
);

-- PRODUTO
CREATE TABLE transportadora.produto (
    id SERIAL PRIMARY KEY,
    descricao VARCHAR(150) NOT NULL,
    valor_padrao DECIMAL(10,2) NOT NULL,
    peso_padrao DECIMAL(8,3) NOT NULL,
    unidade_medida VARCHAR(5) NOT NULL DEFAULT 'UN',
    CONSTRAINT ck_unidade CHECK (unidade_medida IN ('UN', 'KG', 'L')) 
);

-- ENTREGA 
CREATE TABLE transportadora.entrega (
    id SERIAL PRIMARY KEY,
    remetente_id INTEGER NOT NULL,
    destinatario_id INTEGER NOT NULL,
    endereco_origem_id INTEGER NOT NULL,
    endereco_destino_id INTEGER NOT NULL,
    status_entrega VARCHAR(20) NOT NULL DEFAULT 'PENDENTE',
    valor_frete DECIMAL(10,2) NOT NULL,
    numero_nota_fiscal VARCHAR(50), 
    data_emissao DATE NOT NULL DEFAULT CURRENT_DATE,

    CONSTRAINT fk_remetente FOREIGN KEY (remetente_id) REFERENCES transportadora.cliente(id),
    CONSTRAINT fk_destinatario FOREIGN KEY (destinatario_id) REFERENCES transportadora.cliente(id),
    CONSTRAINT fk_origem FOREIGN KEY (endereco_origem_id) REFERENCES transportadora.endereco(id),
    CONSTRAINT fk_destino FOREIGN KEY (endereco_destino_id) REFERENCES transportadora.endereco(id)
);

-- ITEM ENTREGA 
CREATE TABLE transportadora.item_entrega (
    id SERIAL PRIMARY KEY,
    entrega_id INTEGER NOT NULL,
    produto_id INTEGER NOT NULL,
    quantidade INTEGER NOT NULL,
    valor_unitario DECIMAL(10,2) NOT NULL, 

    CONSTRAINT fk_item_entrega FOREIGN KEY (entrega_id) REFERENCES transportadora.entrega(id) ON DELETE CASCADE,
    CONSTRAINT fk_item_produto FOREIGN KEY (produto_id) REFERENCES transportadora.produto(id)
);