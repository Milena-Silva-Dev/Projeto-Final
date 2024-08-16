package com.mimepapelaria.Controller;

import com.mimepapelaria.Model.Produto;
import com.mimepapelaria.Service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/produtos")
public class AdminProdutoController {

    @Autowired
    private ProdutoService produtoService;

    @PostMapping("/cadastrar")
    public ResponseEntity<Produto> addProduct(@RequestBody Produto product) {
        Produto createdProduct = produtoService.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> updateProduct(@PathVariable int id, @RequestBody Produto product) {
        Produto updatedProduct = produtoService.atualizarProdutoParcial((long) id, product);
        return updatedProduct != null ? ResponseEntity.ok(updatedProduct) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        return produtoService.delete(Long.valueOf(id)) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Produto>> listAllProducts() {
        List<Produto> products = produtoService.findAll();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> getProductById(@PathVariable int id) {
        Produto product = produtoService.findById((long) id);
        return product != null ? ResponseEntity.ok(product) : ResponseEntity.notFound().build();
    }

}
