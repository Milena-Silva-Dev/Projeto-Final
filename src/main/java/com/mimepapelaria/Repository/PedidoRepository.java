package com.mimepapelaria.Repository;

import com.mimepapelaria.Model.Frete;
import com.mimepapelaria.Model.Pedido;
import com.mimepapelaria.Model.Usuario;
import com.mimepapelaria.Model.FormaPagamento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    List<Pedido> findByUsuario(Usuario usuario);
    List<Pedido> findByFormaPagamento(FormaPagamento formaPagamento);
    List<Pedido> findByFrete(Frete frete);
}
