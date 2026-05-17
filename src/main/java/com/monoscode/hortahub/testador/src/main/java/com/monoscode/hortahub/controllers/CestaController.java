package com.monoscode.hortahub.testador.src.main.java.com.monoscode.hortahub.controllers;

import com.monoscode.hortahub.entidades.Cesta;
import com.monoscode.hortahub.services.CestaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cestas")
public class CestaController {

    @Autowired
    private CestaService servico;

    @GetMapping
    public List<Cesta> listar() {
        return servico.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cesta> buscar(@PathVariable Long id) {
        return servico.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cesta criar(@RequestBody Cesta cesta) {
        return servico.salvar(cesta);
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