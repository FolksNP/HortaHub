package com.monoscode.hortahub.internal_operations;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class VerificadorDeCodigoService {
    private final Map<String, String> codigosEmMemoria = new ConcurrentHashMap<>();

//    public VerificadorDeCodigo(String numeroDeTelefoneAssociado){
//        this.numeroDeTelefoneAssociado = numeroDeTelefoneAssociado;
//        this.codigo = gerarCodigoVerificacao();
//    }

    private static String gerarCodigoVerificacao() {
        Random rd = new Random();
        return String.valueOf(rd.nextInt(111111, 999999));
    }

    public void enviarCodigo(String numeroDeTelefone) {
        String codigoGerado = gerarCodigoVerificacao();

        codigosEmMemoria.put(numeroDeTelefone, codigoGerado);

        RestClient restClient = RestClient.create();
        restClient.get()
                .uri("https://api.callmebot.com/whatsapp.php?phone=" + numeroDeTelefone + "&text=" + codigoGerado + "&apikey=5122341")
                .retrieve()
                .body(String.class);
    }

    public boolean verificarCodigo(String numeroDeTelefone, String codigoCliente) {
        // Busca o código salvo para ESSE número de telefone
        String codigoSalvo = codigosEmMemoria.get(numeroDeTelefone);

        if (codigoSalvo != null && codigoSalvo.equals(codigoCliente)) {
            // Código correto! Removemos da memória para não ser usado de novo
            codigosEmMemoria.remove(numeroDeTelefone);
            return true;
        }

        return false;
    }

    public VerificadorDeCodigoService(){

    }

    // Apenas para uso em testes — permite injetar um código sem chamar a API externa
    public void adicionarCodigoParaTeste(String numeroDeTelefone, String codigo) {
        codigosEmMemoria.put(numeroDeTelefone, codigo);
    }
}
