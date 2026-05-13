package com.monoscode.hortahub.controllers;

import com.monoscode.hortahub.internal_operations.CodigoVerificacao;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@RestController("/verificacao")
public class CodigoVerificacaoController {

    CodigoVerificacao codigo;

    @GetMapping("/enviar/{numero}")
    public String enviarMensagem(@PathVariable String numero) {
        codigo = new CodigoVerificacao(numero);

        try {

            String texto = URLEncoder.encode(
                    codigo.getCodigo(),
                    StandardCharsets.UTF_8
            );

            String url =
                    "https://api.callmebot.com/whatsapp.php" +
                            "?phone=5511951365092" +
                            "&text=" + texto +
                            "&apikey=4892334";

            RestTemplate restTemplate = new RestTemplate();

            String resposta = restTemplate.getForObject(
                    url,
                    String.class
            );

            return resposta;

        } catch (Exception e) {
            return "Erro: " + e.getMessage();
        }
    }

    @GetMapping("/verificar/{code}")
    public Boolean verificarCodigo(@PathVariable String code){
        if (Objects.equals(code, codigo.getCodigo())){
            return true;
        }

        return false;
    }

}
