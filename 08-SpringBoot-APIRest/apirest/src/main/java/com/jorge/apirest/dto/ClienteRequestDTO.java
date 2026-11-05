package com.jorge.apirest.dto;

import com.jorge.apirest.entity.TipoCliente;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

/** Datos que ENVÍA el cliente HTTP para crear un cliente. El id no va: lo genera la BD. */
public record ClienteRequestDTO(
        @NotBlank @Size(max = 20) String codigo,
        @NotNull TipoCliente tipo,
        LocalDate fechaAlta,   // opcional: si no viene, se pone la fecha de hoy
        Boolean activo         // opcional: si no viene, true
) {}
