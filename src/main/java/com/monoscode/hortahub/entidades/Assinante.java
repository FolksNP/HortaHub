package com.monoscode.hortahub.entidades;

import com.monoscode.hortahub.repositories.PlanoAssinaturaRepository;
import jakarta.persistence.*;

import java.util.Objects;

@Entity(name = "tbl_assinante")
public class Assinante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String nome;
    private String sobrenome;
    private String cpf;
    private String telefone;
    private Endereco endereco;
//    private Cesta cestaSemanal; Esse registro deve ser mantido nessa entidade?

    @ManyToOne
    @JoinColumn(name = "plano_assinatura_id")
    private PlanoAssinatura planoAssinatura; // Onde entra a classe associativa "Contrato" aqui?

    public Assinante(/*long id, */ String nome, String sobrenome, String cpf, String telefone) {
        //this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.cpf = cpf;
        this.telefone = telefone;
    }

    public Assinante() {

    }

    public void selecionarPlanoAssinatura(PlanoAssinatura plano) {
        this.planoAssinatura = plano;
    }

    public long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public PlanoAssinatura getPlanoAssinatura() {
        return planoAssinatura;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Assinante assinante = (Assinante) o;
        return id == assinante.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
