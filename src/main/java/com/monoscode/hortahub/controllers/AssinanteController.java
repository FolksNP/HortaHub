package com.monoscode.hortahub.controllers;

import com.monoscode.hortahub.entidades.Assinante;
import com.monoscode.hortahub.services.AssinanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/assinantes")
public class AssinanteController {

    @Autowired
    private AssinanteService servico;

    @GetMapping
    public List<Assinante> listar() {
        return servico.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Assinante> buscar(@PathVariable Long id) {
        return servico.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cpf/{cpf}")
    public ResponseEntity<Assinante> buscarPorCpf(@PathVariable String cpf) {
        return servico.buscarPorCpf(cpf)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Assinante adicionar(@RequestBody Assinante assinante) {
        return servico.salvar(assinante);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Assinante> atualizar(@PathVariable Long id, @RequestBody Assinante assinante) {
        try {
            return ResponseEntity.ok(servico.atualizar(id, assinante));
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