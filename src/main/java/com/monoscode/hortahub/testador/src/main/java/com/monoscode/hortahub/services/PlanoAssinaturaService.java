package com.monoscode.hortahub.testador.src.main.java.com.monoscode.hortahub.services;

import com.monoscode.hortahub.entidades.PlanoAssinatura;
import com.monoscode.hortahub.repositories.PlanoAssinaturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlanoAssinaturaService {

    @Autowired
    private PlanoAssinaturaRepository planoRepository;

    public PlanoAssinaturaService() {
    }

    public List<PlanoAssinatura> listarTodos() {
        return planoRepository.findAll();
    }
}
