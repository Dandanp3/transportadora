Table cliente {
  id integer [primary key]
  tipo_pessoa char(1) [not null, note: "'F' para Física, 'J' para Jurídica"]
  nome varchar(100) [not null, note: "Nome da Pessoa ou Razão Social"]
  nome_fantasia varchar(100) [note: "Preenchido apenas se for 'J'"]
  documento varchar(14) [unique, not null, note: "Armazena os 11 dígitos do CPF ou 14 do CNPJ"]
  email varchar(100)
  telefone varchar(20) [not null]
  created_at timestamp [default: `CURRENT_TIMESTAMP`]
}

Table endereco {
  id integer [primary key]
  cliente_id integer [not null]
  tipo_endereco varchar(20) [not null, note: "'Coleta', 'Entrega', 'Faturamento'"]
  cep varchar(10) [not null]
  logradouro varchar(50) [not null]
  numero varchar(10) [not null]
  complemento varchar(30)
  bairro varchar(30) [not null]
  cidade varchar(30) [not null]
  uf varchar(2) [not null]
}

Table produto {
  id integer [primary key]
  cliente_id integer [not null, note: "Dono do produto"]
  descricao varchar(150) [not null]
  valor_padrao decimal(10,2) [not null, note: "Valor de referência do produto"]
}

Table entrega {
  id integer [primary key]
  remetente_id integer [not null]
  destinatario_id integer [not null]
  endereco_origem_id integer [not null, note: "Local da coleta"]
  endereco_destino_id integer [not null, note: "Local da entrega"]
  num_rastreio varchar(20) [unique, not null]
  status_entrega varchar(20) [not null, note: "'Pendente', 'Em Rota', 'Entregue'"]
  valor_frete decimal(10,2) [not null]
  data_emissao date [not null]
}

// O VOLUME FÍSICO (A caixa no caminhão)
Table item_entrega {
  id integer [primary key]
  entrega_id integer [not null]
  codigo_barras_etiqueta varchar(50) [unique, not null]
  peso_kg decimal(8,3) [not null]
  altura_cm integer [not null]
  largura_cm integer [not null]
  comprimento_cm integer [not null]
}

// O DOCUMENTO LEGAL (Cabeçalho)
Table documento_fiscal {
  id integer [primary key]
  entrega_id integer [not null]
  tipo_documento varchar(20) [not null, note: "'NF-e' para PJ ou 'Declaracao' para PF"]
  numero_documento varchar(20) [not null]
  chave_acesso varchar(44) [unique, note: "Nulo se for Declaração de Conteúdo"]
  valor_total_declarado decimal(10,2) [not null, note: "Para o seguro da carga"]
}

// O CONTEÚDO (O que está descrito no papel)
Table item_documento {
  id integer [primary key]
  documento_fiscal_id integer [not null]
  produto_id integer [not null]
  quantidade integer [not null]
  valor_unitario decimal(10,2) [not null, note: "Pode variar do valor_padrao no momento da emissao"]
}

// Relacionamentos - Clientes e Endereços
Ref: endereco.cliente_id > cliente.id
Ref: entrega.remetente_id > cliente.id
Ref: entrega.destinatario_id > cliente.id
Ref: entrega.endereco_origem_id > endereco.id
Ref: entrega.endereco_destino_id > endereco.id

// Relacionamentos - Produtos, Entregas e Fiscal
Ref: produto.cliente_id > cliente.id
Ref: item_entrega.entrega_id > entrega.id
Ref: documento_fiscal.entrega_id > entrega.id
Ref: item_documento.documento_fiscal_id > documento_fiscal.id
Ref: item_documento.produto_id > produto.id