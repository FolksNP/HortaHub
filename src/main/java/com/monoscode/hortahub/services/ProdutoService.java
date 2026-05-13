package com.monoscode.hortahub.services;

import com.monoscode.hortahub.entidades.Produto;
import com.monoscode.hortahub.enums.CategoriaProduto;
import com.monoscode.hortahub.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repositorio;

    public List<Produto> listarTodos() {
        return repositorio.findAll();
    }

    public List<Produto> listarPorCategoria(CategoriaProduto categoria) {
        return repositorio.findByCategoria(categoria);
    }

    public Optional<Produto> buscarPorId(Long id) {
        return repositorio.findById(id);
    }

    @Transactional
    public Produto salvar(Produto produto) {
        return repositorio.save(produto);
    }

    @Transactional
    public void excluir(Long id) {
        repositorio.deleteById(id);
    }

    @Transactional
    public Produto atualizar(Long id, Produto produtoAtualizado) {
        return repositorio.findById(id).map(produto -> {
            produto.setNome(produtoAtualizado.getNome());
            produto.setPreco(produtoAtualizado.getPreco());
            produto.setUnidadeMedida(produtoAtualizado.getUnidadeMedida());
            produto.setSafra(produtoAtualizado.getSafra());
            produto.setCategoria(produtoAtualizado.getCategoria());
            return repositorio.save(produto);
        }).orElseThrow(() -> new RuntimeException("Produto não encontrado"));
    }
}