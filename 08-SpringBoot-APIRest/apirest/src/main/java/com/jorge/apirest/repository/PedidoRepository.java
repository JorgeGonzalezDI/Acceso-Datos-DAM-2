package com.jorge.apirest.repository;

import com.jorge.apirest.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    // WHERE cliente_id = ?  (navega la propiedad cliente -> id)
    List<Pedido> findByClienteId(Long clienteId);
}
