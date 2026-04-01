package com.monoscode.hortahub;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "tbl_endereco")
public class Endereco {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer assianteId;
    String cidade;
    String rua;
    int cep;
    String numero;
    String complemento;

}
