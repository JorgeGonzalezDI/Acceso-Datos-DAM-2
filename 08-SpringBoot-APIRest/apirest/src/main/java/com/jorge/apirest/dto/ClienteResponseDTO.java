package com.jorge.apirest.dto;

import com.jorge.apirest.entity.TipoCliente;

import java.time.LocalDate;

/** Datos que DEVUELVE la API. Sin la lista de pedidos: así evitamos bucles JSON cliente<->pedido. */
public record ClienteResponseDTO(
        Long id,
        String codigo,
        TipoCliente tipo,
        LocalDate fechaAlta,
        boolean activo
) {}
