package com.monoscode.hortahub.testador.src.main.java.com.monoscode.hortahub.entidades;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tbl_plano_assinatura")
public class PlanoAssinatura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(length = 500)
    private String descricao;

    @Column(nullable = false)
    private Double valor;

    @Column(nullable = false)
    private Integer quantidadeFrutas;

    @Column(nullable = false)
    private Integer quantidadeLegumes;

    @Column(nullable = false)
    private Integer quantidadeVerduras;

    @OneToMany(mappedBy = "planoAssinatura")
    private List<Pedido> pedidos = new ArrayList<>();

    public PlanoAssinatura() {
    }

    public PlanoAssinatura(String nome,
                           String descricao,
                           Double valor,
                           Integer quantidadeFrutas,
                           Integer quantidadeLegumes,
                           Integer quantidadeVerduras) {
        this.nome = nome;
        this.descricao = descricao;
        this.valor = valor;
        this.quantidadeFrutas = quantidadeFrutas;
        this.quantidadeLegumes = quantidadeLegumes;
        this.quantidadeVerduras = quantidadeVerduras;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Integer getQuantidadeFrutas() {
        return quantidadeFrutas;
    }

    public void setQuantidadeFrutas(Integer quantidadeFrutas) {
        this.quantidadeFrutas = quantidadeFrutas;
    }

    public Integer getQuantidadeLegumes() {
        return quantidadeLegumes;
    }

    public void setQuantidadeLegumes(Integer quantidadeLegumes) {
        this.quantidadeLegumes = quantidadeLegumes;
    }

    public Integer getQuantidadeVerduras() {
        return quantidadeVerduras;
    }

    public void setQuantidadeVerduras(Integer quantidadeVerduras) {
        this.quantidadeVerduras = quantidadeVerduras;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlanoAssinatura that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
