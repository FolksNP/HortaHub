package com.monoscode.hortahub.testador.src.test.java.com.monoscode.hortahub.service;

import com.monoscode.hortahub.entidades.Assinante;
import com.monoscode.hortahub.repositories.AssinanteRepository;
import com.monoscode.hortahub.services.AssinanteService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AssinanteService")
class AssinanteServiceTest {

    @Mock private AssinanteRepository repo;
    @InjectMocks private AssinanteService service;

    @Test
    @DisplayName("Salvar assinante retorna entidade persistida")
    void deveSalvarAssinante() {
        Assinante a = new Assinante("João", "Silva", "12345678901", "11999990000");
        when(repo.save(any())).thenReturn(a);

        Assinante salvo = service.salvar(a);

        assertThat(salvo.getNome()).isEqualTo("João");
        assertThat(salvo.getCpf()).isEqualTo("12345678901");
        verify(repo).save(a);
    }

    @Test
    @DisplayName("Buscar por CPF existente retorna assinante")
    void deveBuscarPorCpf() {
        Assinante a = new Assinante("Ana", "Lima", "11122233344", "11988880000");
        when(repo.findByCpf("11122233344")).thenReturn(Optional.of(a));

        Optional<Assinante> resultado = service.buscarPorCpf("11122233344");

        assertTrue(resultado.isPresent());
        assertThat(resultado.get().getNome()).isEqualTo("Ana");
    }

    @Test
    @DisplayName("Buscar por CPF inexistente retorna Optional vazio")
    void deveRetornarVazioParaCpfInexistente() {
        when(repo.findByCpf("00000000000")).thenReturn(Optional.empty());

        Optional<Assinante> resultado = service.buscarPorCpf("00000000000");

        assertTrue(resultado.isEmpty());
    }

    @Test
    @DisplayName("Listar todos retorna lista completa")
    void deveListarTodos() {
        Assinante a1 = new Assinante("Carlos", "Mendes", "11111111111", "11900001111");
        Assinante a2 = new Assinante("Beatriz", "Costa", "22222222222", "11900002222");
        when(repo.findAll()).thenReturn(List.of(a1, a2));

        List<Assinante> lista = service.listarTodos();

        assertThat(lista).hasSize(2);
    }

    @Test
    @DisplayName("Atualizar assinante existente modifica nome e sobrenome")
    void deveAtualizarAssinante() {
        Assinante existente = new Assinante("Carlos", "Antigo", "11111111111", "11900001111");
        Assinante atualizado = new Assinante("Carlos", "Novo", "11111111111", "11900009999");

        when(repo.findById(1L)).thenReturn(Optional.of(existente));
        when(repo.save(any())).thenReturn(existente);

        Assinante resultado = service.atualizar(1L, atualizado);

        assertThat(resultado.getSobrenome()).isEqualTo("Novo");
        assertThat(resultado.getTelefone()).isEqualTo("11900009999");
    }

    @Test
    @DisplayName("Atualizar assinante inexistente lança RuntimeException")
    void deveLancarExcecaoAoAtualizarAssinanteInexistente() {
        when(repo.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> service.atualizar(99L, new Assinante("X", "X", "X", "X")));
    }

    @Test
    @DisplayName("Excluir chama deleteById no repositório")
    void deveExcluirAssinante() {
        doNothing().when(repo).deleteById(1L);
        service.excluir(1L);
        verify(repo).deleteById(1L);
    }
}
