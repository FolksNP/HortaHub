package com.monoscode.hortahub.entidades;

import com.monoscode.hortahub.enums.CategoriaProduto;
import jakarta.persistence.*;

@Entity
@Table(name = "produtos")
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String unidadeMedida;
    private Double preco;
    private String safra;
    private CategoriaProduto categoriaProduto;


}
