package com.monoscode.hortahub.controllers;

import com.monoscode.hortahub.entidades.PlanoAssinatura;
import com.monoscode.hortahub.services.PlanoAssinaturaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/planos")
public class PlanoAssinaturaController {
    PlanoAssinaturaService planoService;

//    public PlanoAssinaturaController() {}

    public PlanoAssinaturaController(PlanoAssinaturaService planoService) {
        this.planoService = planoService;
    }


    @GetMapping // Ou mapear para /planos/all
    public ResponseEntity<List<PlanoAssinatura>> listarTodos() {
        return ResponseEntity.ok(planoService.listarTodos());
    }
}
