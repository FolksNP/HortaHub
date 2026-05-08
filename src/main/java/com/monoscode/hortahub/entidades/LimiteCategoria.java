package com.monoscode.hortahub.entidades;

import com.monoscode.hortahub.enums.CategoriaProduto;

// Isso aqui ainda tá com cheirinho de gambiarra
// TODO: Ainda penso se poderia virar alguma Collection, talvez um Map
// O problema é que não sei como isso ficaria representado no banco, já que os limites são estáticos pra cada plano

public class LimiteCategoria {
    private double limite;
    private CategoriaProduto categoria;

    public LimiteCategoria(double limite, CategoriaProduto categoria) {
        this.limite = limite;
        this.categoria = categoria;
    }

    public double getLimite() {
        return limite;
    }

    public CategoriaProduto getCategoria() {
        return categoria;
    }
}
