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
        
        // pegando array de numeros cpf
        int[] digitos = new int[11];
        for (int i = 0; i < 11; i++) {
            digitos[i] = cpf.charAt(i) - '0';
        }

        // calculo verificador 1
        int soma = 0;
        int peso = 10;
        for (int i = 0; i < 9; i++) {
            soma += (cpf.charAt(i) - '0') * peso--;
        }
        int resto = 11 - (soma % 11);
        //System.out.println("Verificador 1: " + resto);
        char verificador1 = (resto == 10 || resto == 11) ? '0' : (char) (resto + '0');

        // calculo verificador 2
        soma = 0;
        peso = 11;
        for (int i = 0; i< 10; i++) {
            soma += (cpf.charAt(i) - '0')  * peso--;
        }
        resto = 11 - (soma % 11);
        //System.out.println("Verificador 2: " + resto);
        char verificador2 = (resto == 10 || resto == 11) ? '0' : (char) (resto + '0');

        if (verificador1 == cpf.charAt(9) && verificador2 == cpf.charAt(10)) {
            return true;
        } else{
            return false;
        }

    }
}
