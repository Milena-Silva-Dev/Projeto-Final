package com.mimepapelaria.Repository;

import com.mimepapelaria.Model.Carrinho;
import com.mimepapelaria.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CarrinhoRepository extends JpaRepository<Carrinho, Integer> {
    List<Carrinho> findByUsuario(Usuario usuario);
    Optional<Carrinho> findByProdutoId(Long productId);
}
