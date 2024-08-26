package com.mimepapelaria.Repository;

import com.mimepapelaria.Model.Carrinho;
import com.mimepapelaria.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import com.mimepapelaria.Model.Carrinho;
import com.mimepapelaria.Model.Produto;
import com.mimepapelaria.Model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {
    List<Carrinho> findByUsuario(Usuario usuario);
    Optional<Carrinho> findByUsuarioAndProduto(Usuario usuario, Produto produto);
    Optional<Carrinho> findByUsuarioAndProdutoId(Usuario usuario, Long produtoId);
}

