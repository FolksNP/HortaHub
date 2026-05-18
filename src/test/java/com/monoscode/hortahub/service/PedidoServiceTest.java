package com.monoscode.hortahub.testador.src.test.java.com.monoscode.hortahub.service;

import com.monoscode.hortahub.entidades.Assinante;
import com.monoscode.hortahub.entidades.Pedido;
import com.monoscode.hortahub.entidades.PlanoAssinatura;
import com.monoscode.hortahub.repositories.PedidoRepository;
import com.monoscode.hortahub.services.PedidoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("PedidoService — Passos 18, 19, 22, 24")
class PedidoServiceTest {

    @Mock private PedidoRepository repo;
    @InjectMocks private PedidoService service;

    private Assinante assinante() {
        return new Assinante("Test", "User", "12345678900", "11999990000");
    }

    private PlanoAssinatura plano() {
        return new PlanoAssinatura("Essencial", "desc", 49.90, 3, 2, 2);
    }

    @Test
    @DisplayName("Passo 19: Pedido criado com status AGUARDANDO_APROVACAO")
    void deveCriarPedidoComStatusAguardandoAprovacao() {
        Pedido pedidoInput = new Pedido(null, null, null, assinante(), plano());
        Pedido pedidoSalvo = new Pedido(LocalDateTime.now(), "AGUARDANDO_APROVACAO", "ABCD1234", assinante(), plano());
        when(repo.save(any())).thenReturn(pedidoSalvo);

        Pedido resultado = service.criarPedido(pedidoInput);

        assertThat(resultado.getStatus()).isEqualTo("AGUARDANDO_APROVACAO");
    }

    @Test
    @DisplayName("Passo 24: Protocolo é gerado automaticamente se não informado")
    void deveGerarProtocoloAutomaticamente() {
        Pedido pedidoInput = new Pedido(null, null, null, assinante(), plano());

        ArgumentCaptor<Pedido> captor = ArgumentCaptor.forClass(Pedido.class);
        when(repo.save(captor.capture())).thenAnswer(inv -> inv.getArgument(0));

        service.criarPedido(pedidoInput);

        Pedido capturado = captor.getValue();
        assertThat(capturado.getProtocolo()).isNotNull().isNotBlank();
        assertThat(capturado.getProtocolo()).hasSize(8);
    }

    @Test
    @DisplayName("Passo 18: DataPedido é definida no momento da criação")
    void deveDefinirDataPedidoAutomaticamente() {
        Pedido pedidoInput = new Pedido(null, null, null, assinante(), plano());

        ArgumentCaptor<Pedido> captor = ArgumentCaptor.forClass(Pedido.class);
        when(repo.save(captor.capture())).thenAnswer(inv -> inv.getArgument(0));

        service.criarPedido(pedidoInput);

        assertThat(captor.getValue().getDataPedido()).isNotNull();
        assertThat(captor.getValue().getDataPedido()).isBeforeOrEqualTo(LocalDateTime.now());
    }

    @Test
    @DisplayName("Protocolo não é sobrescrito se já existir")
    void naoDeveSObrEscreverProtocoloExistente() {
        Pedido pedidoInput = new Pedido(null, null, "PROTOCOLO_EXISTENTE", assinante(), plano());

        ArgumentCaptor<Pedido> captor = ArgumentCaptor.forClass(Pedido.class);
        when(repo.save(captor.capture())).thenAnswer(inv -> inv.getArgument(0));

        service.criarPedido(pedidoInput);

        assertThat(captor.getValue().getProtocolo()).isEqualTo("PROTOCOLO_EXISTENTE");
    }

    @Test
    @DisplayName("Atualizar status do pedido")
    void deveAtualizarStatusDoPedido() {
        Pedido pedidoExistente = new Pedido(LocalDateTime.now(), "AGUARDANDO_APROVACAO", "XYZ99999", assinante(), plano());
        when(repo.findById(1L)).thenReturn(Optional.of(pedidoExistente));
        when(repo.save(any())).thenReturn(pedidoExistente);

        Pedido atualizado = service.atualizarStatus(1L, "APROVADO");

        assertThat(atualizado.getStatus()).isEqualTo("APROVADO");
    }

    @Test
    @DisplayName("Buscar pedido por protocolo existente")
    void deveBuscarPorProtocolo() {
        Pedido p = new Pedido(LocalDateTime.now(), "APROVADO", "PROT0001", assinante(), plano());
        when(repo.findByProtocolo("PROT0001")).thenReturn(Optional.of(p));

        Optional<Pedido> resultado = service.buscarPorProtocolo("PROT0001");

        assertTrue(resultado.isPresent());
        assertThat(resultado.get().getProtocolo()).isEqualTo("PROT0001");
    }

    @Test
    @DisplayName("Atualizar status de pedido inexistente lança RuntimeException")
    void deveLancarExcecaoParaPedidoInexistente() {
        when(repo.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.atualizarStatus(99L, "APROVADO"));
    }
}
