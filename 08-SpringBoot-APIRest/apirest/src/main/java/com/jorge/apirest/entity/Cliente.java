package com.jorge.apirest.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/** Entidad Cliente: una fila de la tabla cliente. Es la parte INVERSA de la relación con Pedido. */
@Entity
@Table(name = "cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // lo genera MySQL (AUTO_INCREMENT)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String codigo;

    @Enumerated(EnumType.STRING) // guarda 'PERSONA'/'EMPRESA' (no 0/1)
    @Column(nullable = false)
    private TipoCliente tipo;

    @Column(name = "fecha_alta", nullable = false)
    private LocalDate fechaAlta;

    @Column(nullable = false)
    private boolean activo = true;

    // Parte inversa: NO tiene columna propia, la FK está en pedido.cliente_id
    @OneToMany(mappedBy = "cliente")
    private List<Pedido> pedidos = new ArrayList<>();

    public Cliente() {} // JPA lo exige

    public Cliente(String codigo, TipoCliente tipo, LocalDate fechaAlta, boolean activo) {
        this.codigo = codigo;
        this.tipo = tipo;
        this.fechaAlta = fechaAlta;
        this.activo = activo;
    }

    /** Mantiene los DOS lados de la relación bidireccional sincronizados. */
    public void addPedido(Pedido pedido) {
        pedidos.add(pedido);
        pedido.setCliente(this);
    }

    public Long getId() { return id; }
    public String getCodigo() { return codigo; }
    public TipoCliente getTipo() { return tipo; }
    public LocalDate getFechaAlta() { return fechaAlta; }
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
    public List<Pedido> getPedidos() { return pedidos; }
}
