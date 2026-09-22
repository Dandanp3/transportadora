package src.utils;

public class CpfUtils {
    // metodo de formar CPF
    public static String formatarCPF(String cpf) {
        cpf = cpf.replaceAll("[^0-9]", ""); // ^ NEGAÇÃO / OQ PROCURA / OQ SUBSTITUIR
        return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }

    public static boolean validarCPF(String cpf) {
        cpf = cpf.replaceAll("[^0-9]", "");

        // cpf deve ter 11 digitos e não pode ter digitos repetidos
        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
            return false; 
        }
        

        // calculo do verificador cpf
        int[] digitos = new int[11];
        for (int i = 0; i < 11; i++) {
            digitos[i] = cpf.charAt(i) - '0';
        }

        /*
        ANOTAÇÃO:
        i passa por todos os valores do cpf, enquanto é incremendo +1 a ele
        com isso consigo passar por cada digito e ainda subtrair dos pesos
        após isso vem o calculo da multiplicaçao dos pesos.
        
        */
        int somaPesos = 0;
        for (int i = 0; i < 9; i++) {
            somaPesos += digitos[i] * (10-i);
        }

        int resto = (int) (somaPesos / 11);



        return true;


    }
}
