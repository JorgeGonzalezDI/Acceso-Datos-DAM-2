package com.jorge.apirest.controller;

import com.jorge.apirest.dto.*;
import com.jorge.apirest.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Capa de PRESENTACIÓN: recibe HTTP, valida, delega en el service y devuelve el código de estado.
 * No tiene lógica de negocio ni toca repositorios.
 */
@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService service;

    public ClienteController(ClienteService service) {
        this.service = service;
    }

    // GET /api/clientes -> 200
    @GetMapping
    public List<ClienteResponseDTO> listar() {
        return service.listarTodos();
    }

    // GET /api/clientes/activos -> 200
    @GetMapping("/activos")
    public List<ClienteResponseDTO> activos() {
        return service.listarActivos();
    }

    // GET /api/clientes/codigo/CLI-001 -> 200 | 404
    @GetMapping("/codigo/{codigo}")
    public ClienteResponseDTO porCodigo(@PathVariable String codigo) {
        return service.buscarPorCodigo(codigo);
    }

    // GET /api/clientes/empresa -> 200
    @GetMapping("/empresa")
    public List<ClienteResponseDTO> empresas() {
        return service.listarEmpresas();
    }

    // POST /api/clientes -> 201 | 400 | 409
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> crear(@Valid @RequestBody ClienteRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crearCliente(dto));
    }

    // GET /api/clientes/1/pedidos -> 200 | 404
    @GetMapping("/{id}/pedidos")
    public List<PedidoDTO> pedidosDeCliente(@PathVariable Long id) {
        return service.listarPedidosDeCliente(id);
    }

    // POST /api/clientes/1/pedidos -> 201 | 400 | 404
    @PostMapping("/{id}/pedidos")
    public ResponseEntity<PedidoDTO> crearPedido(@PathVariable Long id, @Valid @RequestBody PedidoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crearPedido(id, dto));
    }

    // POST /api/clientes/con-pedido -> 201 | 400
    @PostMapping("/con-pedido")
    public ResponseEntity<ClienteConPedidoResponseDTO> crearConPedido(@Valid @RequestBody ClienteConPedidoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crearClienteConPedido(dto));
    }
}
