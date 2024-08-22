package com.mimepapelaria.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "produto")
public class Produto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "nome", nullable = false, length = 50)
    private String nome;

    @Column(name = "quantidade", nullable = false)
    private int quantidade;

    @Column(name = "descricao", nullable = false, length = 255)
    private String descricao;

    @Lob
    @Column(name = "imagem")
    private String imagem;

    @Column(name = "preco", nullable = false)
    private double preco;

    @Column(name = "categoria", nullable = false, length = 50)
    private String categoria;

    @Column(name = "estoque", nullable = false)
    private int estoque;

    public boolean hasStock(int quantity) {
        return estoque >= quantity;
    }

    public void updateStock(int quantity) {
        this.estoque -= quantity;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", quantidade=" + quantidade +
                ", descricao='" + descricao + '\'' +
                ", preco=" + preco +
                ", categoria='" + categoria + '\'' +
                '}';
    }
}
