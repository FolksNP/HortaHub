package com.monoscode.hortahub.repositories;

import com.monoscode.hortahub.entidades.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    Optional<Pedido> findByProtocolo(String protocolo);
}
