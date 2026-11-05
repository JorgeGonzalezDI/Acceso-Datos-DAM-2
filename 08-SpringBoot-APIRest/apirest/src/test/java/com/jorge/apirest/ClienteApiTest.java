package com.jorge.apirest;

import com.jorge.apirest.entity.Cliente;
import com.jorge.apirest.entity.Pedido;
import com.jorge.apirest.entity.TipoCliente;
import com.jorge.apirest.repository.ClienteRepository;
import com.jorge.apirest.repository.PedidoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** Prueba cada endpoint contra la app completa (controller -> service -> repository -> H2). */
@SpringBootTest
@AutoConfigureMockMvc
class ClienteApiTest {

    @Autowired MockMvc mvc;
    @Autowired ClienteRepository clientes;
    @Autowired PedidoRepository pedidos;

    private Long idCli1;

    @BeforeEach
    void datos() {
        pedidos.deleteAll();
        clientes.deleteAll();
        Cliente c1 = clientes.save(new Cliente("CLI-001", TipoCliente.PERSONA, LocalDate.of(2026, 1, 15), true));
        clientes.save(new Cliente("CLI-003", TipoCliente.PERSONA, LocalDate.of(2026, 2, 20), false));
        clientes.save(new Cliente("CLI-005", TipoCliente.EMPRESA, LocalDate.of(2026, 3, 12), true));
        clientes.save(new Cliente("CLI-004", TipoCliente.EMPRESA, LocalDate.of(2026, 3, 1), true));
        Pedido p = new Pedido(LocalDate.of(2026, 10, 1), new BigDecimal("125.50"));
        c1.addPedido(p);
        pedidos.save(p);
        idCli1 = c1.getId();
    }

    @Test void getClientes_200() throws Exception {
        mvc.perform(get("/api/clientes")).andExpect(status().isOk()).andExpect(jsonPath("$", hasSize(4)));
    }

    @Test void getActivos_200_ordenadosPorCodigo() throws Exception {
        mvc.perform(get("/api/clientes/activos")).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].codigo").value("CLI-001"))
                .andExpect(jsonPath("$[1].codigo").value("CLI-004"))
                .andExpect(jsonPath("$[2].codigo").value("CLI-005"));
    }

    @Test void getPorCodigo_200() throws Exception {
        mvc.perform(get("/api/clientes/codigo/CLI-001")).andExpect(status().isOk())
                .andExpect(jsonPath("$.codigo").value("CLI-001"));
    }

    @Test void getPorCodigo_404() throws Exception {
        mvc.perform(get("/api/clientes/codigo/NOEXISTE")).andExpect(status().isNotFound());
    }

    @Test void getEmpresa_200() throws Exception {
        mvc.perform(get("/api/clientes/empresa")).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].tipo").value("EMPRESA"));
    }

    @Test void postCliente_201() throws Exception {
        mvc.perform(post("/api/clientes").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"codigo\":\"CLI-100\",\"tipo\":\"PERSONA\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.activo").value(true));
    }

    @Test void postCliente_409_codigoDuplicado() throws Exception {
        mvc.perform(post("/api/clientes").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"codigo\":\"CLI-001\",\"tipo\":\"PERSONA\"}"))
                .andExpect(status().isConflict());
    }

    @Test void postCliente_400_sinCodigo() throws Exception {
        mvc.perform(post("/api/clientes").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"tipo\":\"PERSONA\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test void getPedidosDeCliente_200() throws Exception {
        mvc.perform(get("/api/clientes/" + idCli1 + "/pedidos")).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].clienteId").value(idCli1));
    }

    @Test void getPedidosDeCliente_404() throws Exception {
        mvc.perform(get("/api/clientes/99999/pedidos")).andExpect(status().isNotFound());
    }

    @Test void postPedido_201() throws Exception {
        mvc.perform(post("/api/clientes/" + idCli1 + "/pedidos").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"total\":50.25}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.clienteId").value(idCli1));
        mvc.perform(get("/api/clientes/" + idCli1 + "/pedidos")).andExpect(jsonPath("$", hasSize(2)));
    }

    @Test void postPedido_404_clienteInexistente() throws Exception {
        mvc.perform(post("/api/clientes/99999/pedidos").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"total\":10}"))
                .andExpect(status().isNotFound());
    }

    @Test void postPedido_400_totalNegativo() throws Exception {
        mvc.perform(post("/api/clientes/" + idCli1 + "/pedidos").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"total\":-5}"))
                .andExpect(status().isBadRequest());
    }

    @Test void postConPedido_201_clienteNuevo() throws Exception {
        mvc.perform(post("/api/clientes/con-pedido").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"cliente\":{\"codigo\":\"CLI-200\",\"tipo\":\"EMPRESA\"},\"pedido\":{\"total\":99.90}}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.cliente.codigo").value("CLI-200"))
                .andExpect(jsonPath("$.pedido.total").value(99.90));
        mvc.perform(get("/api/clientes/codigo/CLI-200")).andExpect(status().isOk());
    }

    @Test void postConPedido_201_clienteExistente_noDuplica() throws Exception {
        mvc.perform(post("/api/clientes/con-pedido").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"cliente\":{\"codigo\":\"CLI-001\",\"tipo\":\"PERSONA\"},\"pedido\":{\"total\":10}}"))
                .andExpect(status().isCreated());
        mvc.perform(get("/api/clientes")).andExpect(jsonPath("$", hasSize(4))); // sigue habiendo 4 clientes
        mvc.perform(get("/api/clientes/" + idCli1 + "/pedidos")).andExpect(jsonPath("$", hasSize(2)));
    }

    @Test void postConPedido_400_sinPedido() throws Exception {
        mvc.perform(post("/api/clientes/con-pedido").contentType(MediaType.APPLICATION_JSON)
                        .content("{\"cliente\":{\"codigo\":\"CLI-300\",\"tipo\":\"PERSONA\"}}"))
                .andExpect(status().isBadRequest());
    }
}
