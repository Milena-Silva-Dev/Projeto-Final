package com.mimepapelaria.Service;

import com.mimepapelaria.Model.Role;
import com.mimepapelaria.Model.Usuario;
import com.mimepapelaria.Repository.RoleRepository;
import com.mimepapelaria.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

  private final RoleRepository roleRepository;

  private final UsuarioRepository usuarioRepository;

  @Autowired
  public RoleService(RoleRepository roleRepository, UsuarioRepository usuarioRepository) {
    this.roleRepository = roleRepository;
    this.usuarioRepository = usuarioRepository;
  }

  public List<Role> findAll() {
    try {
      return roleRepository.findAll();
    } catch (Exception e) {
      throw new RuntimeException("Falha ao buscar todos os perfis: ", e);
    }
  }

  public Role findById(Long id) {
    return roleRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Não existe perfil de usuário com o id: " + id));
  }

  public Role save(Role role) {
    try {
      return roleRepository.save(role);
    } catch (Exception e) {
      throw new RuntimeException("Falha ao salvar o perfil: ", e);
    }
  }

  public Role update(Long id, Role role) {
    Role existingRole = roleRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Não existe perfil de usuário com o id: " + id));
    existingRole.setNome(role.getNome());
    try {
      return roleRepository.save(existingRole);
    } catch (Exception e) {
      throw new RuntimeException("Falha ao atualizar o perfil: ", e);
    }
  }

  public void delete(Long id) {
    Role role = roleRepository.findById(id)
      .orElseThrow(() -> new RuntimeException("Não existe perfil de usuário com o id: " + id));
    List<Usuario> usuarios = usuarioRepository.findAll();
    for (Usuario usuario : usuarios) {
      if (usuario.getRoles().contains(role)) {
        throw new RuntimeException("Não é possível deletar o perfil com id: " + id + " porque ele possui usuários associados.");
      }
    }
    try {
      roleRepository.deleteById(id);
    } catch (Exception e) {
      throw new RuntimeException("Falha ao deletar o perfil: ", e);
    }
  }

  public Role findByNome(String nome) {
    try {
      return roleRepository.findByNome(nome);
    } catch (Exception e) {
      throw new RuntimeException("Não existe perfil de usuário com nome: ", e);
    }
  }

}
