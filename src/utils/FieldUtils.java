package src.utils;

import java.util.ArrayList;
import java.util.List;

public class FieldUtils {
    private final List<String> erros = new ArrayList<>();

    // Util para verificar se o campo está vazio
    public FieldUtils verificarNotBlank(String valor, String mensagemErro) {
        if (valor == null || valor.trim().isEmpty()) {
            erros.add(mensagemErro);
        }
        return this;
    }

    // verificar se campos doubles estão vazios
    public FieldUtils verificarDouble(String valor, String mensagemErro) {
        if (valor == null || valor.trim().isEmpty()) {
            return this;
        }

        try {
            double numero = Double.parseDouble(valor.replace(",", "."));
            if (numero <= 0) {
                erros.add(mensagemErro);
            }

        } catch (NumberFormatException e) {
            erros.add(mensagemErro);
        }

        return this;
    }

    // Metodo para adicionar erros
    public FieldUtils adicionarErro(String mensagemErro) {
        erros.add(mensagemErro);
        return this;
    }


    public boolean temErros() {
        return !erros.isEmpty();
    }

    public List<String> getErros() {
        return erros;
    }
}
