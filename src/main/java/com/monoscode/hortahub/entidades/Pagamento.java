package com.monoscode.hortahub.entidades;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "tbl_pagamento")
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double valor;

    @Column(nullable = false)
    private LocalDateTime dataPagamento;

    @Column(nullable = false)
    private String status;


    @ManyToOne
    @JoinColumn(name = "assinante_id", nullable = false)
    private Assinante assinante;

    @OneToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    public Pagamento() {
    }

    public Pagamento(Double valor,
                     LocalDateTime dataPagamento,
                     String status,
                     Assinante assinante,
                     Pedido pedido) {
        this.valor = valor;
        this.dataPagamento = dataPagamento;
        this.status = status;
        this.assinante = assinante;
        this.pedido = pedido;
    }


    public Long getId() {
        return id;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public LocalDateTime getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDateTime dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Assinante getAssinante() {
        return assinante;
    }

    public void setAssinante(Assinante assinante) {
        this.assinante = assinante;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pagamento that)) return false;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}