package com.monoscode.hortahub.controllers;

import com.monoscode.hortahub.internal_operations.VerificadorDeCodigoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/verificacao")
public class CodigoVerificacaoController {
    VerificadorDeCodigoService verificadorTelefoneService;

    @GetMapping("/enviar/{numero}")
    public ResponseEntity<Void> enviarMensagem(@PathVariable String numero) {
        verificadorTelefoneService.enviarCodigo(numero);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/verificar/{numero}/{codigo_cliente}")
    public ResponseEntity<Boolean> verificarCodigo(@PathVariable String numero, @PathVariable String codigo_cliente){
        Boolean codigoCorreto = verificadorTelefoneService.verificarCodigo(numero, codigo_cliente);
        return ResponseEntity.ok(codigoCorreto);
    }

}
