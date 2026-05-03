package com.monoscode.hortahub.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.sql.Date;

@Entity(name = "tbl_pagamento")
public class Pagamento {

    @Id
    private int idPagamento;

    private Date pagamentoRealizado;
    private Double pagamento;
    private Boolean aprovado;

    public Pagamento(Date pagamentoRealizado, Double pagamento, Boolean aprovado) {
        this.pagamentoRealizado = pagamentoRealizado;
        this.pagamento = pagamento;
        this.aprovado = aprovado;
    }

    public Pagamento() {

    }

    public Date getPagamentoRealizado() {
        return pagamentoRealizado;
    }

    public void setPagamentoRealizado(Date pagamentoRealizado) {
        this.pagamentoRealizado = pagamentoRealizado;
    }

    public Double getPagamento() {
        return pagamento;
    }

    public void setPagamento(Double pagamento) {
        this.pagamento = pagamento;
    }

    public Boolean getAprovado() {
        return aprovado;
    }

    public void setAprovado(Boolean aprovado) {
        this.aprovado = aprovado;
    }
}
