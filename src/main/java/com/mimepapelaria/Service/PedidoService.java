package com.mimepapelaria.Service;

import com.mimepapelaria.Exception.ResourceNotFoundException;
import com.mimepapelaria.Model.Carrinho;
import com.mimepapelaria.Model.Pedido;
import com.mimepapelaria.Repository.CarrinhoRepository;
import com.mimepapelaria.Repository.PedidoRepository;
import com.mimepapelaria.Repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private CarrinhoRepository carrinhoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Transactional
    public Pedido save(Pedido pedido) {
        for (Carrinho item : pedido.getItensCarrinho()) {
            if (!item.validateStock()) {
                throw new RuntimeException("Estoque insuficiente para o produto: " + item.getProduto().getNome());
            }
            item.updateStock();
        }

        double total = pedido.getItensCarrinho().stream()
                .mapToDouble(Carrinho::getTotalItemValue)
                .sum();
        pedido.setTotal(total);

        Pedido savedPedido = pedidoRepository.save(pedido);

        for (Carrinho item : pedido.getItensCarrinho()) {
            item.setPedido(savedPedido);
            carrinhoRepository.save(item);
        }

        return savedPedido;
    }

    public Pedido findById(int id) {
        return pedidoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));
    }

    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }
}
