package com.mimepapelaria.Controller;

import com.mimepapelaria.Model.Produto;
import com.mimepapelaria.Service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @Controller
    public class ProdutoViewController {

        @GetMapping("/produto/{id}")
        public String getProdutoView(@PathVariable Long id, Model model) {
            Produto produto = productService.findById(id);
            if (produto == null) {
                return "redirect:/error"; // Redirecionar para uma página de erro
            }
            model.addAttribute("produto", produto);
            return "produto";
        }
    }


}
