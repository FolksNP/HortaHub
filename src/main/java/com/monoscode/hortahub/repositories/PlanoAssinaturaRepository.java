package com.monoscode.hortahub.repositories;

import com.monoscode.hortahub.entidades.PlanoAssinatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlanoAssinaturaRepository extends JpaRepository<PlanoAssinatura, Integer> {}
