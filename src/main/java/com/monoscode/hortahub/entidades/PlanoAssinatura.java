package com.monoscode.hortahub.entidades;

import com.monoscode.hortahub.enums.Plano;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity(name = "tbl_plano_assinatura")
public class PlanoAssinatura {

    @Id
    private int codigoPlano;

    private double precoSemanal;
    private Plano planoSelecionado;

    public PlanoAssinatura(double precoSemanal, Plano planoSelecionado, int codigoPlano) {
        this.precoSemanal = precoSemanal;
        this.planoSelecionado = planoSelecionado;
        this.codigoPlano = codigoPlano;
    }

    public PlanoAssinatura() {

    }

    public int getCodigoPlano() {
        return codigoPlano;
    }

    public double getPrecoSemanal() {
        return precoSemanal;
    }

    public void setPrecoSemanal(double precoSemanal) {
        this.precoSemanal = precoSemanal;
    }

    public Plano getPlanoSelecionado() {
        return planoSelecionado;
    }

    public void setPlanoSelecionado(Plano planoSelecionado) {
        this.planoSelecionado = planoSelecionado;
    }
}
