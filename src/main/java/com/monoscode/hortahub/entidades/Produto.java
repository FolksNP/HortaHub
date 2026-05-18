package com.monoscode.hortahub.testador.src.main.java.com.monoscode.hortahub.entidades;

import com.monoscode.hortahub.enums.CategoriaProduto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.sql.Date;
import java.util.Objects;

@Entity(name = "tbl_produto")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String unidadeMedida;
    private Double preco;
    private Date safra;
    private CategoriaProduto categoria;

    public Produto(String nome, String unidadeMedida, Double preco, Date safra, CategoriaProduto categoria) {
        this.nome = nome;
        this.unidadeMedida = unidadeMedida;
        this.preco = preco;
        this.safra = safra;
        this.categoria = categoria;
    }

    public Produto(){

    }

    public Long getId() {
        return id;
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Date getSafra() {
        return safra;
    }

    public void setSafra(Date safra) {
        this.safra = safra;
    }

    public CategoriaProduto getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaProduto categoria) {
        this.categoria = categoria;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return id == produto.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
