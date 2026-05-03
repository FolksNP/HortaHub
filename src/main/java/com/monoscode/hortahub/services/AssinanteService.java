package com.monoscode.hortahub.services;

import com.monoscode.hortahub.entidades.Assinante;
import com.monoscode.hortahub.repositories.AssinanteRepository;

public class AssinanteService {

    private final AssinanteRepository repository;

    public AssinanteService(AssinanteRepository repository){
        this.repository = repository;
    }

    public boolean verificarCadastro(String numero) {
        return repository.findByNumero(numero).isPresent();
    }

    public boolean cadastrarNovoCliente(Assinante assinante) {
        repository.save(assinante);
        if (repository.findById(assinante.getId()).isPresent()){
            return true;
        }

        return false;
    }


}
