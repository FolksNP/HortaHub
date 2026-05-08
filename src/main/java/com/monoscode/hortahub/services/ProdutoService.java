package com.monoscode.hortahub.services;

import com.monoscode.hortahub.entidades.Produto;
import com.monoscode.hortahub.enums.CategoriaProduto;
import com.monoscode.hortahub.repositories.ProdutoRepository;

import java.util.List;

public class ProdutoService {
    private ProdutoRepository produtoRepository;

    public List<Produto> listarPorCategoria(CategoriaProduto categoria) {
        return produtoRepository.findProdutosByCategoriaProduto(categoria);
    }
}
