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
@Table(name = "forma_pagamento")
public class FormaPagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "descricao", nullable = false, length = 50)
    private String descricao;

    @Override
    public String toString() {
        return "FormaPagamento{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
