package src.utils;

public class ConversaoUtils {
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
}
