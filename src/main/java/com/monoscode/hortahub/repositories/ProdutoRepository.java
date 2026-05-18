package com.monoscode.hortahub.testador.src.main.java.com.monoscode.hortahub.repositories;

import com.monoscode.hortahub.entidades.Assinante;
import com.monoscode.hortahub.entidades.Produto;
import com.monoscode.hortahub.enums.CategoriaProduto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    List<Produto> findByCategoria(CategoriaProduto categoria);

}
