package com.monoscode.hortahub.testador.src.test.java.com.monoscode.hortahub.service;

import com.monoscode.hortahub.entidades.*;
import com.monoscode.hortahub.enums.CategoriaProduto;
import com.monoscode.hortahub.repositories.CestaRepository;
import com.monoscode.hortahub.repositories.ItemCestaRepository;
import com.monoscode.hortahub.services.CestaService;
import com.monoscode.hortahub.services.ItemCestaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CestaService / ItemCestaService — Passos 9, 12, 15, 16")
class CestaServiceTest {

    @Mock private CestaRepository cestaRepo;
    @Mock private ItemCestaRepository itemCestaRepo;

    @InjectMocks private CestaService cestaService;
    @InjectMocks private ItemCestaService itemCestaService;

    private Pedido pedidoDummy() {
        Assinante a = new Assinante("Test", "User", "11111111111", "11900000000");
        PlanoAssinatura p = new PlanoAssinatura("Essencial", "desc", 49.90, 3, 2, 2);
        return new Pedido(LocalDateTime.now(), "AGUARDANDO_APROVACAO", "TST0001", a, p);
    }

    @Test
    @DisplayName("Passos 9/12/15: Cesta salva com itens e consistência bidirecional garantida")
    void deveSalvarCestaComItens() {
        Pedido pedido = pedidoDummy();
        Cesta cesta = new Cesta(pedido);

        Produto banana = new Produto("Banana", "kg", 5.0, null, CategoriaProduto.FRUTA);
        Produto cenoura = new Produto("Cenoura", "kg", 4.0, null, CategoriaProduto.LEGUME);

        ItemCesta item1 = new ItemCesta(2, cesta, banana);
        ItemCesta item2 = new ItemCesta(1, cesta, cenoura);
        cesta.adicionarItem(item1);
        cesta.adicionarItem(item2);

        when(cestaRepo.save(any())).thenReturn(cesta);

        Cesta cestaSalva = cestaService.salvar(cesta);

        assertThat(cestaSalva.getItens()).hasSize(2);
        // Todos os itens devem ter a referência à cesta correta
        cestaSalva.getItens().forEach(item ->
                assertThat(item.getCesta()).isEqualTo(cesta));
    }

    @Test
    @DisplayName("Passo 16: Buscar cesta por ID")
    void deveBuscarCestaPorId() {
        Cesta cesta = new Cesta(pedidoDummy());
        when(cestaRepo.findById(1L)).thenReturn(Optional.of(cesta));

        Optional<Cesta> resultado = cestaService.buscarPorId(1L);

        assertTrue(resultado.isPresent());
    }

    @Test
    @DisplayName("Buscar cesta por ID inexistente retorna vazio")
    void deveRetornarVazioParaCestaInexistente() {
        when(cestaRepo.findById(99L)).thenReturn(Optional.empty());
        assertTrue(cestaService.buscarPorId(99L).isEmpty());
    }

    @Test
    @DisplayName("Adicionar item à cesta via ItemCestaService")
    void deveAdicionarItemNaCesta() {
        Cesta cesta = new Cesta(pedidoDummy());
        Produto alface = new Produto("Alface", "maço", 2.5, null, CategoriaProduto.VERDURA);
        ItemCesta item = new ItemCesta(1, cesta, alface);

        when(itemCestaRepo.save(any())).thenReturn(item);

        ItemCesta salvo = itemCestaService.adicionar(item);

        assertThat(salvo.getProduto().getNome()).isEqualTo("Alface");
        assertThat(salvo.getQuantidade()).isEqualTo(1);
        verify(itemCestaRepo).save(item);
    }

    @Test
    @DisplayName("Listar itens de uma cesta específica")
    void deveListarItensPorCesta() {
        Cesta cesta = new Cesta(pedidoDummy());
        Produto banana = new Produto("Banana", "kg", 5.0, null, CategoriaProduto.FRUTA);
        ItemCesta item = new ItemCesta(3, cesta, banana);

        when(itemCestaRepo.findByCestaId(1L)).thenReturn(List.of(item));

        List<ItemCesta> itens = itemCestaService.listarPorCesta(1L);

        assertThat(itens).hasSize(1);
        assertThat(itens.get(0).getProduto().getNome()).isEqualTo("Banana");
    }

    @Test
    @DisplayName("Remover item da cesta via adicionarItem/removerItem")
    void deveRemoverItemDaCesta() {
        Cesta cesta = new Cesta(pedidoDummy());
        Produto manga = new Produto("Manga", "unidade", 3.5, null, CategoriaProduto.FRUTA);
        ItemCesta item = new ItemCesta(1, cesta, manga);
        cesta.adicionarItem(item);

        assertThat(cesta.getItens()).hasSize(1);
        cesta.removerItem(item);
        assertThat(cesta.getItens()).isEmpty();
        assertThat(item.getCesta()).isNull();
    }

    @Test
    @DisplayName("Excluir cesta chama deleteById")
    void deveExcluirCesta() {
        doNothing().when(cestaRepo).deleteById(1L);
        cestaService.excluir(1L);
        verify(cestaRepo).deleteById(1L);
    }
}
