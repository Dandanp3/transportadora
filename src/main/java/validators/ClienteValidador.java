package validators;

import java.util.List;
import models.Cliente;
import utils.CpfUtils;
import utils.FieldUtils;

public class ClienteValidador implements Validador<Cliente> {

    @Override 
    public List<String> validar(Cliente cliente) {
        FieldUtils campos = new FieldUtils();

        // Validações básicas e de formato
        campos.verificarNotBlank(cliente.getNome(), "O Nome completo é obrigatório.")
              .verificarTamanho(cliente.getNome(), 3, 100, "O Nome deve ter entre 3 e 100 caracteres.")
              .verificarNotBlank(cliente.getEmail(), "O E-mail é obrigatório.")
              .verificarEmail(cliente.getEmail(), "O E-mail informado não é válido.")
              .verificarNotBlank(cliente.getTelefone(), "O Telefone é obrigatório.")
              .verificarNotBlank(cliente.getDocumento(), "O CPF/CNPJ é obrigatório.");

        String doc = cliente.getDocumento();
        if (doc != null && !doc.isEmpty()) {
            if (doc.length() == 14) {
                if (!CpfUtils.validarCNPJ(doc)) {
                    campos.adicionarErro("O CNPJ inserido é inválido.");
                }
            } else if (doc.length() == 11) {
                if (!CpfUtils.validarCPF(doc)) {
                    campos.adicionarErro("O CPF inserido é inválido.");
                }
            } else {
                campos.adicionarErro("O documento deve ter exatamente 11 (CPF) ou 14 (CNPJ) números.");
            }
        }

        return campos.getErros();
    }
}