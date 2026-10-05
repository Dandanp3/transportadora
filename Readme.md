# Sistema de Entregas 

Um sistema simples de logística via terminal (CLI) construído em Java. O projeto foi desenvolvido para praticar conceitos fundamentais de Orientação a Objetos (POO), encapsulamento e organização de código.

## O que o sistema faz
- **Cadastro de Clientes:** Salva dados do cliente e seu endereço.
- **Cadastro de Produtos:** Registra itens com peso e valor.
- **Gestão de Entregas:** Junta o cliente aos produtos escolhidos e calcula o frete.
- **Relatórios:** Exibe um resumo completo de todas as entregas e pacotes registrados.

## Tecnologias e Estrutura
- **Linguagem:** Java
- **Armazenamento:** `ArrayList` (dados salvos na memória durante a execução).
- **Organização de Pacotes:** 
  - `models/`: Classes de molde (`Cliente`, `Produto`, etc).
  - `service/`: Onde a mágica acontece (`Sistema` com a lógica e menus).
  - `app/`: Apenas o `Main` para iniciar o programa.

## MODELO ER
```mermaid
erDiagram
    CLIENTE {
        SERIAL id PK
        VARCHAR(1) tipo_pessoa
        VARCHAR(100) nome
        VARCHAR(100) nome_fantasia
        VARCHAR(14) documento UK
        VARCHAR(100) email
        VARCHAR(20) telefone
        TIMESTAMP created_at
    }
    ENDERECO {
        SERIAL id PK
        INTEGER cliente_id FK
        VARCHAR(20) tipo_endereco
        VARCHAR(20) cep
        VARCHAR(50) logradouro
        INTEGER numero
        VARCHAR(30) complemento
        VARCHAR(30) bairro
        VARCHAR(30) cidade
        VARCHAR(2) uf
    }
    ENTREGA {
        SERIAL id PK
        INTEGER remetente_id FK
        INTEGER destinatario_id FK
        INTEGER endereco_origem_id FK
        INTEGER endereco_destino_id FK
        VARCHAR(20) num_rastreio
        VARCHAR(20) status_entrega
        DECIMAL valor_frete
        DATE data_emissao
    }
    NOTA_FISCAL {
        SERIAL id PK
        INTEGER entrega_id FK
        INTEGER numero_nf
        VARCHAR(44) chave_acesso
        DECIMAL(10_2) valor_mercadoria
    }
    ITEM_ENTREGA {
        SERIAL id PK
        INTEGER entrega_id FK
        VARCHAR(50) codigo_barras
        DECIMAL peso_kg
        DECIMAL altura_cm
        DECIMAL largura_cm
        DECIMAL comprimento_cm
    }

    CLIENTE ||--o{ ENDERECO : "possui"
    CLIENTE ||--o{ ENTREGA : "atua como remetente"
    CLIENTE ||--o{ ENTREGA : "atua como destinatário"
    ENDERECO ||--o{ ENTREGA : "local de origem (coleta)"
    ENDERECO ||--o{ ENTREGA : "local de destino (entrega)"
    ENTREGA ||--o{ NOTA_FISCAL : "documentada por"
    ENTREGA ||--o{ ITEM_ENTREGA : "composta por volumes"