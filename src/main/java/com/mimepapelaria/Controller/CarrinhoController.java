package com.mimepapelaria.Controller;

import com.mimepapelaria.Model.Carrinho;
import com.mimepapelaria.Service.CarrinhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carrinho")
public class CarrinhoController {

    @Autowired
    private CarrinhoService CarrinhoService;

    @PostMapping("/{productId}")
    @PreAuthorize("hasRole('USER')")
    public void addToCart(@PathVariable Long productId) {
        CarrinhoService.addProductToCart(productId);
    }

    @DeleteMapping("/{productId}")
    @PreAuthorize("hasRole('USER')")
    public void removeFromCart(@PathVariable Long productId) {
        CarrinhoService.removeProductFromCart(productId);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public List<Carrinho> getCart() {
        return CarrinhoService.getUserCart();
    }
}
