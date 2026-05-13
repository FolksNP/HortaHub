package com.monoscode.hortahub.entidades;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tbl_cesta")
public class Cesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @OneToMany(mappedBy = "cesta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemCesta> itens = new ArrayList<>();

    public Cesta() {
    }

    public Cesta(Pedido pedido) {
        this.pedido = pedido;
    }

    public Long getId() {
        return id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public List<ItemCesta> getItens() {
        return itens;
    }

    public void setItens(List<ItemCesta> itens) {
        this.itens = itens;
    }



    public void adicionarItem(ItemCesta item) {
        itens.add(item);
        item.setCesta(this);
    }

    public void removerItem(ItemCesta item) {
        itens.remove(item);
        item.setCesta(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cesta cesta)) return false;
        return Objects.equals(id, cesta.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}