package com.monoscode.hortahub.controllers;

import com.monoscode.hortahub.entidades.Produto;
import com.monoscode.hortahub.enums.CategoriaProduto;
import com.monoscode.hortahub.services.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService servico;

    @GetMapping
    public List<Produto> listar() {
        return servico.listarTodos();
    }

    @GetMapping("/categoria/{categoria}")
    public List<Produto> listarPorCategoria(@PathVariable CategoriaProduto categoria) {
        return servico.listarPorCategoria(categoria);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> buscar(@PathVariable Long id) {
        return servico.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Produto adicionar(@RequestBody Produto produto) {
        return servico.salvar(produto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Produto> atualizar(@PathVariable Long id, @RequestBody Produto produto) {
        try {
            return ResponseEntity.ok(servico.atualizar(id, produto));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        if (servico.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        servico.excluir(id);
        return ResponseEntity.noContent().build();
    }
}