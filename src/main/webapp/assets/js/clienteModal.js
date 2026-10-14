(function () {
    const modal  = document.getElementById('modal-cliente');
    const form   = document.getElementById('form-cliente');
    const etapas = form.querySelectorAll('.etapa');
    const passos = form.querySelectorAll('.passo');
    const botoesRodape = form.querySelectorAll('.modal-rodape [data-etapa]');
    let etapaAtual = 0;

    /*  Abrir / fechar */
    document.getElementById('btn-novo-cliente')
        .addEventListener('click', () => modal.showModal());

    form.querySelectorAll('[data-fechar]')
        .forEach(b => b.addEventListener('click', () => modal.close()));

    modal.addEventListener('click', e => { if (e.target === modal) modal.close(); });

    // Fechou (X, Cancelar, ESC ou fundo): limpa tudo
    modal.addEventListener('close', () => {
        form.reset();
        irParaEtapa(0);
        atualizarDocumento();
    });

    /*  Etapas  */
    function irParaEtapa(n) {
        etapaAtual = n;
        etapas.forEach((e, i) => e.hidden = i !== n);
        passos.forEach((p, i) => {
            p.classList.toggle('ativo', i === n);
            p.classList.toggle('concluido', i < n);
        });
        botoesRodape.forEach(b => b.hidden = Number(b.dataset.etapa) !== n);

        const primeiro = etapas[n].querySelector('input:not([type="radio"]):not(:disabled), select');
        if (primeiro && modal.open) primeiro.focus();
    }

    function proximo() {
        // Só avança se os campos da etapa 1 estiverem válidos
        for (const campo of etapas[0].querySelectorAll('input, select')) {
            if (!campo.reportValidity()) return;
        }
        irParaEtapa(1);
    }

    document.getElementById('btn-proximo').addEventListener('click', proximo);
    document.getElementById('btn-voltar').addEventListener('click', () => irParaEtapa(0));

    // Enter na etapa 1 avança em vez de tentar enviar o formulário
    form.addEventListener('keydown', e => {
        if (e.key === 'Enter' && etapaAtual === 0 && e.target.tagName !== 'BUTTON') {
            e.preventDefault();
            proximo();
        }
    });

    /* Mascaras  */
    // Preenche o padrão (# = dígito) com os números digitados
    function mascara(valor, padrao) {
        const digitos = valor.replace(/\D/g, '');
        let saida = '', i = 0;
        for (const c of padrao) {
            if (i >= digitos.length) break;
            saida += c === '#' ? digitos[i++] : c;
        }
        return saida;
    }

    /*  CPF x CNPJ (detectado pelo que foi digitado)  */
    const doc = form.elements.documento;

    function atualizarDocumento() {
        const cnpj = doc.value.replace(/\D/g, '').length > 11;

        // até 11 dígitos = máscara de CPF; a partir do 12º vira CNPJ
        doc.value = mascara(doc.value, cnpj ? '##.###.###/####-##' : '###.###.###-##');
        const total = doc.value.replace(/\D/g, '').length;

        document.getElementById('rotulo-nome').textContent = cnpj ? 'Razão social' : 'Nome completo';
        document.getElementById('grupo-fantasia').hidden = !cnpj;
        form.elements.nomeFantasia.disabled = !cnpj;   // desabilitado não é enviado no POST

        // Só aceita CPF completo (11) ou CNPJ completo (14)
        doc.setCustomValidity(
            total === 11 || total === 14 ? '' : 'Informe um CPF (11 dígitos) ou um CNPJ (14 dígitos).'
        );
    }

    doc.addEventListener('input', atualizarDocumento);
    atualizarDocumento();   // estado inicial

    /* Telefone e CEP */
    const tel = form.elements.telefone;
    tel.addEventListener('input', () => {
        const longo = tel.value.replace(/\D/g, '').length > 10;
        tel.value = mascara(tel.value, longo ? '(##) #####-####' : '(##) ####-####');
    });

    const cep = form.elements.cep;
    cep.addEventListener('input', () => cep.value = mascara(cep.value, '#####-###'));
})();