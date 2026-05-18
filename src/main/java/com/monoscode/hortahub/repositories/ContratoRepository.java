package com.monoscode.hortahub.testador.src.main.java.com.monoscode.hortahub.repositories;

import com.monoscode.hortahub.entidades.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContratoRepository extends JpaRepository<Contrato, Long> {

    List<Contrato> findByAssinanteId(Long assinanteId);
}
