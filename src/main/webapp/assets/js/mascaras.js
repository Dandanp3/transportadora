document.addEventListener("DOMContentLoaded", function() {
    
    const campoDocumento = document.getElementById('documento');
    const campoTelefone = document.getElementById('telefone');
    const campoCep = document.getElementById('cep');

    // Máscara de CPF e CNPJ dinâmica
    if (campoDocumento) {
        campoDocumento.addEventListener('input', function() {
            let v = this.value.replace(/\D/g, "");
            
            if (v.length <= 11) {
                // formata como cpf
                v = v.replace(/(\d{3})(\d)/, "$1.$2");
                v = v.replace(/(\d{3})(\d)/, "$1.$2");
                v = v.replace(/(\d{3})(\d{1,2})$/, "$1-$2");
            } else {
                // formata como cnpj
                v = v.replace(/^(\d{2})(\d)/, "$1.$2");
                v = v.replace(/^(\d{2})\.(\d{3})(\d)/, "$1.$2.$3");
                v = v.replace(/\.(\d{3})(\d)/, ".$1/$2");
                v = v.replace(/(\d{4})(\d)/, "$1-$2");
            }
            this.value = v.substring(0, 18);
        });
    }

    // mascara de telefone
    if (campoTelefone) {
        campoTelefone.addEventListener('input', function() {
            let v = this.value.replace(/\D/g, "");
            v = v.replace(/^(\d{2})(\d)/g, "($1) $2");
            v = v.replace(/(\d)(\d{4})$/, "$1-$2");
            this.value = v.substring(0, 15);
        });
    }

    // mascara do cep
    if (campoCep) {
        campoCep.addEventListener('input', function() {
            let v = this.value.replace(/\D/g, "");
            v = v.replace(/^(\d{5})(\d)/, "$1-$2");
            this.value = v.substring(0, 9);
        });
    }

    
});

function mascaraMoeda(input) {
            let valor = input.value;
            // deixa so numero e virgula
            valor = valor.replace(/\D/g, "");
            // coloca virgula antes dos dois ultimos digitos
            valor = valor.replace(/(\d)(\d{2})$/, "$1,$2");
            // formata milhar se passar de mil reais
            valor = valor.replace(/(?=(\d{3})+(\D))\B/g, ".");
            input.value = valor;
        }

        function mascaraPeso(input) {
            let valor = input.value;
            // peso precisa aceitar 3 casas decimais (gramas) se quiser
            valor = valor.replace(/[^\d,]/g, "");
            input.value = valor;
        }
        
        // Dispara a mascara ao carregar a tela de edicao pra arrumar os numeros q vem do banco (ex: 150.0 vira 150,00)
        window.addEventListener("load", function () {
        const valorInput = document.getElementById("valorPadrao");
        const pesoInput = document.getElementById("pesoPadrao");

        if (valorInput && valorInput.value && !valorInput.value.includes(',')) {
            valorInput.value = parseFloat(valorInput.value).toFixed(2).replace('.', ',');
        }
        if (pesoInput && pesoInput.value && !pesoInput.value.includes(',')) {
            pesoInput.value = parseFloat(pesoInput.value).toFixed(3).replace('.', ',');
        }
} );