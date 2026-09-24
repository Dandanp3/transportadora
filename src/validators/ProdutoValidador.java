package src.validators;

import java.util.List;

import src.models.Produto;
import src.utils.FieldUtils;

public class ProdutoValidador implements Validador<Produto> {

    @Override 
    public List<String> validar(Produto produto) {
        FieldUtils campos = new FieldUtils();

        campos.verificarNotBlank(produto.getNome(), "Erro: Campo Nome do Produto é obrigatório.")
            .verificarDouble(produto.getPeso(), "Erro: Campo Peso obrigatório.")
            .verificarDouble(produto.getPreco(), "Erro: Campo Preço é obrigatório.");


        if (campos.temErros()) {
            campos.getErros().forEach(System.out::prinln);
            return;
        }
        return null;
    }
    
}
