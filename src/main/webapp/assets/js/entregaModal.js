(function () {
    const modal  = document.getElementById('modal-entrega');
    const form   = document.getElementById('form-entrega');
    const etapas = form.querySelectorAll('.etapa');
    const passos = form.querySelectorAll('.passo');
    const botoesRodape = form.querySelectorAll('.modal-rodape [data-etapa]');
    let etapaAtual = 0;

    const CLIENTES_DEMO = [
        { id: 1, codigo: 'CL-001', nome: 'Marcos Oliveira', tipo: 'F', documento: '12345678901',
          enderecos: [
            { id: 101, tipo: 'COLETA',  logradouro: 'Rua da Bahia',  numero: 1200, complemento: 'Apto 301', bairro: 'Centro',      cidade: 'Belo Horizonte', uf: 'MG' },
            { id: 102, tipo: 'ENTREGA', logradouro: 'Av. Amazonas',  numero: 450,  complemento: '',         bairro: 'Barro Preto', cidade: 'Belo Horizonte', uf: 'MG' }
          ] },
        { id: 2, codigo: 'CL-002', nome: 'Tech Solutions Ltda.', tipo: 'J', documento: '12345678000195',
          enderecos: [
            { id: 201, tipo: 'COLETA',  logradouro: 'Av. do Contorno', numero: 8000, complemento: 'Sala 12', bairro: 'Santo Agostinho', cidade: 'Belo Horizonte', uf: 'MG' },
            { id: 202, tipo: 'ENTREGA', logradouro: 'Rua Padre Eustáquio', numero: 90, complemento: '',     bairro: 'Padre Eustáquio', cidade: 'Contagem',       uf: 'MG' }
          ] },
        { id: 3, codigo: 'CL-003', nome: 'Ana Souza', tipo: 'F', documento: '12378912301',
          enderecos: [
            { id: 301, tipo: 'COLETA',  logradouro: 'Rua das Flores', numero: 15, complemento: '', bairro: 'Jardim Alterosa', cidade: 'Betim', uf: 'MG' },
            { id: 302, tipo: 'ENTREGA', logradouro: 'Rua das Flores', numero: 15, complemento: '', bairro: 'Jardim Alterosa', cidade: 'Betim', uf: 'MG' }
          ] },
        { id: 4, codigo: 'CL-004', nome: 'Comercial Horizonte', tipo: 'J', documento: '12567890000144',
          enderecos: [
            { id: 401, tipo: 'COLETA',  logradouro: 'Av. Brasil', numero: 2500, complemento: 'Galpão 2', bairro: 'Industrial', cidade: 'Betim',      uf: 'MG' },
            { id: 402, tipo: 'ENTREGA', logradouro: 'Rua Goiás',  numero: 310,  complemento: '',         bairro: 'Centro',     cidade: 'Uberlândia', uf: 'MG' }
          ] },
        { id: 5, codigo: 'CL-005', nome: 'Lucas Ferreira', tipo: 'F', documento: '12332165401',
          enderecos: [
            { id: 501, tipo: 'ENTREGA', logradouro: 'Rua XV de Novembro', numero: 77, complemento: 'Casa 2', bairro: 'Centro', cidade: 'Juiz de Fora', uf: 'MG' }
          ] },   // sem endereço de coleta: serve para testar a mensagem de "nenhum endereço"
        { id: 9, codigo: 'CL-009', nome: 'SixtySeven Solutions LTDA.', tipo: 'J', documento: '98765432000110',
          enderecos: [
            { id: 901, tipo: 'COLETA',  logradouro: 'Rua Rio Grande do Norte', numero: 1000, complemento: '10º andar', bairro: 'Savassi', cidade: 'Belo Horizonte', uf: 'MG' },
            { id: 902, tipo: 'ENTREGA', logradouro: 'Rua Rio Grande do Norte', numero: 1000, complemento: '10º andar', bairro: 'Savassi', cidade: 'Belo Horizonte', uf: 'MG' }
          ] }
    ];

    const semAcento = s => s.normalize('NFD').replace(/[\u0300-\u036f]/g, '').toLowerCase();

    // Versão de demonstração: filtra a lista acima (por nome, CPF ou CNPJ)
    function buscarClientes(termo) {
        const texto   = semAcento(termo.trim());
        const digitos = termo.replace(/\D/g, '');
        const achados = CLIENTES_DEMO.filter(c =>
            !texto ||
            semAcento(c.nome).includes(texto) ||
            (digitos && c.documento.includes(digitos))
        );
        return Promise.resolve(achados.slice(0, 8));
    }

    function documentoMascarado(c) {
        if (c.documentoMascarado) return c.documentoMascarado;
        const d = c.documento;
        return d.length === 11
            ? `***.${d.slice(3, 6)}.${d.slice(6, 9)}-**`
            : `**.${d.slice(2, 5)}.${d.slice(5, 8)}/${d.slice(8, 12)}-**`;
    }

    function formatarEndereco(e) {
        const compl = e.complemento ? ` – ${e.complemento}` : '';
        return `${e.logradouro}, ${e.numero}${compl} · ${e.bairro}, ${e.cidade}/${e.uf}`;
    }

    function criarCombo(raiz, cfg) {
        const input      = raiz.querySelector('.combo-input');
        const idCliente  = raiz.querySelector('input[type="hidden"]');
        const btnLimpar  = raiz.querySelector('.combo-limpar');
        const lista      = raiz.querySelector('.combo-lista');
        const selectEnd  = document.getElementById(cfg.selectEndereco);
        const MSG        = 'Selecione um cliente da lista.';

        let resultados = [];
        let indiceAtivo = -1;
        let ultimoPedido = 0;   // descarta respostas antigas se a pessoa continuar digitando
        let timer = null;

        /* --- endereços --- */
        function resetarEnderecos() {
            selectEnd.innerHTML = '';
            const op = new Option(cfg.textoSemCliente, '', true, true);
            op.disabled = true;
            selectEnd.add(op);
            selectEnd.disabled = true;
        }

        function preencherEnderecos(cliente) {
            const doTipo = (cliente.enderecos || []).filter(e => e.tipo === cfg.tipoEndereco);

            selectEnd.innerHTML = '';
            const texto = doTipo.length
                ? 'Selecione o endereço'
                : `Nenhum endereço de ${cfg.rotuloTipo} cadastrado`;
            const op = new Option(texto, '', true, true);
            op.disabled = true;
            selectEnd.add(op);

            doTipo.forEach(e => selectEnd.add(new Option(formatarEndereco(e), e.id)));
            selectEnd.disabled = false;   // fica habilitado (inválido) para o formulário não deixar passar sem endereço

            if (doTipo.length === 1) selectEnd.value = String(doTipo[0].id);   // só um? já escolhe
        }

        /* --- lista aberta / fechada --- */
        function abrir() {
            lista.hidden = false;
            input.setAttribute('aria-expanded', 'true');
        }

        function fechar() {
            lista.hidden = true;
            input.setAttribute('aria-expanded', 'false');
            input.removeAttribute('aria-activedescendant');
            indiceAtivo = -1;
        }

        function marcarAtivo(i) {
            const itens = lista.querySelectorAll('.combo-opcao');
            itens.forEach((li, n) => li.setAttribute('aria-selected', String(n === i)));
            indiceAtivo = i;
            if (itens[i]) {
                input.setAttribute('aria-activedescendant', itens[i].id);
                itens[i].scrollIntoView({ block: 'nearest' });
            }
        }

        function desenhar() {
            lista.innerHTML = '';

            if (!resultados.length) {
                const vazio = document.createElement('li');
                vazio.className = 'combo-vazio';
                vazio.textContent = 'Nenhum cliente encontrado.';
                lista.appendChild(vazio);
                return;
            }

            resultados.forEach((c, i) => {
                const li = document.createElement('li');
                li.className = 'combo-opcao';
                li.id = `${raiz.id}-op-${i}`;
                li.setAttribute('role', 'option');
                li.setAttribute('aria-selected', 'false');

                // textContent (e não innerHTML) para um nome com "<" não virar HTML
                const nome = document.createElement('span');
                nome.className = 'combo-nome';
                nome.textContent = c.nome;

                const detalhe = document.createElement('span');
                detalhe.className = 'combo-detalhe';
                detalhe.textContent = `${c.tipo === 'J' ? 'CNPJ' : 'CPF'} ${documentoMascarado(c)} · ${c.codigo}`;

                li.append(nome, detalhe);
                li.addEventListener('click', () => selecionar(c));
                lista.appendChild(li);
            });
        }

        async function pesquisar() {
            const pedido = ++ultimoPedido;
            const achados = await buscarClientes(input.value);
            if (pedido !== ultimoPedido) return;   // chegou uma resposta velha: ignora

            resultados = achados;
            desenhar();
            abrir();
            indiceAtivo = -1;
        }

        /* --- escolher / limpar --- */
        function selecionar(c) {
            ultimoPedido++;
            clearTimeout(timer);

            input.value = c.nome;
            idCliente.value = c.id;
            input.setCustomValidity('');
            btnLimpar.hidden = false;
            fechar();
            preencherEnderecos(c);
        }

        function limparSelecao() {
            idCliente.value = '';
            input.setCustomValidity(MSG);   // sem cliente da lista, o campo fica inválido
            btnLimpar.hidden = true;
            resetarEnderecos();
        }

        function reiniciar() {
            ultimoPedido++;
            clearTimeout(timer);
            input.value = '';
            limparSelecao();
            fechar();
            lista.innerHTML = '';
        }

        /* eventos */
        input.addEventListener('input', () => {
            if (idCliente.value) limparSelecao();   // mexeu no texto: a escolha anterior deixa de valer
            clearTimeout(timer);
            timer = setTimeout(pesquisar, 150);     // espera 150 ms sem digitar antes de buscar
        });

        input.addEventListener('focus', () => { if (!idCliente.value) pesquisar(); });
        input.addEventListener('click', () => { if (lista.hidden && !idCliente.value) pesquisar(); });
        input.addEventListener('blur', fechar);

        // Clicar na lista não pode tirar o foco do campo (senão o blur fecha antes do clique)
        lista.addEventListener('mousedown', e => e.preventDefault());

        input.addEventListener('keydown', e => {
            const aberta = !lista.hidden;
            const itens = lista.querySelectorAll('.combo-opcao');

            if (e.key === 'ArrowDown') {
                e.preventDefault();
                if (!aberta) { pesquisar(); return; }
                if (itens.length) marcarAtivo((indiceAtivo + 1) % itens.length);

            } else if (e.key === 'ArrowUp') {
                e.preventDefault();
                if (aberta && itens.length) marcarAtivo((indiceAtivo - 1 + itens.length) % itens.length);

            } else if (e.key === 'Enter' && aberta && itens.length) {
                const alvo = indiceAtivo >= 0 ? indiceAtivo : (resultados.length === 1 ? 0 : -1);
                if (alvo >= 0) {
                    e.preventDefault();
                    e.stopPropagation();
                    selecionar(resultados[alvo]);
                }

            } else if (e.key === 'Escape' && aberta) {
                e.preventDefault();
                e.stopPropagation();   // fecha só a lista, não o modal inteiro
                fechar();
            }
        });

        btnLimpar.addEventListener('click', () => {
            input.value = '';
            limparSelecao();
            input.focus();
        });

        reiniciar();   // estado inicial
        return { reiniciar };
    }

    const combos = [
        criarCombo(document.getElementById('combo-remetente'), {
            selectEndereco: 'origem-endereco',
            tipoEndereco: 'COLETA',
            rotuloTipo: 'coleta',
            textoSemCliente: 'Selecione o remetente primeiro'
        }),
        criarCombo(document.getElementById('combo-destinatario'), {
            selectEndereco: 'destino-endereco',
            tipoEndereco: 'ENTREGA',
            rotuloTipo: 'entrega',
            textoSemCliente: 'Selecione o destinatário primeiro'
        })
    ];

    /* Etapa 2: rastreio e data */
    const rastreio = document.getElementById('num-rastreio');
    const dataEmissao = document.getElementById('data-emissao');

    rastreio.addEventListener('input', () => {
        rastreio.value = rastreio.value.toUpperCase().replace(/[^A-Z0-9]/g, '');
    });

    // Gera "BR" 
    document.getElementById('btn-gerar-rastreio').addEventListener('click', () => {
        rastreio.value = 'BR' + String(Math.floor(Math.random() * 1e8)).padStart(8, '0');
        rastreio.focus();
    });

    function hoje() {
        const d = new Date();
        d.setMinutes(d.getMinutes() - d.getTimezoneOffset());   // evita virar o dia por causa do fuso
        return d.toISOString().slice(0, 10);
    }
    dataEmissao.value = hoje();

    /* Abrir / fechar */
    document.getElementById('btn-nova-entrega')
        .addEventListener('click', () => modal.showModal());

    form.querySelectorAll('[data-fechar]')
        .forEach(b => b.addEventListener('click', () => modal.close()));

    // Clicou no fundo escurecido 
    modal.addEventListener('click', e => { if (e.target === modal) modal.close(); });

    // Fechou (X, Cancelar, ESC ou fundo) limpa tudo
    modal.addEventListener('close', () => {
        form.reset();
        combos.forEach(c => c.reiniciar());   // o reset do form não limpa os campos escondidos
        dataEmissao.value = hoje();
        irParaEtapa(0);
    });

    /* Etapas */
    function irParaEtapa(n) {
        etapaAtual = n;
        etapas.forEach((e, i) => e.hidden = i !== n);
        passos.forEach((p, i) => {
            p.classList.toggle('ativo', i === n);
            p.classList.toggle('concluido', i < n);
        });
        botoesRodape.forEach(b => b.hidden = Number(b.dataset.etapa) !== n);

        const primeiro = etapas[n].querySelector(
            'input:not([type="hidden"]):not([type="radio"]):not(:disabled), select:not(:disabled)'
        );
        if (primeiro && modal.open) primeiro.focus();
    }

    function proximo() {
        // Só avança se a etapa 1 estiver valida (clientes escolhidos da lista + endereços)
        for (const campo of etapas[0].querySelectorAll('input, select')) {
            if (!campo.reportValidity()) return;
        }
        irParaEtapa(1);
    }

    document.getElementById('btn-entrega-proximo').addEventListener('click', proximo);
    document.getElementById('btn-entrega-voltar').addEventListener('click', () => irParaEtapa(0));

    // Enter na etapa 1 avança em vez de tentar enviar o forms
    form.addEventListener('keydown', e => {
        if (e.key === 'Enter' && etapaAtual === 0 && e.target.tagName !== 'BUTTON') {
            e.preventDefault();
            proximo();
        }
    });
})();