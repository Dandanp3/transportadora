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

    // valida campo vazio e numero invalido
    public FieldUtils verificarDouble(String valor, String mensagemErro) {
        // verifica se está vazio ou nulo 
        if (valor == null || valor.trim().isEmpty()) {
            erros.add(mensagemErro);
            return this;
        }

        try {
            // tenta converter para double
            double numero = Double.parseDouble(valor.trim().replace(",", "."));
            
            // verifica se é zero ou negativo
            if (numero <= 0) {
                erros.add(mensagemErro + " (Não pode ser zero ou negativo)");
            }

        } catch (NumberFormatException e) {
            // se for letras ou símbolos n aceita
            erros.add(mensagemErro + " (Deve ser um número válido)");
        }

        return this;
    }

    // valida peso 
    public FieldUtils verificarPeso(String valor, String mensagemErro) {
        // nao pode ser vazio
        if (valor == null || valor.trim().isEmpty()) {
            erros.add(mensagemErro + " - Campo obrigatório");
            return this;
        }

        try {
            double peso = Double.parseDouble(valor.trim().replace(",", "."));
            
            // Peso nao pode ser zero ou negativo
            if (peso <= 0) {
                erros.add(mensagemErro + " - Peso deve ser maior que zero");
            }
            // Peso nao pode ser muito alto
            if (peso > 1000) {
                erros.add(mensagemErro + " - Peso não pode exceder 1000kg");
            }

        } catch (NumberFormatException e) {
            erros.add(mensagemErro + " - Deve conter apenas números (use . ou , para decimais)");
        }

        return this;
    }

    // validar preço
    public FieldUtils verificarPreco(String valor, String mensagemErro) {
        // N pode ser vazio
        if (valor == null || valor.trim().isEmpty()) {
            erros.add(mensagemErro + " - Campo obrigatório");
            return this;
        }

        try {
            double preco = Double.parseDouble(valor.trim().replace(",", "."));
            
            // Preço nn pode ser zero ou ngativo
            if (preco <= 0) {
                erros.add(mensagemErro + " - Preço deve ser maior que R$0,00");
            }
            // preço n pode ser absurdo 
            if (preco > 999999.99) {
                erros.add(mensagemErro + " - Preço não pode exceder R$999.999,99");
            }

        } catch (NumberFormatException e) {
            erros.add(mensagemErro + " - Deve conter apenas números (use . ou , para decimais)");
        }

        return this;
    }

    // metodo para erros customizados
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