package com.monoscode.hortahub.internal_operations;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Random;

@Entity(name = "tbl_sandbox_codigos_verificacao")
public class CodigoVerificacao {



    private int gerarNumero(){
        Random rd = new Random();
        return rd.nextInt(111111, 999999);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int codigo;
    private String numeroDeTelefoneAssociado;

    public CodigoVerificacao(String numeroDeTelefoneAssociado){
        this.numeroDeTelefoneAssociado = numeroDeTelefoneAssociado;
        this.codigo = gerarNumero();
    }

    public CodigoVerificacao(){

    }

    public String getCodigo() {
        return String.valueOf(codigo);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumeroDeTelefoneAssociado() {
        return numeroDeTelefoneAssociado;
    }

    public void setNumeroDeTelefoneAssociado(String numeroDeTelefoneAssociado) {
        this.numeroDeTelefoneAssociado = numeroDeTelefoneAssociado;
    }
}
