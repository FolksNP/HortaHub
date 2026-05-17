package com.monoscode.hortahub.testador.src.test.java.com.monoscode.hortahub.service;

import com.monoscode.hortahub.entidades.*;
import com.monoscode.hortahub.repositories.ContratoRepository;
import com.monoscode.hortahub.services.ContratoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ContratoService — Passo 22 (geração de contrato)")
class ContratoServiceTest {

    @Mock private ContratoRepository repo;
    @InjectMocks private ContratoService service;

    private Assinante assinante() {
        return new Assinante("Lucia", "Ferreira", "77766655544", "11955550000");
    }

    private Pedido pedido() {
        PlanoAssinatura plano = new PlanoAssinatura("HortiLife", "Premium", 89.90, 6, 4, 4);
        return new Pedido(LocalDateTime.now(), "APROVADO", "PROT0099", assinante(), plano);
    }

    @Test
    @DisplayName("Passo 22: Contrato gerado com status ATIVO")
    void deveGerarContratoAtivo() {
        Contrato input = new Contrato(null, null, "PENDENTE", pedido(), assinante());

        ArgumentCaptor<Contrato> captor = ArgumentCaptor.forClass(Contrato.class);
        when(repo.save(captor.capture())).thenAnswer(inv -> inv.getArgument(0));

        Contrato gerado = service.gerarContrato(input);

        assertThat(captor.getValue().getStatus()).isEqualTo("ATIVO");
    }

    @Test
    @DisplayName("Passo 22: DataInicio definida automaticamente se não informada")
    void deveDefinirDataInicioAutomaticamente() {
        Contrato input = new Contrato(null, null, "PENDENTE", pedido(), assinante());

        ArgumentCaptor<Contrato> captor = ArgumentCaptor.forClass(Contrato.class);
        when(repo.save(captor.capture())).thenAnswer(inv -> inv.getArgument(0));

        service.gerarContrato(input);

        assertThat(captor.getValue().getDataInicio()).isNotNull();
        assertThat(captor.getValue().getDataInicio()).isEqualTo(LocalDate.now());
    }

    @Test
    @DisplayName("DataInicio informada não é sobrescrita")
    void naoDeveSObrEscreverDataInicioInformada() {
        LocalDate dataPassada = LocalDate.of(2026, 1, 1);
        Contrato input = new Contrato(dataPassada, null, "PENDENTE", pedido(), assinante());

        ArgumentCaptor<Contrato> captor = ArgumentCaptor.forClass(Contrato.class);
        when(repo.save(captor.capture())).thenAnswer(inv -> inv.getArgument(0));

        service.gerarContrato(input);

        assertThat(captor.getValue().getDataInicio()).isEqualTo(dataPassada);
    }

    @Test
    @DisplayName("Encerrar contrato define DataFim e status ENCERRADO")
    void deveEncerrarContrato() {
        Contrato existente = new Contrato(LocalDate.of(2026, 1, 1), null, "ATIVO", pedido(), assinante());
        when(repo.findById(1L)).thenReturn(Optional.of(existente));
        when(repo.save(any())).thenReturn(existente);

        Contrato encerrado = service.encerrarContrato(1L);

        assertThat(encerrado.getStatus()).isEqualTo("ENCERRADO");
        assertThat(encerrado.getDataFim()).isNotNull();
        assertThat(encerrado.getDataFim()).isEqualTo(LocalDate.now());
    }

    @Test
    @DisplayName("Encerrar contrato inexistente lança RuntimeException")
    void deveLancarExcecaoParaContratoInexistente() {
        when(repo.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.encerrarContrato(99L));
    }

    @Test
    @DisplayName("Buscar contratos por assinante")
    void deveBuscarContratosPorAssinante() {
        Contrato c = new Contrato(LocalDate.now(), null, "ATIVO", pedido(), assinante());
        when(repo.findByAssinanteId(1L)).thenReturn(List.of(c));

        List<Contrato> contratos = service.buscarPorAssinante(1L);

        assertThat(contratos).hasSize(1)
                .allMatch(ct -> ct.getStatus().equals("ATIVO"));
    }
}
