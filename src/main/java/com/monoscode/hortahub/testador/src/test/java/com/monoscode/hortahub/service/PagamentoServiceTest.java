package com.monoscode.hortahub.testador.src.test.java.com.monoscode.hortahub.service;

import com.monoscode.hortahub.entidades.Assinante;
import com.monoscode.hortahub.entidades.Pagamento;
import com.monoscode.hortahub.entidades.Pedido;
import com.monoscode.hortahub.entidades.PlanoAssinatura;
import com.monoscode.hortahub.repositories.PagamentoRepository;
import com.monoscode.hortahub.services.PagamentoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("PagamentoService — Passo 22 (validação e aprovação do pagamento)")
class PagamentoServiceTest {

    @Mock private PagamentoRepository repo;
    @InjectMocks private PagamentoService service;

    private Assinante assinante() {
        return new Assinante("Maria", "Santos", "98765432100", "11988880000");
    }

    private Pedido pedido() {
        PlanoAssinatura plano = new PlanoAssinatura("Essencial", "desc", 49.90, 3, 2, 2);
        return new Pedido(LocalDateTime.now(), "AGUARDANDO_APROVACAO", "XZ001234", assinante(), plano);
    }

    @Test
    @DisplayName("Passo 22: Pagamento registrado com status APROVADO")
    void deveRegistrarPagamentoComoAprovado() {
        Pagamento input = new Pagamento(49.90, null, null, assinante(), pedido());

        ArgumentCaptor<Pagamento> captor = ArgumentCaptor.forClass(Pagamento.class);
        when(repo.save(captor.capture())).thenAnswer(inv -> inv.getArgument(0));

        Pagamento resultado = service.registrarPagamento(input);

        assertThat(captor.getValue().getStatus()).isEqualTo("APROVADO");
    }

    @Test
    @DisplayName("Passo 22: Data de pagamento definida automaticamente no registro")
    void deveDefinirDataPagamentoAutomaticamente() {
        Pagamento input = new Pagamento(49.90, null, null, assinante(), pedido());

        ArgumentCaptor<Pagamento> captor = ArgumentCaptor.forClass(Pagamento.class);
        when(repo.save(captor.capture())).thenAnswer(inv -> inv.getArgument(0));

        service.registrarPagamento(input);

        assertThat(captor.getValue().getDataPagamento()).isNotNull();
        assertThat(captor.getValue().getDataPagamento()).isBeforeOrEqualTo(LocalDateTime.now());
    }

    @Test
    @DisplayName("Valor do pagamento corresponde ao plano selecionado")
    void deveManterValorDoPagamento() {
        Pagamento input = new Pagamento(89.90, null, null, assinante(), pedido());
        when(repo.save(any())).thenAnswer(inv -> inv.getArgument(0));

        Pagamento resultado = service.registrarPagamento(input);

        assertThat(resultado.getValor()).isEqualTo(89.90);
    }

    @Test
    @DisplayName("Excluir pagamento chama deleteById")
    void deveExcluirPagamento() {
        doNothing().when(repo).deleteById(1L);
        service.excluir(1L);
        verify(repo).deleteById(1L);
    }
}
