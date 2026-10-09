package utils;
import java.util.Random;

public class GeradorNfeUtils {
    private static String obterCodigoIbge(String uf) {
        switch (uf.toUpperCase()) {
            case "AC": return "12"; case "AL": return "27"; case "AP": return "16";
            case "AM": return "13"; case "BA": return "29"; case "CE": return "23";
            case "DF": return "53"; case "ES": return "32"; case "GO": return "52";
            case "MA": return "21"; case "MT": return "51"; case "MS": return "50";
            case "MG": return "31"; case "PA": return "15"; case "PB": return "25";
            case "PR": return "41"; case "PE": return "26"; case "PI": return "22";
            case "RJ": return "33"; case "RN": return "24"; case "RS": return "43";
            case "RO": return "11"; case "RR": return "14"; case "SC": return "42";
            case "SP": return "35"; case "SE": return "28"; case "TO": return "17";
            default: return "99"; // caso uf venha errada
        }
    }

    public static String geradorNFE(String cnpj, String ufOrigem, String dataEmissao) {
        // codigo numero da uf
        String uf = obterCodigoIbge(ufOrigem);
        
        // junta ano e mes
        String anoMes = dataEmissao.substring(2, 4) + dataEmissao.substring(5, 7);
        String modeloSerie = "55001";
        String cnpjLimpo = cnpj.replaceAll("[^0-9]", "");

        StringBuilder aleatorio = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i <  18; i++) {
            aleatorio.append(random.nextInt(10));
        }

        return uf + anoMes + cnpjLimpo + modeloSerie + aleatorio.toString();
    }   

}
