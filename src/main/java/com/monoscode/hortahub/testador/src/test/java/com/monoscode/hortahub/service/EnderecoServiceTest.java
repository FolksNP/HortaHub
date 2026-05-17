package com.monoscode.hortahub.testador.src.test.java.com.monoscode.hortahub.service;

import com.monoscode.hortahub.entidades.Assinante;
import com.monoscode.hortahub.entidades.Endereco;
import com.monoscode.hortahub.repositories.EnderecoRepository;
import com.monoscode.hortahub.services.EnderecoService;
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
@DisplayName("EnderecoService — Passo 18 (armazenar endereço de entrega)")
class EnderecoServiceTest {

    @Mock private EnderecoRepository repo;
    @InjectMocks private EnderecoService service;

    private Assinante assinante() {
        return new Assinante("Pedro", "Alves", "55544433322", "11977770000");
    }

    @Test
    @DisplayName("Passo 18: Salvar endereço de entrega")
    void deveSalvarEndereco() {
        Assinante a = assinante();
        Endereco e = new Endereco("01310100", "Av. Paulista", 1578, "Próximo ao metrô", a);
        when(repo.save(any())).thenReturn(e);

        Endereco salvo = service.salvar(e);

        assertThat(salvo.getCep()).isEqualTo("01310100");
        assertThat(salvo.getLogradouro()).isEqualTo("Av. Paulista");
        assertThat(salvo.getNumero()).isEqualTo(1578);
        verify(repo).save(e);
    }

    @Test
    @DisplayName("Buscar endereços por assinante")
    void deveBuscarEnderecosPorAssinante() {
        Assinante a = assinante();
        Endereco e1 = new Endereco("01310100", "Av. Paulista", 1578, null, a);
        Endereco e2 = new Endereco("04040040", "Rua Augusta", 200, "Apto 5", a);
        when(repo.findByAssinanteId(1L)).thenReturn(List.of(e1, e2));

        List<Endereco> enderecos = service.buscarPorAssinante(1L);

        assertThat(enderecos).hasSize(2);
    }

    @Test
    @DisplayName("Atualizar endereço existente")
    void deveAtualizarEndereco() {
        Assinante a = assinante();
        Endereco existente = new Endereco("01310100", "Av. Paulista", 1578, null, a);
        Endereco novo = new Endereco("04040040", "Rua Nova", 300, "Bloco B", a);

        when(repo.findById(1L)).thenReturn(Optional.of(existente));
        when(repo.save(any())).thenReturn(existente);

        Endereco atualizado = service.atualizar(1L, novo);

        assertThat(atualizado.getCep()).isEqualTo("04040040");
        assertThat(atualizado.getLogradouro()).isEqualTo("Rua Nova");
    }

    @Test
    @DisplayName("Atualizar endereço inexistente lança RuntimeException")
    void deveLancarExcecaoParaEnderecoInexistente() {
        when(repo.findById(99L)).thenReturn(Optional.empty());
        Endereco novo = new Endereco("00000000", "Rua X", 1, null, assinante());
        assertThrows(RuntimeException.class, () -> service.atualizar(99L, novo));
    }

    @Test
    @DisplayName("Excluir endereço chama deleteById")
    void deveExcluirEndereco() {
        doNothing().when(repo).deleteById(1L);
        service.excluir(1L);
        verify(repo).deleteById(1L);
    }
}
