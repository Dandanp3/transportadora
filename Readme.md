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
        VARCHAR(10) cep
        VARCHAR(100) logradouro
        INTEGER numero
        VARCHAR(50) complemento
        VARCHAR(50) bairro
        VARCHAR(50) cidade
        VARCHAR(2) uf
    }
    PRODUTO {
        SERIAL id PK
        VARCHAR(150) descricao
        DECIMAL(10_2) valor_padrao
        DECIMAL(8_3) peso_padrao
    }
    ENTREGA {
        SERIAL id PK
        INTEGER remetente_id FK
        INTEGER destinatario_id FK
        INTEGER endereco_origem_id FK
        INTEGER endereco_destino_id FK
        VARCHAR(20) status_entrega
        DECIMAL(10_2) valor_frete
        VARCHAR(50) numero_nota_fiscal
        DATE data_emissao
    }
    ITEM_ENTREGA {
        SERIAL id PK
        INTEGER entrega_id FK
        INTEGER produto_id FK
        INTEGER quantidade
        DECIMAL(10_2) valor_unitario
    }

    CLIENTE ||--o{ ENDERECO : "possui"
    CLIENTE ||--o{ ENTREGA : "atua como remetente"
    CLIENTE ||--o{ ENTREGA : "atua como destinatário"
    ENDERECO ||--o{ ENTREGA : "local de origem (coleta)"
    ENDERECO ||--o{ ENTREGA : "local de destino (entrega)"
    ENTREGA ||--o{ ITEM_ENTREGA : "possui itens"
    PRODUTO ||--o{ ITEM_ENTREGA : "compõe os itens"
```