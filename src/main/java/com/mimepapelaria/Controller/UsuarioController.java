package com.mimepapelaria.Controller;

import com.mimepapelaria.Model.Usuario;
import com.mimepapelaria.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/cadastrar")
    public ResponseEntity<Usuario> cadastrarUsuario(@RequestBody Usuario usuario) {
        Usuario usuarioCriado = usuarioService.criarUsuario(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioCriado);
    }

    @PutMapping("/me")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Usuario> atualizarUsuario(@RequestBody Usuario usuarioAtualizado) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String emailUsuarioAtual = authentication.getName();
        Optional<Usuario> usuarioOptional = usuarioService.buscarUsuarioPorEmail(emailUsuarioAtual);

        if (usuarioOptional.isPresent()) {
            Usuario usuarioExistente = usuarioOptional.get();
            usuarioAtualizado.setId(usuarioExistente.getId());
            Usuario usuarioAtualizadoResponse = usuarioService.atualizarUsuarioParcial(usuarioExistente.getId(), usuarioAtualizado);
            return ResponseEntity.ok(usuarioAtualizadoResponse);
        }
        return ResponseEntity.notFound().build();
    }
}
