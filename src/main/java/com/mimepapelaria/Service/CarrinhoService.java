package com.mimepapelaria.Service;

import com.mimepapelaria.Model.Carrinho;
import com.mimepapelaria.Model.Produto;
import com.mimepapelaria.Model.Usuario;
import com.mimepapelaria.Repository.CarrinhoRepository;
import com.mimepapelaria.Repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class CarrinhoService {

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public void addProductToCart(Long productId) {
        Produto produto = produtoRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado!"));

        Carrinho carrinho = new Carrinho();
        carrinho.setProduto(produto);
        carrinho.setQuantidade(1);
        carrinho.setPreco(produto.getPreco());
        carrinho.setUsuario(getLoggedUser());

        if (carrinho.validateStock()) {
            carrinhoRepository.save(carrinho);
            carrinho.updateStock();
            produtoRepository.save(produto);
        } else {
            throw new RuntimeException("Estoque insuficiente!");
        }
    }

    public void removeProductFromCart(Long productId) {
        Carrinho carrinho = carrinhoRepository.findByProdutoId(productId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado no carrinho!"));
        carrinhoRepository.delete(carrinho);
    }

    public List<Carrinho> getUserCart() {
        return carrinhoRepository.findByUsuario(getLoggedUser());
    }

    private Usuario getLoggedUser() {
        return null;
    }
}