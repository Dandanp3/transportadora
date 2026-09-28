package src.utils;

public class NumeroUtils {
    public static String formatarTelefone(String telefone) {
        telefone = telefone.replaceAll("[^0-9]", "");

        return telefone.replaceAll("(\\d{2})(\\d{1})(\\d{4})(\\d{4})", "($1) $2 $3-$4");
    }
    
}
