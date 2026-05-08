package com.monoscode.hortahub.services;

import com.monoscode.hortahub.entidades.PlanoAssinatura;
import com.monoscode.hortahub.repositories.PlanoAssinaturaRepository;

import java.util.List;

public class PlanoAssinaturaService {
    private PlanoAssinaturaRepository planoRepository;

    public PlanoAssinaturaService(PlanoAssinaturaRepository planoRepository) {
        this.planoRepository = planoRepository;
    }

    public PlanoAssinaturaService() {
    }

    public List<PlanoAssinatura> listarTodos(){
        return planoRepository.findAll();
    }
}
