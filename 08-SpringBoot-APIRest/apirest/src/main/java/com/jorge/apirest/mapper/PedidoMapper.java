package com.jorge.apirest.mapper;

import com.jorge.apirest.dto.PedidoDTO;
import com.jorge.apirest.dto.PedidoRequestDTO;
import com.jorge.apirest.entity.Pedido;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class PedidoMapper {

    /** DTO de entrada -> Entidad. El cliente se asocia después, en el service. */
    public Pedido toEntity(PedidoRequestDTO dto) {
        return new Pedido(dto.fecha() != null ? dto.fecha() : LocalDate.now(), dto.total());
    }

    /** Entidad -> DTO. getCliente().getId() no dispara consulta extra (el id ya está en el proxy). */
    public PedidoDTO toDto(Pedido p) {
        return new PedidoDTO(p.getId(), p.getFecha(), p.getTotal(), p.getCliente().getId());
    }
}
