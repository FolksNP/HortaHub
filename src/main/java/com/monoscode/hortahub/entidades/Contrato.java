package com.monoscode.hortahub.entidades;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "tbl_contrato")
public class Contrato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate dataInicio;

    @Column
    private LocalDate dataFim;

    @Column(nullable = false)
    private String status;

    @OneToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "assinante_id", nullable = false)
    private Assinante assinante;

    public Contrato() {
    }

    public Contrato(LocalDate dataInicio,
                    LocalDate dataFim,
                    String status,
                    Pedido pedido,
                    Assinante assinante) {
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.status = status;
        this.pedido = pedido;
        this.assinante = assinante;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Assinante getAssinante() {
        return assinante;
    }

    public void setAssinante(Assinante assinante) {
        this.assinante = assinante;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contrato contrato)) return false;
        return Objects.equals(id, contrato.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}