package com.mimepapelaria.FrontController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UsuarioPages {

  @GetMapping("/api/usuarios/cadastrar")
  public String cadastro() {
    return "cadastro"; // Nome do arquivo HTML sem a extensão .html
  }

}
