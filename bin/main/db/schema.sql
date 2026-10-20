CREATE SCHEMA transportadora

CREATE TABLE transportadora.cliente (
    id SERIAL PRIMARY KEY NOT NULL,
    tipo_pessoa CHAR(1) NOT NULL, -- 'F ou J
    nome VARCHAR(100) NOT NULL,
    nome_fantasia VARCHAR(100),
    documento VARCHAR(14) UNIQUE NOT NULL,
    email VARCHAR(100),
    telefone VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE transportadora.endereco (
    id SERIAL PRIMARY KEY NOT NULL,
    cliente_id INTEGER NOT NULL,
    tipo_endereco VARCHAR(20) NOT NULL, -- coleta / entrega
    cep VARCHAR(10) NOT NULL,
    logradouro VARCHAR(50) NOT NULL,
    numero INT NOT NULL, 
    complemento VARCHAR(30),
    bairro VARCHAR(30) NOT NULL,
    cidade VARCHAR(30) NOT NULL,
    uf VARCHAR(2) NOT NULL,

    CONSTRAINT fk_cliente FOREIGN KEY (cliente_id) REFERENCES transportadora.cliente(id)
);

CREATE TABLE transportadora.produto (
    id SERIAL PRIMARY KEY NOT NULL,
    cliente_id INTEGER NOT NULL, -- remetente
    descricao VARCHAR(150) NOT NULL,
    valor_padrao DECIMAL(10,2) NOT NULL,

    CONSTRAINT fk_produto_cliente FOREIGN KEY (cliente_id) REFERENCES transportadora.cliente(id)
);

CREATE TABLE transportadora.entrega (
    id SERIAL PRIMARY KEY NOT NULL,
    remetente_id INTEGER NOT NULL,
    destinatario_id INTEGER NOT NULL,
    endereco_origem_id INTEGER NOT NULL,
    endereco_destino_id INTEGER NOT NULL,
    num_rastreio VARCHAR(20) UNIQUE NOT NULL,
    status_entrega VARCHAR(20) NOT NULL,
    valor_frete DECIMAL(10,2) NOT NULL,
    data_emissao DATE NOT NULL,

    CONSTRAINT fk_remetente FOREIGN KEY (remetente_id) REFERENCES transportadora.cliente(id),
    CONSTRAINT fk_destinatario FOREIGN KEY (destinatario_id) REFERENCES transportadora.cliente(id),
    CONSTRAINT fk_origem FOREIGN KEY (endereco_origem_id) REFERENCES transportadora.endereco(id),
    CONSTRAINT fk_destino FOREIGN KEY (endereco_destino_id) REFERENCES transportadora.endereco(id)
);

-- a caixa fisica do caminhao
CREATE TABLE transportadora.item_entrega (
    id SERIAL PRIMARY KEY NOT NULL,
    entrega_id INTEGER NOT NULL,
    codigo_barras_etiqueta VARCHAR(50) UNIQUE NOT NULL,
    peso_kg DECIMAL(8,3) NOT NULL,
    altura_cm INTEGER NOT NULL,
    largura_cm INTEGER NOT NULL,
    comprimento_cm INTEGER NOT NULL,

    CONSTRAINT fk_item_entrega FOREIGN KEY (entrega_id) REFERENCES transportadora.entrega(id)
);

-- documento legal
CREATE TABLE transportadora.documento_fiscal (
    id SERIAL PRIMARY KEY NOT NULL,
    entrega_id INTEGER NOT NULL,
    tipo_documento VARCHAR(20) NOT NULL, --nfe ou declaracao
    numero_documento VARCHAR(20) NOT NULL,
    chave_acesso VARCHAR(44) UNIQUE, -- null se for pf
    valor_total_declarado DECIMAL(10,2) NOT NULL,

    CONSTRAINT fk_doc_entrega FOREIGN KEY (entrega_id) REFERENCES transportadora.entrega(id)
);

-- conteudo da caixa
CREATE TABLE transportadora.item_documento (
    id SERIAL PRIMARY KEY NOT NULL,
    documento_fiscal_id INTEGER NOT NULL,
    produto_id INTEGER NOT NULL,
    quantidade INTEGER NOT NULL,
    valor_unitario DECIMAL(10,2) NOT NULL,

    CONSTRAINT fk_itemdoc_fiscal FOREIGN KEY (documento_fiscal_id) REFERENCES transportadora.documento_fiscal(id),
    CONSTRAINT fk_itemdoc_produto FOREIGN KEY (produto_id) REFERENCES transportadora.produto(id)
);