package com.jorge.apirest.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.time.LocalDate;

/** Datos para crear un pedido. El cliente se indica por la URL (o por el body en con-pedido). */
public record PedidoRequestDTO(
        LocalDate fecha,                      // opcional: por defecto hoy
        @NotNull @Positive BigDecimal total
) {}
