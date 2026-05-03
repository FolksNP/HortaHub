package com.monoscode.hortahub.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity(name = "tbl_item_cesta")
public class ItemCesta {

    @Id
    private Long id;

    private int quantidade;
    private Produto produto;

    public ItemCesta(int quantidade, Produto produto){
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public ItemCesta() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ItemCesta itemCesta = (ItemCesta) o;
        return Objects.equals(id, itemCesta.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
