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

Table nota_fiscal {
  id integer [primary key]
  entrega_id integer [not null]
  numero_nf varchar(20) [not null]
  chave_acesso varchar(44) [unique, not null, note: "Codigo de barras da NF-e"]
  valor_mercadoria decimal(10,2) [not null, note: "para o seguro da carga"]
}

Table itemEntrega {
  id integer [primary key]
  entrega_id integer [not null]
  codigo_barras_etiqueta varchar(50) [unique, not null]
  peso_kg decimal(8,3) [not null]
  altura_cm integer [not null]
  largura_cm integer [not null]
  comprimento_cm integer [not null]
}


Ref: endereco.cliente_id > cliente.id

Ref: entrega.remetente_id > cliente.id
Ref: entrega.destinatario_id > cliente.id

Ref: entrega.endereco_origem_id > endereco.id
Ref: entrega.endereco_destino_id > endereco.id

Ref: nota_fiscal.entrega_id > entrega.id

Ref: itemEntrega.entrega_id > entrega.id