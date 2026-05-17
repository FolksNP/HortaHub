package com.monoscode.hortahub.testador.src.main.java.com.monoscode.hortahub.services;

import com.monoscode.hortahub.entidades.Pedido;
import com.monoscode.hortahub.repositories.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repositorio;

    public List<Pedido> listarTodos() {
        return repositorio.findAll();
    }

    public Optional<Pedido> buscarPorId(Long id) {
        return repositorio.findById(id);
    }

    public Optional<Pedido> buscarPorProtocolo(String protocolo) {
        return repositorio.findByProtocolo(protocolo);
    }

    @Transactional
    public Pedido criarPedido(Pedido pedido) {
        // Passo 18 e 19: Armazena e muda status para aguardando aprovação
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setStatus("AGUARDANDO_APROVACAO");

        // Passo 22 e 24: Gera número de protocolo único
        if (pedido.getProtocolo() == null || pedido.getProtocolo().isEmpty()) {
            pedido.setProtocolo(UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        }

        return repositorio.save(pedido);
    }

    @Transactional
    public Pedido atualizarStatus(Long id, String novoStatus) {
        return repositorio.findById(id).map(pedido -> {
            pedido.setStatus(novoStatus);
            return repositorio.save(pedido);
        }).orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
    }
}