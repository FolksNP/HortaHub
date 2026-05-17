package com.monoscode.hortahub.testador.src.main.java.com.monoscode.hortahub.controllers;

import com.monoscode.hortahub.entidades.Contrato;
import com.monoscode.hortahub.services.ContratoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contratos")
public class ContratoController {

    @Autowired
    private ContratoService servico;

    @GetMapping
    public List<Contrato> listar() {
        return servico.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contrato> buscar(@PathVariable Long id) {
        return servico.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/assinante/{assinanteId}")
    public List<Contrato> listarPorAssinante(@PathVariable Long assinanteId) {
        return servico.buscarPorAssinante(assinanteId);
    }

    @PostMapping
    public ResponseEntity<Contrato> criar(@RequestBody Contrato contrato) {
        try {
            Contrato novoContrato = servico.gerarContrato(contrato);
            return ResponseEntity.status(HttpStatus.CREATED).body(novoContrato);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/{id}/encerrar")
    public ResponseEntity<Contrato> encerrar(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(servico.encerrarContrato(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}