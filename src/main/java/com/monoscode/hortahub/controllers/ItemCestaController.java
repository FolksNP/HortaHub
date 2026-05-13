package com.monoscode.hortahub.controllers;

import com.monoscode.hortahub.entidades.ItemCesta;
import com.monoscode.hortahub.services.ItemCestaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/itens-cesta")
public class ItemCestaController {

    @Autowired
    private ItemCestaService servico;

    @GetMapping("/cesta/{cestaId}")
    public List<ItemCesta> listarPorCesta(@PathVariable Long cestaId) {
        return servico.listarPorCesta(cestaId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ItemCesta adicionarItem(@RequestBody ItemCesta item) {
        return servico.adicionar(item);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerItem(@PathVariable Long id) {
        if (servico.buscarPorId(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        servico.remover(id);
        return ResponseEntity.noContent().build();
    }
}