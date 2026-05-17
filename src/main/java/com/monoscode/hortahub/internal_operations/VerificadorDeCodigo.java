package com.monoscode.hortahub.internal_operations;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.springframework.web.client.RestClient;

import java.util.Random;

@Entity(name = "tbl_sandbox_codigos_verificacao")
public class VerificadorDeCodigo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int codigo;
    private String numeroDeTelefoneAssociado;

    public VerificadorDeCodigo(String numeroDeTelefoneAssociado){
        this.numeroDeTelefoneAssociado = numeroDeTelefoneAssociado;
        this.codigo = gerarCodigoVerificacao();
    }

    private int gerarCodigoVerificacao(){
        Random rd = new Random();
        return rd.nextInt(111111, 999999);
    }

    public void enviarCodigo() {
        RestClient restClient = RestClient.create();
        restClient.get()
                .uri("https://api.callmebot.com/whatsapp.php?phone=" + this.numeroDeTelefoneAssociado +"&text=" + codigo + "&apikey=4892334")
                .retrieve()
                .body(String.class);
    }

    public VerificadorDeCodigo(){

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
