package com.monoscode.hortahub.controllers;

import com.monoscode.hortahub.internal_operations.VerificadorDeCodigo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/verificacao")
public class CodigoVerificacaoController {
    VerificadorDeCodigo verificadorTelefone;

    @GetMapping("/enviar/{numero}")
    public ResponseEntity<Void> enviarMensagem(@PathVariable String numero) {
        verificadorTelefone = new VerificadorDeCodigo(numero);

        verificadorTelefone.enviarCodigo();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/verificar/{codigo_cliente}")
    public ResponseEntity<Boolean> verificarCodigo(@PathVariable String codigo_cliente){
        Boolean codigoCorreto = verificadorTelefone.getCodigo().equals(codigo_cliente);
        return ResponseEntity.ok(codigoCorreto);
    }

}
