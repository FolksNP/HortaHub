package com.monoscode.hortahub.entidades;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "cestas")
public class Cesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private List<ItemCesta> itensSelecionados;

    public Cesta() {
    }

    public void adicionarItem(ItemCesta item) {
        itensSelecionados.addLast(item);
    }
}
