package com.mimepapelaria.Controller;

import com.mimepapelaria.Model.Usuario;
import com.mimepapelaria.Service.UsuarioService;
import com.mimepapelaria.Service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtService jwtService;

    @GetMapping("/me")
    public ResponseEntity<Usuario> obterUsuarioLogado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            System.out.println("Principal: " + principal);
            System.out.println("Principal Type: " + principal.getClass().getName());
            if (principal instanceof UserDetails) {
                UserDetails userDetails = (UserDetails) principal;
                String email = userDetails.getUsername();
                Optional<Usuario> usuario = usuarioService.buscarUsuarioPorEmail(email);
                return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizarUsuario(@PathVariable int id, @RequestBody Usuario usuarioAtualizado) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String emailUsuarioAtual = authentication.getName();
        Optional<Usuario> usuarioOptional = usuarioService.buscarUsuarioPorEmail(emailUsuarioAtual);

        if (usuarioOptional.isPresent()) {
            Usuario usuarioExistente = usuarioOptional.get();
            if (usuarioExistente.getId() != id) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            usuarioAtualizado.setId(id);
            Usuario usuarioAtualizadoResponse = usuarioService.atualizarUsuarioParcial(id, usuarioAtualizado);
            return ResponseEntity.ok(usuarioAtualizadoResponse);
        }

        return ResponseEntity.notFound().build();
    }
}