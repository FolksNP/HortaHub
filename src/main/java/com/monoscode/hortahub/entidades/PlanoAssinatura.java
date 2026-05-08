package com.monoscode.hortahub.entidades;

import com.monoscode.hortahub.enums.Plano;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/*
TODO: Tô achando essa implementação meio ambígua
Acredito que o correto seria remover a enum Plano e transformá-la apenas em um atributo "nomePlano"
Outra alternativa é transformar em um enum com atributos mantendo os atributos dessa classe, mas
todos os plano ficariam hardcodados

Dessa forma, Assinante teria uma associação com PlanoAssinatura (essa classe) e planoSelecionado seria desnecessário
*/

@Entity(name = "tbl_plano_assinatura")
public class PlanoAssinatura {

    @Id
    private int codigoPlano;

    private double precoSemanal;
    private String nome;
    private List<LimiteCategoria> limitesPorCategoria;

    public PlanoAssinatura(double precoSemanal, int codigoPlano, String nome) {
        this.precoSemanal = precoSemanal;
        this.codigoPlano = codigoPlano;
        this.nome = nome;
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
}
