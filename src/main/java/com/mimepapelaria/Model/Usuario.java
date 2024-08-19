package com.mimepapelaria.Model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuario", uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "cpf")
})
public class Usuario implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "usuario_role", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "role_id"), uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id", "role_id"}))
    @JsonProperty("roles")
    @JsonIgnoreProperties(value = { "usuarios" })
    private List<Role> roles = new ArrayList<>();

    @Column(name = "nome", nullable = false, length = 50)
    private String nome;

    @Column(name = "email", nullable = false, length = 50, unique = true)
    private String email;

    @Column(name = "senha", nullable = false, length = 60)
    private String senha;

    @Column(name = "cpf", nullable = false, length = 11, unique = true)
    private String cpf;

    @Column(name = "rg", nullable = false, length = 9)
    private String rg;

    @Column(name = "rua", nullable = false, length = 50)
    private String rua;

    @Column(name = "numero", nullable = false)
    private int numero;

    @Column(name = "cidade", nullable = false, length = 50)
    private String cidade;

    @Column(name = "municipio", nullable = false, length = 50)
    private String municipio;

    @Column(name = "cep", nullable = false, length = 8)
    private String cep;

    @Column(name = "uf", nullable = false, length = 2)
    private String uf;

    @Override
    public String toString() {
      return "Usuario{" +
        "id=" + id +
        ", nome='" + nome + '\'' +
        ", email='" + email + '\'' +
        ", senha='" + senha + '\'' +
        ", cpf='" + cpf + '\'' +
        ", rg='" + rg + '\'' +
        ", rua='" + rua + '\'' +
        ", numero=" + numero +
        ", cidade='" + cidade + '\'' +
        ", municipio='" + municipio + '\'' +
        ", cep='" + cep + '\'' +
        ", uf='" + uf + '\'' +
        '}';
    }
}
