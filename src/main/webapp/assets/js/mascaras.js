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