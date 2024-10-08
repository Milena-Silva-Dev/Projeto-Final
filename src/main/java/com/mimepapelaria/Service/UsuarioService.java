package com.mimepapelaria.Service;

import com.mimepapelaria.Model.Role;
import com.mimepapelaria.Model.Usuario;
import com.mimepapelaria.Repository.RoleRepository;
import com.mimepapelaria.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Usuario> listarUsuarios() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarUsuarioPorId(int id) {
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> buscarUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    public Usuario criarUsuario(Usuario usuario) {
        Role userRole = roleRepository.findByNome("USER");
        if (userRole == null) {
            throw new RuntimeException("Role 'USER' não encontrada");
        }
        usuario.setRoles(Collections.singletonList(userRole));
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    public Usuario criarAdmin(Usuario usuario) {
        Role adminRole = roleRepository.findByNome("ADMIN");
        if (adminRole == null) {
            throw new RuntimeException("Role 'ADMIN' não encontrada");
        }
        usuario.setRoles(Collections.singletonList(adminRole));
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    @Transactional
    public Usuario atualizarUsuarioParcial(int id, Usuario usuarioAtualizado) {
        Usuario usuarioExistente = usuarioRepository.findById(id).orElse(null);
        if (usuarioExistente == null) {
            return null;
        }
        if (usuarioAtualizado.getNome() != null) {
            usuarioExistente.setNome(usuarioAtualizado.getNome());
        }
        if (usuarioAtualizado.getEmail() != null) {
            usuarioExistente.setEmail(usuarioAtualizado.getEmail());
        }
        if (usuarioAtualizado.getSenha() != null) {
            usuarioExistente.setSenha(passwordEncoder.encode(usuarioAtualizado.getSenha()));
        }
        if (usuarioAtualizado.getCep() != null) {
            usuarioExistente.setCep(usuarioAtualizado.getCep());
        }
        if (usuarioAtualizado.getNumero() != 0) {
            usuarioExistente.setNumero(usuarioAtualizado.getNumero());
        }
        return usuarioRepository.save(usuarioExistente);
    }

    public boolean deletarUsuario(int id) {
        if (usuarioRepository.existsById(id)) {
            usuarioRepository.deleteById(id);
            return true;
        }
        return false;
    }

}

