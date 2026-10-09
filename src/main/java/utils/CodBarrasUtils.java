package utils;

import java.util.Random;

public class CodBarrasUtils {
    public static String gerarCodBarras() {
        // gera 13 digitos unicos a partir do currentime
        String tempo = String.valueOf(System.currentTimeMillis());

        //gera um numero aleatorio de 3 digitos (100 e 999)
        int aleatorio = new Random().nextInt(900) + 100;
        return tempo + aleatorio;
    }
}
