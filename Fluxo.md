Aqui está o seu texto com as palavras originais mantidas de forma exata, apenas organizado e formatado para ficar mais limpo e visualmente agradável:

# Divisão de responsabilidades

* **Produto:**
é apenas um cardapio do cliente que está enviando (remetente). Não possui estoque e serve apenas para o sistema saber que o cliente X costuma vencer item Y por valor A.
* **item_entrega:**
É a "caixa". Para a transportadora não importa oq tem dentro. Importa apenas o peso, dimensões e c.barras.
* **documento_fiscal:**
Guarda apenas os dados gerais que não se repetem, como num. documento e o valor declarado
* **item_documento:**
A lista que está impresso no Documento fiscal. Aqui está a quantidade enviada ao Produto do catálogo. Por exemplo 2 teclados anexados ao id do documento fiscal.

---

# Passo a passo na prática

**1. Pré-cadastro:**
Antes de fazer envios frequentes, o remetente cadastra seus produtos no sistema, ficando relacionado a ele.

**2. O usuario inicia a tela de nova entrega e define:**

* Quem envia (Remetente) e de onde (endereço de origem)
* Quem recebe (destinatário) e para onde vai (endereço destino)
* Dados gerais do frete
* Sistema gera e salva a entrega.

**3. Registro das caixas:**
O usuario informa quantas caixas compoem essa entrega. Para cada caixa, ele preenche o peso e o tamanho.

* sistema salva os ItemEntrega atrelado a Entrega

**4. registro da burocracia:**
o usuario informa o documento que acompanha as caixas.

* sistema salva o DocumentoFiscal atrelado aquela entrega.

**5. declaraão do conteudo:**
Pofim o usuario precisa dizer oq tem dentro daquela NFE. O sistema busca a lista de Produtos daquele remetente. O usuario seleciona "teclado", digita a quantidade "1" e salva.

* o sistema salva os ItemDocumento atrelado aquele DocumentoFiscal e ao Produto escolhido.

---

# Vantagens

Se um e-commerce grande for usar, ja vai ter um catalogo de produtos. Se o caminhão for roubado, vms saber exatamente o valor.