package com.monoscode.hortahub.services;

import com.monoscode.hortahub.entidades.Contrato;
import com.monoscode.hortahub.repositories.ContratoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ContratoService {

    @Autowired
    private ContratoRepository repositorio;

    public List<Contrato> listarTodos() {
        return repositorio.findAll();
    }

    public Optional<Contrato> buscarPorId(Long id) {
        return repositorio.findById(id);
    }

    public List<Contrato> buscarPorAssinante(Long assinanteId) {
        return repositorio.findByAssinanteId(assinanteId);
    }

    @Transactional
    public Contrato gerarContrato(Contrato contrato) {
        // Define data de início como hoje caso não venha no objeto
        if (contrato.getDataInicio() == null) {
            contrato.setDataInicio(LocalDate.now());
        }

        // Passo 22: Muda o status da assinatura/contrato para aprovado/ativo
        contrato.setStatus("ATIVO");

        return repositorio.save(contrato);
    }

    @Transactional
    public Contrato encerrarContrato(Long id) {
        return repositorio.findById(id).map(contrato -> {
            contrato.setDataFim(LocalDate.now());
            contrato.setStatus("ENCERRADO");
            return repositorio.save(contrato);
        }).orElseThrow(() -> new RuntimeException("Contrato não encontrado"));
    }
}