package com.monoscode.hortahub.testador.src.main.java.com.monoscode.hortahub.services;

import com.monoscode.hortahub.entidades.Cesta;
import com.monoscode.hortahub.repositories.CestaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CestaService {

    @Autowired
    private CestaRepository repositorio;

    public List<Cesta> listarTodas() {
        return repositorio.findAll();
    }

    public Optional<Cesta> buscarPorId(Long id) {
        return repositorio.findById(id);
    }

    @Transactional
    public Cesta salvar(Cesta cesta) {
        // Garante a consistência da relação bidirecional com itens
        if (cesta.getItens() != null) {
            cesta.getItens().forEach(item -> item.setCesta(cesta));
        }
        return repositorio.save(cesta);
    }

    @Transactional
    public void excluir(Long id) {
        repositorio.deleteById(id);
    }
}