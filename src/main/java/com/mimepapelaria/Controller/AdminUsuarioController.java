package com.mimepapelaria.Controller;

import com.mimepapelaria.Model.Usuario;
import com.mimepapelaria.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin/usuarios")
public class AdminUsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/listar")
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarUsuarioPorId(@PathVariable int id) {
        Optional<Usuario> usuarioOptional = usuarioService.buscarUsuarioPorId(id);
        return usuarioOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Usuario> cadastrarAdmin(@RequestBody Usuario usuario) {
        Usuario novoAdmin = usuarioService.criarAdmin(usuario);
        return ResponseEntity.ok(novoAdmin);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> atualizarUsuarioAdmin(@PathVariable int id, @RequestBody Usuario usuarioAtualizado) {
        Usuario usuarioAtualizadoResponse = usuarioService.atualizarUsuarioParcial(id, usuarioAtualizado);
        return usuarioAtualizadoResponse != null ? ResponseEntity.ok(usuarioAtualizadoResponse) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarUsuario(@PathVariable int id) {
        return usuarioService.deletarUsuario(id) ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
