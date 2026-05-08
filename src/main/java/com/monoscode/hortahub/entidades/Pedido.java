package com.monoscode.hortahub.entidades;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Objects;

@Entity(name = "tbl_pedidos")
public class Pedido {

    @Id
    private String protocolo;

    private String status;
    private Double valorTotal;
    private Date dataRegistro;

    @OneToOne
    @JoinColumn(name = "cesta_id")
    private Cesta cesta;

    public Pedido(String protocolo, String status, Double valorTotal, Date dataRegistro, Cesta cesta) {
        this.protocolo = protocolo;
        this.status = status;
        this.valorTotal = valorTotal;
        this.dataRegistro = dataRegistro;
        this.cesta = cesta;
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
