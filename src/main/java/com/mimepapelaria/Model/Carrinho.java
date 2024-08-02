package com.mimepapelaria.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "carrinho")
public class Carrinho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @Column(name = "quantidade", nullable = false)
    private int quantidade;

    @Column(name = "preco", nullable = false)
    private double preco;

    @Override
    public String toString() {
        return "Carrinho{" +
                "id=" + id +
                ", usuario=" + usuario +
                ", produto=" + produto +
                ", pedido=" + pedido +
                ", quantidade=" + quantidade +
                ", preco=" + preco +
                '}';
    }
}
