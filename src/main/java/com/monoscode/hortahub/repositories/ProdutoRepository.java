package com.monoscode.hortahub.repositories;

import com.monoscode.hortahub.entidades.Produto;
import com.monoscode.hortahub.enums.CategoriaProduto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    public List<Produto> findProdutosByCategoriaProduto(CategoriaProduto categoriaProduto);
}
