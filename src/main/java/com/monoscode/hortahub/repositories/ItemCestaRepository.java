package com.monoscode.hortahub.repositories;

import com.monoscode.hortahub.entidades.ItemCesta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemCestaRepository extends JpaRepository<ItemCesta, Long> {

    List<ItemCesta> findByCestaId(Long cestaId);
}
