package validators;

import java.util.List;

import models.Cliente;
import utils.CpfUtils;
import utils.FieldUtils;

public class ClienteValidador implements Validador<Cliente> {

    @Override 
    public List<String> validar(Cliente cliente) {
        FieldUtils campos = new FieldUtils();

        campos.verificarNotBlank(cliente.getNome(), "Erro: Campo Nome é obrigatório.")
                .verificarNotBlank(cliente.getDocumento(), "Erro: Campo CPF é obrigatório.");

                if (!CpfUtils.validarCPF(cliente.getDocumento())) {
                        campos.adicionarErro("CPF inserido inválido.");
                    } 
        return campos.getErros();
    }
    
}
