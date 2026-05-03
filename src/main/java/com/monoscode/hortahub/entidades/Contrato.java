package com.monoscode.hortahub.entidades;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "tbl_contratos")
public class Contrato {

    @Id
    private long numeroContato;

    @ManyToOne
    @JoinColumn(name = "assinante_id")
    private Assinante assinante;

    @ManyToOne
    @JoinColumn(name = "assinatura_codigo_plano")
    private PlanoAssinatura assinatura;

    public PlanoAssinatura getAssinatura() {
        return assinatura;
    }

    public void setAssinatura(PlanoAssinatura assinatura) {
        this.assinatura = assinatura;
    }

    public Assinante getAssinante() {
        return assinante;
    }

    public void setAssinante(Assinante assinante) {
        this.assinante = assinante;
    }

}
