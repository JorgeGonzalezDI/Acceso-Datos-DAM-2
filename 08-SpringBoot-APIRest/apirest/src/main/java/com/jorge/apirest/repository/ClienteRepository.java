package com.jorge.apirest.repository;

import com.jorge.apirest.entity.Cliente;
import com.jorge.apirest.entity.TipoCliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Capa de acceso a datos. Es una INTERFAZ: Spring genera la implementación en tiempo de ejecución.
 * Los "métodos derivados" se convierten en SQL a partir del NOMBRE del método.
 */
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    // WHERE activo = true ORDER BY codigo ASC
    List<Cliente> findByActivoTrueOrderByCodigoAsc();

    // WHERE codigo = ?
    Optional<Cliente> findByCodigo(String codigo);

    // WHERE tipo = ?
    List<Cliente> findByTipo(TipoCliente tipo);

    boolean existsByCodigo(String codigo);
}
