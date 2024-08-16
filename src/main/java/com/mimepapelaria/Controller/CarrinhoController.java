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
    private CarrinhoService CarrinhoService;

    @PostMapping("/{productId}")
    public void addToCart(@PathVariable Long productId) {
        CarrinhoService.addProductToCart(productId);
    }

    @DeleteMapping("/{productId}")
    public void removeFromCart(@PathVariable Long productId) {
        CarrinhoService.removeProductFromCart(productId);
    }

    @GetMapping
    public List<Carrinho> getCart() {
        return CarrinhoService.getUserCart();
    }
}
