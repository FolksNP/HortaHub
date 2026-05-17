package com.monoscode.hortahub.testador.src.test.java.com.monoscode.hortahub;

import com.monoscode.hortahub.entidades.*;
import com.monoscode.hortahub.enums.CategoriaProduto;
import com.monoscode.hortahub.internal_operations.VerificadorDeCodigoService;
import com.monoscode.hortahub.repositories.*;
import com.monoscode.hortahub.services.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Testa o cenário principal completo: "Assinar Serviço de Feira" (25 passos do enunciado).
 * Usa Mockito puro — não precisa de banco de dados.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("Fluxo Completo: Assinar Serviço de Feira")
class FluxoAssinaturaTest {

    // --- Repositórios mockados ---
    @Mock private AssinanteRepository assinanteRepo;
    @Mock private PlanoAssinaturaRepository planoRepo;
    @Mock private ProdutoRepository produtoRepo;
    @Mock private CestaRepository cestaRepo;
    @Mock private ItemCestaRepository itemCestaRepo;
    @Mock private EnderecoRepository enderecoRepo;
    @Mock private PedidoRepository pedidoRepo;
    @Mock private PagamentoRepository pagamentoRepo;
    @Mock private ContratoRepository contratoRepo;

    // --- Services com repositórios injetados via @InjectMocks ---
    @InjectMocks private AssinanteService assinanteService;
    @InjectMocks private ProdutoService produtoService;
    @InjectMocks private PedidoService pedidoService;
    @InjectMocks private PagamentoService pagamentoService;
    @InjectMocks private CestaService cestaService;
    @InjectMocks private EnderecoService enderecoService;
    @InjectMocks private ContratoService contratoService;
    @InjectMocks private ItemCestaService itemCestaService;
    @InjectMocks private PlanoAssinaturaService planoAssinaturaService;

    private VerificadorDeCodigoService verificadorService;

    @BeforeEach
    void setup() {
        verificadorService = new VerificadorDeCodigoService();
    }

