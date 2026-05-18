package com.monoscode.hortahub.testador.src.test.java.com.monoscode.hortahub.service;

import com.monoscode.hortahub.entidades.Produto;
import com.monoscode.hortahub.enums.CategoriaProduto;
import com.monoscode.hortahub.repositories.ProdutoRepository;
import com.monoscode.hortahub.services.ProdutoService;
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
@DisplayName("ProdutoService — Passos 7, 10, 13 (catálogo por categoria)")
class ProdutoServiceTest {

    @Mock private ProdutoRepository repo;
    @InjectMocks private ProdutoService service;

    @Test
    @DisplayName("Passo 7: Listar frutas disponíveis")
    void deveListarFrutas() {
        Produto banana = new Produto("Banana", "kg", 5.0, null, CategoriaProduto.FRUTA);
        Produto maca   = new Produto("Maçã", "kg", 8.0, null, CategoriaProduto.FRUTA);
        when(repo.findByCategoria(CategoriaProduto.FRUTA)).thenReturn(List.of(banana, maca));

        List<Produto> frutas = service.listarPorCategoria(CategoriaProduto.FRUTA);

        assertThat(frutas).hasSize(2)
                .allMatch(p -> p.getCategoria() == CategoriaProduto.FRUTA);
    }

    @Test
    @DisplayName("Passo 10: Listar legumes disponíveis")
    void deveListarLegumes() {
        Produto cenoura = new Produto("Cenoura", "kg", 4.0, null, CategoriaProduto.LEGUME);
        when(repo.findByCategoria(CategoriaProduto.LEGUME)).thenReturn(List.of(cenoura));

        List<Produto> legumes = service.listarPorCategoria(CategoriaProduto.LEGUME);

        assertThat(legumes).hasSize(1)
                .allMatch(p -> p.getCategoria() == CategoriaProduto.LEGUME);
    }

    @Test
    @DisplayName("Passo 13: Listar verduras disponíveis")
    void deveListarVerduras() {
        Produto alface = new Produto("Alface", "maço", 2.5, null, CategoriaProduto.VERDURA);
        when(repo.findByCategoria(CategoriaProduto.VERDURA)).thenReturn(List.of(alface));

        List<Produto> verduras = service.listarPorCategoria(CategoriaProduto.VERDURA);

        assertThat(verduras).hasSize(1)
                .allMatch(p -> p.getCategoria() == CategoriaProduto.VERDURA);
    }

    @Test
    @DisplayName("Buscar produto por ID existente")
    void deveBuscarPorId() {
        Produto p = new Produto("Manga", "unidade", 3.5, null, CategoriaProduto.FRUTA);
        when(repo.findById(1L)).thenReturn(Optional.of(p));

        Optional<Produto> resultado = service.buscarPorId(1L);

        assertTrue(resultado.isPresent());
        assertThat(resultado.get().getNome()).isEqualTo("Manga");
    }

    @Test
    @DisplayName("Buscar produto por ID inexistente retorna vazio")
    void deveRetornarVazioParaIdInexistente() {
        when(repo.findById(99L)).thenReturn(Optional.empty());
        assertTrue(service.buscarPorId(99L).isEmpty());
    }

    @Test
    @DisplayName("Salvar produto persiste e retorna entidade")
    void deveSalvarProduto() {
        Produto p = new Produto("Espinafre", "maço", 3.0, null, CategoriaProduto.VERDURA);
        when(repo.save(any())).thenReturn(p);

        Produto salvo = service.salvar(p);

        assertThat(salvo.getNome()).isEqualTo("Espinafre");
        verify(repo).save(p);
    }

    @Test
    @DisplayName("Atualizar produto inexistente lança RuntimeException")
    void deveLancarExcecaoParaProdutoInexistente() {
        when(repo.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class,
                () -> service.atualizar(99L, new Produto("X", "X", 0.0, null, CategoriaProduto.FRUTA)));
    }
}
