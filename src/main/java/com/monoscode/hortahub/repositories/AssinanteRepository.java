package com.monoscode.hortahub.repositories;

import com.monoscode.hortahub.entidades.Assinante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssinanteRepository extends JpaRepository<Assinante, Long> {

    Optional<Assinante> findByTelefone(String telefone);

    Optional<Assinante> findByCpf(String cpf);


}
