package com.monoscode.hortahub.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.sql.Date;
import java.util.Objects;

@Entity(name = "tbl_pedidos")
public class Pedido {

    @Id
    private String protocolo;

    private String status;
    private Double valorTotal;
    private Date dataRegistro;

    public Pedido(String protocolo, String status, Double valorTotal, Date dataRegistro) {
        this.protocolo = protocolo;
        this.status = status;
        this.valorTotal = valorTotal;
        this.dataRegistro = dataRegistro;
    }

    public Pedido() {

    }

    public String getProtocolo() {
        return protocolo;
    }

    public void setProtocolo(String protocolo) {
        this.protocolo = protocolo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Date getDataRegistro() {
        return dataRegistro;
    }

    public void setDataRegistro(Date dataRegistro) {
        this.dataRegistro = dataRegistro;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return Objects.equals(protocolo, pedido.protocolo);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(protocolo);
    }
}