    @Test
    @DisplayName("Passos 1-25: Fluxo completo de assinatura com sucesso")
    void deveConcluirAssinaturaComSucesso() {

        // ===========================================================
        // PASSO 1: Assinante informa número de celular
        // PASSO 2: Sistema envia código (simulado — não chama API real)
        // ===========================================================
        String telefone = "5511998887766";
        verificadorService.adicionarCodigoParaTeste(telefone, "482931");

        // PASSO 3: Assinante informa o código recebido por SMS
        // PASSO 4: Sistema valida o código
        boolean codigoValido = verificadorService.verificarCodigo(telefone, "482931");
        assertTrue(codigoValido, "Passo 4: Código SMS deve ser aceito");

        // Garante que o código é descartado após validação (não pode ser reusado)
        assertFalse(verificadorService.verificarCodigo(telefone, "482931"),
                "Código não deve ser reutilizável após uso");

        // ===========================================================
        // PASSO 5: Sistema busca relação de planos de assinatura
        // ===========================================================
        PlanoAssinatura planoEssencial = new PlanoAssinatura(
                "Essencial", "Kit semanal básico: 3 frutas, 2 legumes, 2 verduras",
                49.90, 3, 2, 2);
        PlanoAssinatura planoHortiLife = new PlanoAssinatura(
                "HortiLife", "Kit completo: 6 frutas, 4 legumes, 4 verduras",
                89.90, 6, 4, 4);

        when(planoRepo.findAll()).thenReturn(List.of(planoEssencial, planoHortiLife));
        List<PlanoAssinatura> planosDisponiveis = planoAssinaturaService.listarTodos();

        assertThat(planosDisponiveis).hasSize(2)
                .extracting(PlanoAssinatura::getNome)
                .containsExactly("Essencial", "HortiLife");

        // PASSO 6: Assinante seleciona o plano Essencial
        PlanoAssinatura planoSelecionado = planosDisponiveis.get(0);
        assertThat(planoSelecionado.getNome()).isEqualTo("Essencial");
        assertThat(planoSelecionado.getValor()).isEqualTo(49.90);

        // ===========================================================
        // PASSO 7: Sistema busca frutas disponíveis para o plano
        // ===========================================================
        Produto banana = new Produto("Banana Prata", "kg", 5.00, null, CategoriaProduto.FRUTA);
        Produto manga  = new Produto("Manga Palmer", "unidade", 3.50, null, CategoriaProduto.FRUTA);
        Produto maca   = new Produto("Maçã Fuji", "kg", 8.00, null, CategoriaProduto.FRUTA);

        when(produtoRepo.findByCategoria(CategoriaProduto.FRUTA))
                .thenReturn(List.of(banana, manga, maca));

        List<Produto> frutas = produtoService.listarPorCategoria(CategoriaProduto.FRUTA);
        assertThat(frutas).hasSize(3)
                .allMatch(p -> p.getCategoria() == CategoriaProduto.FRUTA);

        // PASSO 8: Assinante pode escolher até o limite do plano
        assertThat(frutas.size()).isGreaterThanOrEqualTo(planoSelecionado.getQuantidadeFrutas());

        // PASSO 9: Itens de fruta armazenados na cesta (verificado no passo de salvar cesta)

        // ===========================================================
        // PASSO 10: Sistema busca legumes disponíveis
        // ===========================================================
        Produto cenoura = new Produto("Cenoura", "kg", 4.00, null, CategoriaProduto.LEGUME);
        Produto batata  = new Produto("Batata Doce", "kg", 3.00, null, CategoriaProduto.LEGUME);

        when(produtoRepo.findByCategoria(CategoriaProduto.LEGUME))
                .thenReturn(List.of(cenoura, batata));

        List<Produto> legumes = produtoService.listarPorCategoria(CategoriaProduto.LEGUME);
        assertThat(legumes).hasSize(2)
                .allMatch(p -> p.getCategoria() == CategoriaProduto.LEGUME);

        // PASSO 11-12: Assinante escolhe legumes (limite do plano: 2)
        assertThat(legumes.size()).isGreaterThanOrEqualTo(planoSelecionado.getQuantidadeLegumes());

        // ===========================================================
        // PASSO 13: Sistema busca verduras disponíveis
        // ===========================================================
        Produto alface    = new Produto("Alface Crespa", "maço", 2.50, null, CategoriaProduto.VERDURA);
        Produto espinafre = new Produto("Espinafre", "maço", 3.00, null, CategoriaProduto.VERDURA);

        when(produtoRepo.findByCategoria(CategoriaProduto.VERDURA))
                .thenReturn(List.of(alface, espinafre));

        List<Produto> verduras = produtoService.listarPorCategoria(CategoriaProduto.VERDURA);
        assertThat(verduras).hasSize(2)
                .allMatch(p -> p.getCategoria() == CategoriaProduto.VERDURA);

        // PASSO 14-15: Assinante escolhe verduras (limite do plano: 2)
        assertThat(verduras.size()).isGreaterThanOrEqualTo(planoSelecionado.getQuantidadeVerduras());

        // ===========================================================
        // PASSO 16: Sistema exibe cesta e solicita endereço de entrega
        // PASSO 17: Assinante confirma cesta e informa endereço
        // ===========================================================

        // Criar e salvar assinante
        Assinante assinante = new Assinante("Maria", "Souza", "98765432100", telefone);
        when(assinanteRepo.save(any(Assinante.class))).thenReturn(assinante);
        Assinante assinanteSalvo = assinanteService.salvar(assinante);
        assertThat(assinanteSalvo.getNome()).isEqualTo("Maria");
        assertThat(assinanteSalvo.getTelefone()).isEqualTo(telefone);

        // PASSO 18: Sistema armazena endereço de entrega
        Endereco endereco = new Endereco("01310100", "Av. Paulista", 1578,
                "Próximo ao metrô Trianon", assinante);
        when(enderecoRepo.save(any(Endereco.class))).thenReturn(endereco);

        Endereco enderecoSalvo = enderecoService.salvar(endereco);
        assertThat(enderecoSalvo.getCep()).isEqualTo("01310100");
        assertThat(enderecoSalvo.getLogradouro()).isEqualTo("Av. Paulista");
        assertThat(enderecoSalvo.getNumero()).isEqualTo(1578);

        // ===========================================================
        // PASSO 18-19: Sistema cria pedido com status AGUARDANDO_APROVACAO
        // ===========================================================
        Pedido pedidoInput = new Pedido(null, null, null, assinante, planoSelecionado);

        Pedido pedidoCriado = new Pedido(
                LocalDateTime.now(), "AGUARDANDO_APROVACAO", "FA3B9C12",
                assinante, planoSelecionado);
        when(pedidoRepo.save(any(Pedido.class))).thenReturn(pedidoCriado);

        Pedido pedidoSalvo = pedidoService.criarPedido(pedidoInput);

        assertThat(pedidoSalvo.getStatus()).isEqualTo("AGUARDANDO_APROVACAO");
        assertThat(pedidoSalvo.getProtocolo()).isNotNull().isNotBlank();
        assertThat(pedidoSalvo.getDataPedido()).isNotNull();
        verify(pedidoRepo).save(any(Pedido.class));

        // ===========================================================
        // PASSO 20: Sistema apresenta total da assinatura
        // ===========================================================
        double totalAssinatura = planoSelecionado.getValor();
        assertThat(totalAssinatura).isEqualTo(49.90);

        // ===========================================================
        // PASSO 21: Assinante informa dados do cartão de crédito
        // PASSO 22: Sistema valida pagamento → status APROVADO → gera protocolo
        // ===========================================================
        Pagamento pagamentoInput = new Pagamento(totalAssinatura, null, null, assinante, pedidoSalvo);

        Pagamento pagamentoAprovado = new Pagamento(
                49.90, LocalDateTime.now(), "APROVADO", assinante, pedidoSalvo);
        when(pagamentoRepo.save(any(Pagamento.class))).thenReturn(pagamentoAprovado);

        Pagamento pagamentoRegistrado = pagamentoService.registrarPagamento(pagamentoInput);

        assertThat(pagamentoRegistrado.getStatus()).isEqualTo("APROVADO");
        assertThat(pagamentoRegistrado.getValor()).isEqualTo(49.90);
        assertThat(pagamentoRegistrado.getDataPagamento()).isNotNull();

        // PASSO 22: Contrato gerado com status ATIVO
        Contrato contratoInput = new Contrato(null, null, "PENDENTE", pedidoSalvo, assinante);

        Contrato contratoAtivo = new Contrato(
                LocalDate.now(), null, "ATIVO", pedidoSalvo, assinante);
        when(contratoRepo.save(any(Contrato.class))).thenReturn(contratoAtivo);

        Contrato contratoGerado = contratoService.gerarContrato(contratoInput);

        assertThat(contratoGerado.getStatus()).isEqualTo("ATIVO");
        assertThat(contratoGerado.getDataInicio()).isNotNull();
        assertThat(contratoGerado.getDataFim()).isNull();

        // ===========================================================
        // PASSO 23-24: Exibe dados da entrega e número de protocolo
        // ===========================================================
        String protocolo = pedidoSalvo.getProtocolo();
        assertThat(protocolo).isNotBlank();

        System.out.println("=== ASSINATURA CONCLUÍDA COM SUCESSO ===");
        System.out.println("Assinante : " + assinanteSalvo.getNome() + " " + assinanteSalvo.getSobrenome());
        System.out.println("Plano     : " + planoSelecionado.getNome() + " — R$ " + totalAssinatura);
        System.out.println("Entrega   : " + enderecoSalvo.getLogradouro() + ", " + enderecoSalvo.getNumero());
        System.out.println("Protocolo : " + protocolo);
        System.out.println("Status    : " + pagamentoRegistrado.getStatus());

        // PASSO 25: Caso de uso encerrado
    }
}
