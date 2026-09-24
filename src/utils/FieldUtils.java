package src.utils;

import java.util.ArrayList;
import java.util.List;

public class FieldUtils {
    private final List<String> erros = new ArrayList<>();

    public FieldUtils verificarNotBlank(String valor, String mensagemErro) {
        if (valor == null || valor.trim().isEmpty()) {
            erros.add(mensagemErro);
        }
        return this;

    }

    public boolean temErros() {
        return !erros.isEmpty();
    }

    public List<String> getErros() {
        return erros;
    }
}
