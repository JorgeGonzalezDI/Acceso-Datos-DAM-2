package com.jorge.apirest.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/** Cuerpo del caso especial POST /api/clientes/con-pedido: cliente + pedido en una petición. */
public record ClienteConPedidoRequestDTO(
        @NotNull @Valid ClienteRequestDTO cliente,
        @NotNull @Valid PedidoRequestDTO pedido
) {}
