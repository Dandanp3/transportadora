package src.validators;

import java.util.List;

import src.models.Produto;
import utils.FieldUtils;

public class ProdutoValidador implements Validador<Produto> {

    @Override 
    public List<String> validar(Produto produto) {
        FieldUtils campos = new FieldUtils();

        campos.verificarNotBlank(produto.getNome(), "Erro: Nome do Produto é obrigatório")
            .verificarPeso(produto.getPeso(), "Erro: Peso do Produto é obrigatório")
            .verificarPreco(produto.getPreco(), "Erro: Preço do Produto é obrigatório");

        return campos.getErros();
    }
    
}