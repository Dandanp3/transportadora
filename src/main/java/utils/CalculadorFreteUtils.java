package utils;

public class CalculadorFreteUtils {
    // tarifa ficticia de R$5,50 por kg taxavel
    private static final double TARIFA_POR_KG = 5.50;

    private static final double FATOR_CUBAGEM = 300.0;

    public static double calcularFrete(double pesoReal, double alturaCm, double larguraCm, double comprimentoCm) {

        // calcula o volume em metros cubicos / dividi=se o resltado por 1M para converter em metros cubicos
        double volumeMetrosCubicos = (alturaCm * larguraCm * comprimentoCm) / 1000000.0;

        // calcula o peso cubado
        double pesoCubado = volumeMetrosCubicos * FATOR_CUBAGEM;

        // descobrindo o valor taxavel
        double pesoTaxavel = Math.max(pesoReal, pesoCubado);

        // retorna o frete
        return pesoTaxavel * TARIFA_POR_KG;
    } 

    
}
