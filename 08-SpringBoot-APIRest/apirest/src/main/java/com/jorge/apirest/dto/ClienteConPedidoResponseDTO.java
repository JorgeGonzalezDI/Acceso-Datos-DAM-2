package com.jorge.apirest.dto;

/** Respuesta de con-pedido: el cliente (nuevo o ya existente) y el pedido creado. */
public record ClienteConPedidoResponseDTO(
        ClienteResponseDTO cliente,
        PedidoDTO pedido
) {}
