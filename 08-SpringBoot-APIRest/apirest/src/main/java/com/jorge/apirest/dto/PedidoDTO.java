package com.jorge.apirest.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

/** Pedido tal como se devuelve por la API. Solo el id del cliente, no el cliente entero. */
public record PedidoDTO(
        Long id,
        LocalDate fecha,
        BigDecimal total,
        Long clienteId
) {}
