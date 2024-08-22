package com.mimepapelaria.Controller;

import com.mimepapelaria.Model.Produto;
import com.mimepapelaria.Service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService productService;

    @GetMapping("")
    public List<Produto> getAllProducts() {
        return productService.findAll();
    }

    @GetMapping("/{id}")
    public Produto getProductById(@PathVariable Long id) {
        return productService.findById(id);
    }

}
