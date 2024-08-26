package com.mimepapelaria.Controller;

import com.mimepapelaria.Model.Carrinho;
import com.mimepapelaria.Service.CarrinhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carrinho")
public class CarrinhoController {

    @Autowired
    private CarrinhoService carrinhoService;

    @PostMapping("/{produtoId}")
    public void addToCart(@PathVariable Long produtoId) {
        carrinhoService.addProductToCart(produtoId);
    }

    @DeleteMapping("/{produtoId}")
    public void removeFromCart(@PathVariable Long produtoId) {
        carrinhoService.removeProductFromCart(produtoId);
    }

    @GetMapping
    public List<Carrinho> getCart() {
        return carrinhoService.getUserCart();
    }

    @GetMapping("/quantidade")
    public int getCartQuantity() {
        return carrinhoService.getCartQuantity();
    }
}
