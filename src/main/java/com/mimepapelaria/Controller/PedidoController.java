package com.mimepapelaria.Controller;

import com.mimepapelaria.Model.Pedido;
import com.mimepapelaria.Service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping
    public Pedido createPedido(@RequestBody Pedido pedido) {
        return pedidoService.save(pedido);
    }

    @GetMapping("/{id}")
    public Pedido getPedidoById(@PathVariable int id) {
        return pedidoService.findById(id);
    }

    @GetMapping
    public List<Pedido> getAllPedidos() {
        return pedidoService.findAll();
    }
}
