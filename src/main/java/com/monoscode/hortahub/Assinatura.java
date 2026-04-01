package com.monoscode.hortahub;

public class Assinatura {

    Planos plano;
    Assinante assinante;
    Endereco endereco;

    Assinatura(Assinante assinante, Endereco endereco, Planos plano){
        this.plano = plano;
        this.assinante = assinante;
        this.endereco = endereco;
    }

}
