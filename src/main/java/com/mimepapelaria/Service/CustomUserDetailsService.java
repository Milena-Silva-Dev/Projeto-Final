package com.mimepapelaria.Service;

import com.mimepapelaria.Model.Usuario;
import com.mimepapelaria.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import com.mimepapelaria.Model.Role;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      Usuario usuario = usuarioRepository.findByEmail(username)
        .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado com e-mail: " + username));

      String[] roles = usuario.getRoles().stream()
        .map(Role::getNome)
        .toArray(String[]::new);

      return User.builder()
        .username(usuario.getEmail())
        .password(usuario.getSenha())
        .roles(roles)
        .build();
   }
}
