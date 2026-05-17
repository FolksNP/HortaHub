package com.monoscode.hortahub.testador.src.main.java.com.monoscode.hortahub.repositories;

import com.monoscode.hortahub.entidades.Endereco;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnderecoRepository extends JpaRepository<Endereco, Long> {

    List<Endereco> findByAssinanteId(Long assinanteId);
}
