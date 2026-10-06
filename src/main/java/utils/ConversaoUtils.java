package utils;

public class ConversaoUtils {

    // string para double
    public static Double converterDouble(String valor) {

        if (valor == null || valor.trim().isEmpty()) {
            return null;
        }
         try {
            return Double.parseDouble(valor.replace(",", "."));
         } catch (NumberFormatException e) {
            return null;
         }   
    }

    // int para string
    public static String converterString(Integer valor) {
        if (valor == null) {
            return null;
        }
        return String.valueOf(valor);
    }
}
