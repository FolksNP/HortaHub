package com.monoscode.hortahub.testador.src.main.java.com.monoscode.hortahub.controllers;

import com.monoscode.hortahub.entidades.Endereco;
import com.monoscode.hortahub.services.EnderecoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/enderecos")
public class EnderecoController {

    @Autowired
    private EnderecoService servico;

    @GetMapping
    public List<Endereco> listar() {
        return servico.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Endereco> buscar(@PathVariable Long id) {
        return servico.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/assinante/{assinanteId}")
    public List<Endereco> listarPorAssinante(@PathVariable Long assinanteId) {
        return servico.buscarPorAssinante(assinanteId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Endereco adicionar(@RequestBody Endereco endereco) {
        return servico.salvar(endereco);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Endereco> atualizar(@PathVariable Long id, @RequestBody Endereco endereco) {
        try {
            return ResponseEntity.ok(servico.atualizar(id, endereco));
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