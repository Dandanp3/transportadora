CREATE SCHEMA transportadora;

CREATE TABLE transportadora.cliente(
	id SERIAL PRIMARY KEY NOT NULL,
	tipo_pessoa VARCHAR(1) NOT NULL,
	nome VARCHAR(100) NOT NULL,
	nome_fantasia VARCHAR(100),
	documento VARCHAR(14) UNIQUE NOT NULL,
	email VARCHAR(100) NOT NULL,
	telefone VARCHAR(20) NOT NULL,
	created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

CREATE TABLE transportadora.endereco(
	id SERIAL PRIMARY KEY NOT NULL,
	cliente_id INTEGER NOT NULL,
	tipo_endereco VARCHAR(20) NOT NULL, -- COLETA / ENTREGA
	cep VARCHAR(20) NOT NULL,
	logradouro VARCHAR(50) NOT NULL,
	numero INTEGER NOT NULL,
	complemento VARCHAR(30),
	bairro VARCHAR(30) NOT NULL,
	cidade VARCHAR(30) NOT NULL,
	uf VARCHAR(2) NOT NULL,

	CONSTRAINT fk_cliente FOREIGN KEY (cliente_id) REFERENCES transportadora.cliente(id)
);

CREATE TABLE transportadora.entrega(
	id SERIAL PRIMARY KEY NOT NULL,
	remetente_id INTEGER NOT NULL,
	destinatario_id INTEGER NOT NULL,
	endereco_origem_id INTEGER NOT NULL,
	endereco_destino_id INTEGER NOT NULL,
	num_rastreio VARCHAR(20) NOT NULL,
	status_entrega VARCHAR(20) NOT NULL,
	valor_frete decimal,
	data_emissao DATE NOT NULL,

	CONSTRAINT fk_remetente FOREIGN KEY (remetente_id) REFERENCES transportadora.cliente(id),
	CONSTRAINT fk_destinatario FOREIGN KEY (destinatario_id) REFERENCES transportadora.cliente(id),
	CONSTRAINT fk_origem FOREIGN KEY (endereco_origem_id) REFERENCES transportadora.endereco(id),
	CONSTRAINT fk_destino FOREIGN KEY (endereco_destino_id) REFERENCES transportadora.endereco(id)
);

CREATE TABLE transportadora.documento_fiscal (
	id SERIAL PRIMARY KEY NOT NULL,
	entrega_id INTEGER NOT NULL,
	numero_nf INTEGER NOT NULL,
	chave_acesso VARCHAR(44),
	valor_mercadoria DECIMAL(10,2) NOT NULL,

	CONSTRAINT fk_entrega FOREIGN KEY (entrega_id) REFERENCES transportadora.entrega(id)
);

CREATE TABLE transportadora.itemEntrega(
	id SERIAL PRIMARY KEY NOT NULL,
	entrega_id INTEGER NOT NULL,
	codigo_barras VARCHAR(50),
	peso_kg DECIMAL NOT NULL,
	altura_cm DECIMAL NOT NULL,
	largura_cm DECIMAL NOT NULL,
	comprimento_cm DECIMAL NOT NULL,

	CONSTRAINT fk_entrega FOREIGN KEY (entrega_id) REFERENCES transportadora.entrega(id)
);
