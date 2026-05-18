package com.monoscode.hortahub.testador.src.test.java.com.monoscode.hortahub;

import com.monoscode.hortahub.internal_operations.VerificadorDeCodigoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("VerificadorDeCodigoService — Passos 1 a 4 do caso de uso")
class VerificadorDeCodigoServiceTest {

    private VerificadorDeCodigoService service;

    @BeforeEach
    void setup() {
        service = new VerificadorDeCodigoService();
    }

    @Test
    @DisplayName("Passo 4: Código correto deve ser aceito")
    void deveAceitarCodigoCorreto() {
        service.adicionarCodigoParaTeste("5511999990000", "112233");
        assertTrue(service.verificarCodigo("5511999990000", "112233"));
    }

    @Test
    @DisplayName("Passo 4: Código errado deve ser rejeitado")
    void deveRejeitarCodigoErrado() {
        service.adicionarCodigoParaTeste("5511999990000", "112233");
        assertFalse(service.verificarCodigo("5511999990000", "000000"));
    }

    @Test
    @DisplayName("Código não pode ser reutilizado após validação bem-sucedida")
    void codigoDeveSerDescartadoAposUso() {
        service.adicionarCodigoParaTeste("5511999990001", "999888");
        service.verificarCodigo("5511999990001", "999888");
        assertFalse(service.verificarCodigo("5511999990001", "999888"),
                "Segunda tentativa com mesmo código deve falhar");
    }

    @Test
    @DisplayName("Telefone sem código cadastrado retorna false")
    void telefoneSemCodigoDeveRetornarFalse() {
        assertFalse(service.verificarCodigo("5511000000000", "123456"));
    }

    @Test
    @DisplayName("Códigos de telefones diferentes são independentes")
    void codigosDeTelesonesDiferentesSaoIndependentes() {
        service.adicionarCodigoParaTeste("5511111111111", "111111");
        service.adicionarCodigoParaTeste("5511222222222", "222222");

        assertFalse(service.verificarCodigo("5511111111111", "222222"), "Código do outro telefone não deve funcionar");
        assertTrue(service.verificarCodigo("5511111111111", "111111"));
        assertTrue(service.verificarCodigo("5511222222222", "222222"));
    }
}
