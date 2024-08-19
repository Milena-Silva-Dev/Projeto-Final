package com.mimepapelaria.Repository;

import com.mimepapelaria.Model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Role findByNome(String name);
}
