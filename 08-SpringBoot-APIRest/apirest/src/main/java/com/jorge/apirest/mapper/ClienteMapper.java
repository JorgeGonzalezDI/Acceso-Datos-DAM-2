package com.jorge.apirest.mapper;

import com.jorge.apirest.dto.ClienteRequestDTO;
import com.jorge.apirest.dto.ClienteResponseDTO;
import com.jorge.apirest.entity.Cliente;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/** Convierte Entidad <-> DTO a mano (sin librerías). Así el controller nunca ve entidades. */
@Component
public class ClienteMapper {

    /** DTO de entrada -> Entidad (aplica valores por defecto). */
    public Cliente toEntity(ClienteRequestDTO dto) {
        return new Cliente(
                dto.codigo(),
                dto.tipo(),
                dto.fechaAlta() != null ? dto.fechaAlta() : LocalDate.now(),
                dto.activo() != null ? dto.activo() : true
        );
    }

    /** Entidad -> DTO de salida. */
    public ClienteResponseDTO toResponse(Cliente c) {
        return new ClienteResponseDTO(c.getId(), c.getCodigo(), c.getTipo(), c.getFechaAlta(), c.isActivo());
    }
}
