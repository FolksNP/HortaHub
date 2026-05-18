package com.monoscode.hortahub.entidades;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "tbl_endereco")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 8) // 00000000 -> (00000-000)
    private String cep;

    @Column(nullable = false)
    private String logradouro;

    @Column(nullable = false)
    private Integer numero;

    private String referencia;

    @ManyToOne
    @JoinColumn(name = "assinante_id", nullable = false)
    private Assinante assinante;

    public Endereco() {

    }

    public Endereco(String cep, String logradouro, Integer numero,
                    String referencia, Assinante assinante) {
        this.cep = cep;
        this.logradouro = logradouro;
        this.numero = numero;
        this.referencia = referencia;
        this.assinante = assinante;
    }

    public Long getId() {
        return id;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public Assinante getAssinante() {
        return assinante;
    }

    public void setAssinante(Assinante assinante) {
        this.assinante = assinante;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Endereco endereco)) return false;
        return Objects.equals(id, endereco.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
