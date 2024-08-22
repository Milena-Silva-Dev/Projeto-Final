package com.mimepapelaria.FrontController;


import com.mimepapelaria.Model.Produto;
import com.mimepapelaria.Service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/home")
    public String mostrarHome(Model model) {
        List<Produto> produtos = produtoService.findAll();
        model.addAttribute("produtos", produtos);
        return "home";
    }
}
