package com.monoscode.hortahub.services;

import com.monoscode.hortahub.entidades.Assinante;
import com.monoscode.hortahub.repositories.AssinanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class AssinanteService {

    @Autowired
    private AssinanteRepository repositorio;

    public List<Assinante> listarTodos() {
        return repositorio.findAll();
    }

    public Optional<Assinante> buscarPorId(Long id) {
        return repositorio.findById(id);
    }

    public Optional<Assinante> buscarPorCpf(String cpf) {
        return repositorio.findByCpf(cpf);
    }

    @Transactional
    public Assinante salvar(Assinante assinante) {
        return repositorio.save(assinante);
    }

    @Transactional
    public void excluir(Long id) {
        repositorio.deleteById(id);
    }

    @Transactional
    public Assinante atualizar(Long id, Assinante assinanteAtualizado) {
        return repositorio.findById(id).map(assinante -> {
            assinante.setNome(assinanteAtualizado.getNome());
            assinante.setSobrenome(assinanteAtualizado.getSobrenome());
            assinante.setTelefone(assinanteAtualizado.getTelefone());
            // CPF geralmente não se altera, mas pode ser incluído se necessário, vai que né.
            return repositorio.save(assinante);
        }).orElseThrow(() -> new RuntimeException("Assinante não encontrado"));
    }
}