package com.monoscode.hortahub.services;

import com.monoscode.hortahub.entidades.Pagamento;
import com.monoscode.hortahub.repositories.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository repositorio;

    public List<Pagamento> listarTodos() {
        return repositorio.findAll();
    }

    public Optional<Pagamento> buscarPorId(Long id) {
        return repositorio.findById(id);
    }

    @Transactional
    public Pagamento registrarPagamento(Pagamento pagamento) {
        // Regra de negócio: Define a data do pagamento no momento do registro
        pagamento.setDataPagamento(LocalDateTime.now());

        // Passo 22: Sistema valida pagamento e muda status para aprovado
        // Aqui poderia haver uma integração com gateway de cartão
        pagamento.setStatus("APROVADO");

        return repositorio.save(pagamento);
    }

    @Transactional
    public void excluir(Long id) {
        repositorio.deleteById(id);
    }
}