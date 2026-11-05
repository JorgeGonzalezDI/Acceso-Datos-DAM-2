package com.jorge.apirest.service;

import com.jorge.apirest.dto.*;
import com.jorge.apirest.entity.Cliente;
import com.jorge.apirest.entity.Pedido;
import com.jorge.apirest.entity.TipoCliente;
import com.jorge.apirest.exception.ClienteDuplicadoException;
import com.jorge.apirest.exception.ClienteNotFoundException;
import com.jorge.apirest.mapper.ClienteMapper;
import com.jorge.apirest.mapper.PedidoMapper;
import com.jorge.apirest.repository.ClienteRepository;
import com.jorge.apirest.repository.PedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Capa de NEGOCIO. Aquí viven las reglas y las transacciones.
 * @Transactional en la clase: cada método se ejecuta en UNA transacción
 * (si algo falla a mitad, se hace rollback de todo). Los métodos de solo lectura
 * se marcan readOnly = true (más ligero para Hibernate).
 */
@Service
@Transactional
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final PedidoRepository pedidoRepository;
    private final ClienteMapper clienteMapper;
    private final PedidoMapper pedidoMapper;

    // Inyección por constructor: Spring pasa las dependencias solas
    public ClienteService(ClienteRepository clienteRepository, PedidoRepository pedidoRepository,
                          ClienteMapper clienteMapper, PedidoMapper pedidoMapper) {
        this.clienteRepository = clienteRepository;
        this.pedidoRepository = pedidoRepository;
        this.clienteMapper = clienteMapper;
        this.pedidoMapper = pedidoMapper;
    }

    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> listarTodos() {
        return clienteRepository.findAll().stream().map(clienteMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> listarActivos() {
        return clienteRepository.findByActivoTrueOrderByCodigoAsc().stream().map(clienteMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public ClienteResponseDTO buscarPorCodigo(String codigo) {
        Cliente c = clienteRepository.findByCodigo(codigo)
                .orElseThrow(() -> new ClienteNotFoundException("No existe ningún cliente con código " + codigo));
        return clienteMapper.toResponse(c);
    }

    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> listarEmpresas() {
        return clienteRepository.findByTipo(TipoCliente.EMPRESA).stream().map(clienteMapper::toResponse).toList();
    }

    public ClienteResponseDTO crearCliente(ClienteRequestDTO dto) {
        if (clienteRepository.existsByCodigo(dto.codigo())) {
            throw new ClienteDuplicadoException("Ya existe un cliente con código " + dto.codigo());
        }
        Cliente guardado = clienteRepository.save(clienteMapper.toEntity(dto));
        return clienteMapper.toResponse(guardado);
    }

    @Transactional(readOnly = true)
    public List<PedidoDTO> listarPedidosDeCliente(Long clienteId) {
        if (!clienteRepository.existsById(clienteId)) { // distingue "cliente sin pedidos" de "cliente que no existe"
            throw new ClienteNotFoundException("No existe el cliente con id " + clienteId);
        }
        return pedidoRepository.findByClienteId(clienteId).stream().map(pedidoMapper::toDto).toList();
    }

    public PedidoDTO crearPedido(Long clienteId, PedidoRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new ClienteNotFoundException("No existe el cliente con id " + clienteId));
        return pedidoMapper.toDto(guardarPedido(cliente, dto));
    }

    /**
     * Caso especial: UNA transacción para dos inserts.
     * Si el cliente (por código) no existe lo crea; si ya existe, le añade el pedido.
     * Si falla el pedido, se deshace también la creación del cliente (rollback).
     */
    public ClienteConPedidoResponseDTO crearClienteConPedido(ClienteConPedidoRequestDTO dto) {
        Cliente cliente = clienteRepository.findByCodigo(dto.cliente().codigo())
                .orElseGet(() -> clienteRepository.save(clienteMapper.toEntity(dto.cliente())));
        Pedido pedido = guardarPedido(cliente, dto.pedido());
        return new ClienteConPedidoResponseDTO(clienteMapper.toResponse(cliente), pedidoMapper.toDto(pedido));
    }

    // Asocia el pedido al cliente por los dos lados y lo guarda
    private Pedido guardarPedido(Cliente cliente, PedidoRequestDTO dto) {
        Pedido pedido = pedidoMapper.toEntity(dto);
        cliente.addPedido(pedido);
        return pedidoRepository.save(pedido);
    }
}
