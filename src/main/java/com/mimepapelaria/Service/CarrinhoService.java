package com.mimepapelaria.Service;

import com.mimepapelaria.Model.Carrinho;
import com.mimepapelaria.Model.Produto;
import com.mimepapelaria.Model.Usuario;
import com.mimepapelaria.Repository.CarrinhoRepository;
import com.mimepapelaria.Repository.ProdutoRepository;
import com.mimepapelaria.Repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.util.List;

@Transactional
@Service
public class CarrinhoService {

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void addProductToCart(Long productId) {
        Produto produto = produtoRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado!"));

        Usuario usuario = getLoggedUser();

        Carrinho carrinho = carrinhoRepository.findByUsuarioAndProdutoId(usuario, productId)
                .orElse(null);

        if (carrinho == null) {
            carrinho = new Carrinho();
            carrinho.setProduto(produto);
            carrinho.setQuantidade(1);
            carrinho.setPreco(produto.getPreco());
            carrinho.setUsuario(usuario);
        } else {
            carrinho.setQuantidade(carrinho.getQuantidade() + 1);
        }

        if (carrinho.validateStock()) {
            carrinhoRepository.save(carrinho);
            carrinho.updateStock();
            produtoRepository.save(produto);
        } else {
            throw new RuntimeException("Estoque insuficiente!");
        }
    }

    public void removeProductFromCart(Long productId) {
        Usuario usuario = getLoggedUser();

        Carrinho carrinho = carrinhoRepository.findByUsuarioAndProdutoId(usuario, productId)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado no carrinho!"));

        if (carrinho.getQuantidade() > 1) {
            carrinho.setQuantidade(carrinho.getQuantidade() - 1);
            carrinhoRepository.save(carrinho);
        } else {
            carrinhoRepository.delete(carrinho);
        }
    }

    public List<Carrinho> getUserCart() {
        Usuario usuario = null;
        try {
            usuario = getLoggedUser();
        } catch (RuntimeException ignored) {
        }
        return carrinhoRepository.findByUsuario(usuario);
    }

    private Usuario getLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("Usuário não encontrado!");
        }
        String email = authentication.getName();
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado!"));
    }

    public int getCartQuantity() {
        Usuario usuario = null;
        try {
            usuario = getLoggedUser();
        } catch (RuntimeException ignored) {
        }

        if (usuario != null) {
            List<Carrinho> carrinhoList = carrinhoRepository.findByUsuario(usuario);
            return carrinhoList.stream().mapToInt(Carrinho::getQuantidade).sum();
        } else {
            return 0;
        }
    }
}
