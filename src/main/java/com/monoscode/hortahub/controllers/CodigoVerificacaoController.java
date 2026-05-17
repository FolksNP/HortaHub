package com.monoscode.hortahub.controllers;

import com.monoscode.hortahub.internal_operations.VerificadorDeCodigo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("/verificacao")
public class CodigoVerificacaoController {
    VerificadorDeCodigo verificadorTelefone;

//    @GetMapping("/enviar/{numero}")
//    public String enviarMensagem(@PathVariable String numero) {
//        codigo = new CodigoVerificacao(numero);
//
//        try {
//            String texto = URLEncoder.encode(
//                    codigo.getCodigo(),
//                    StandardCharsets.UTF_8
//            );
//
//            String url =
//                    "https://api.callmebot.com/whatsapp.php" +
//                            "?phone=5511951365092" +
//                            "&text=" + texto +
//                            "&apikey=4892334";
//
//            RestTemplate restTemplate = new RestTemplate();
//
//            String resposta = restTemplate.getForObject(
//                    url,
//                    String.class
//            );
//
//            return resposta;
//
//        } catch (Exception e) {
//            return "Erro: " + e.getMessage();
//        }
//    }

    @PostMapping("/enviar/{numero}")
    public ResponseEntity<Void> enviarMensagem(@PathVariable String numero) {
        verificadorTelefone = new VerificadorDeCodigo(numero);

        verificadorTelefone.enviarCodigo();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/verificar/{code}")
    public Boolean verificarCodigo(@PathVariable String codigo_cliente){
        return verificadorTelefone.getCodigo().equals(codigo_cliente);
    }

}
