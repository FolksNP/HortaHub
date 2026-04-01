package com.monoscode.hortahub;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.sql.Date;

@Entity(name = "tbl_frutas")
public class Fruta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int codigoSafra;

    Date safra;
    String nome;
    String especie;
    String classificacao;
    boolean disponibilidade;
    Integer quantidadeEmEstoque;

    public int getCodigoSafra() {
        return codigoSafra;
    }

    public void setCodigoSafra(int codigoSafra) {
        this.codigoSafra = codigoSafra;
    }

    public Date getSafra() {
        return safra;
    }

    public void setSafra(Date safra) {
        this.safra = safra;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(String classificacao) {
        this.classificacao = classificacao;
    }

    public boolean isDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(boolean disponibilidade) {
        this.disponibilidade = disponibilidade;
    }

    public Integer getQuantidadeEmEstoque() {
        return quantidadeEmEstoque;
    }

    public void setQuantidadeEmEstoque(Integer quantidadeEmEstoque) {
        this.quantidadeEmEstoque = quantidadeEmEstoque;
    }
}
