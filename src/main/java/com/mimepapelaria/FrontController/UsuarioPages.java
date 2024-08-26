package com.mimepapelaria.FrontController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsuarioPages {

  @GetMapping("/api/usuarios/cadastrar")
  public String cadastro() {
    return "cadastro";
  }

  @GetMapping("/api/auth/login")
  public String login() {
    return "login";
  }

  @GetMapping("/carrinho")
  public String carrinho() {
    return "carrinho";
  }

}
