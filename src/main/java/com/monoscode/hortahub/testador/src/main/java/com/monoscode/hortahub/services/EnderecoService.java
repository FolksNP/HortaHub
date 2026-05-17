package com.monoscode.hortahub.testador.src.main.java.com.monoscode.hortahub.services;

import com.monoscode.hortahub.entidades.Endereco;
import com.monoscode.hortahub.repositories.EnderecoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EnderecoService {

    @Autowired
    private EnderecoRepository repositorio;

    public List<Endereco> listarTodos() {
        return repositorio.findAll();
    }

    public Optional<Endereco> buscarPorId(Long id) {
        return repositorio.findById(id);
    }

    public List<Endereco> buscarPorAssinante(Long assinanteId) {
        return repositorio.findByAssinanteId(assinanteId);
    }

    @Transactional
    public Endereco salvar(Endereco endereco) {
        // Passo 18: Sistema armazena endereço de entrega
        return repositorio.save(endereco);
    }

    @Transactional
    public void excluir(Long id) {
        repositorio.deleteById(id);
    }

    @Transactional
    public Endereco atualizar(Long id, Endereco enderecoNovo) {
        return repositorio.findById(id).map(endereco -> {
            endereco.setCep(enderecoNovo.getCep());
            endereco.setLogradouro(enderecoNovo.getLogradouro());
            endereco.setNumero(enderecoNovo.getNumero());
            endereco.setReferencia(enderecoNovo.getReferencia());
            return repositorio.save(endereco);
        }).orElseThrow(() -> new RuntimeException("Endereço não encontrado"));
    }
}