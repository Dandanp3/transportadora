package validators;

import java.util.List;

import models.Cliente;
import utils.ConversaoUtils;
import utils.CpfUtils;
import utils.FieldUtils;

public class ClienteValidador implements Validador<Cliente> {

    @Override 
    public List<String> validar(Cliente cliente) {
        FieldUtils campos = new FieldUtils();

        campos.verificarNotBlank(cliente.getNome(), "Erro: Campo Nome é obrigatório.")
                .verificarNotBlank(cliente.getDocumento(), "Erro: Campo CPF é obrigatório.")
                .verificarNotBlank(cliente.getTelefone(), "Erro: Campo Telefone é obrigatório.")
                .verificarNotBlank(cliente.getEndereco().getLogradouro(), "Erro: Campo Logradouro é obrigatório.")
                .verificarNotBlank(ConversaoUtils.converterString(cliente.getEndereco().getNumero()), "Erro: Campo Número é obrigatório.")
                .verificarNotBlank(cliente.getEndereco().getBairro(), "Erro: Campo Bairro é obrigatório.")
                .verificarNotBlank(cliente.getEndereco().getCidade(), "Erro: Campo Cidade é obrigatório.")
                .verificarNotBlank(cliente.getEndereco().getUF(), "Erro: Campo UF é obrigatório.")
                .verificarNotBlank(cliente.getEndereco().getCEP(), "Erro: Campo CEP é obrigatório.");

                if (!CpfUtils.validarCPF(cliente.getCPF())) {
                        campos.adicionarErro("CPF inserido inválido.");
                    } 
        return campos.getErros();
    }
    
}
