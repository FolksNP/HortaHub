package com.monoscode.hortahub.testador.src.test.java.com.monoscode.hortahub.service;

import com.monoscode.hortahub.entidades.PlanoAssinatura;
import com.monoscode.hortahub.repositories.PlanoAssinaturaRepository;
import com.monoscode.hortahub.services.PlanoAssinaturaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("PlanoAssinaturaService — Passos 5 e 6 (listar e selecionar planos)")
class PlanoAssinaturaServiceTest {

    @Mock private PlanoAssinaturaRepository repo;

    private PlanoAssinaturaService service;

    @BeforeEach
    void setup() {
        service = new PlanoAssinaturaService();
        // Injeta o mock via reflexão porque @InjectMocks não funciona com construtor vazio + @Autowired
        try {
            var field = PlanoAssinaturaService.class.getDeclaredField("planoRepository");
            field.setAccessible(true);
            field.set(service, repo);
        } catch (Exception e) {
            throw new RuntimeException("Falha ao injetar mock em PlanoAssinaturaService", e);
        }
    }

    @Test
    @DisplayName("Passo 5: Listar todos os planos de assinatura")
    void deveListarTodosOsPlanos() {
        PlanoAssinatura p1 = new PlanoAssinatura("HortiOne", "Básico", 29.90, 2, 1, 1);
        PlanoAssinatura p2 = new PlanoAssinatura("Essencial", "Padrão", 49.90, 3, 2, 2);
        PlanoAssinatura p3 = new PlanoAssinatura("HortiLife", "Premium", 89.90, 6, 4, 4);
        when(repo.findAll()).thenReturn(List.of(p1, p2, p3));

        List<PlanoAssinatura> planos = service.listarTodos();

        assertThat(planos).hasSize(3)
                .extracting(PlanoAssinatura::getNome)
                .containsExactly("HortiOne", "Essencial", "HortiLife");
    }

    @Test
    @DisplayName("Passo 5: Lista vazia quando não há planos cadastrados")
    void deveRetornarListaVaziaSeNaoHaPlanos() {
        when(repo.findAll()).thenReturn(List.of());

        List<PlanoAssinatura> planos = service.listarTodos();

        assertThat(planos).isEmpty();
    }

    @Test
    @DisplayName("Passo 6: Plano tem quantidade correta de itens por categoria")
    void deveConterQuantidadesCorretas() {
        PlanoAssinatura essencial = new PlanoAssinatura("Essencial", "desc", 49.90, 3, 2, 2);
        when(repo.findAll()).thenReturn(List.of(essencial));

        PlanoAssinatura plano = service.listarTodos().get(0);

        assertThat(plano.getQuantidadeFrutas()).isEqualTo(3);
        assertThat(plano.getQuantidadeLegumes()).isEqualTo(2);
        assertThat(plano.getQuantidadeVerduras()).isEqualTo(2);
        assertThat(plano.getValor()).isEqualTo(49.90);
    }
}
