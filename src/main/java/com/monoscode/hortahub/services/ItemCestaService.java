package com.monoscode.hortahub.services;

import com.monoscode.hortahub.entidades.ItemCesta;
import com.monoscode.hortahub.repositories.ItemCestaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ItemCestaService {

    @Autowired
    private ItemCestaRepository repositorio;

    public List<ItemCesta> listarPorCesta(Long cestaId) {
        return repositorio.findByCestaId(cestaId);
    }

    public Optional<ItemCesta> buscarPorId(Long id) {
        return repositorio.findById(id);
    }

    @Transactional
    public ItemCesta adicionar(ItemCesta item) {
        return repositorio.save(item);
    }

    @Transactional
    public void remover(Long id) {
        repositorio.deleteById(id);
    }
}