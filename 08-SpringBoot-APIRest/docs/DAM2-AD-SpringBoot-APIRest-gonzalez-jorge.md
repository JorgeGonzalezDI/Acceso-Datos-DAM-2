# DAM2-AD-SpringBoot-APIRest — Jorge González Gómez

API REST de **Clientes y Pedidos** con Spring Boot 3, Spring Data JPA y MySQL (Docker, puerto 3307).
Reutiliza las entidades `Cliente`, `Pedido` y `TipoCliente` de la práctica anterior JDBC/JPA.

## 1. Explicación de capas

Arquitectura por capas (separación de responsabilidades, SoC): cada capa solo habla con la siguiente.

`Controller → Service → Mapper / Repository → Base de datos`

**¿DAO o Repository?** Se usa el patrón **Repository** de Spring Data JPA. En un DAO clásico escribimos a mano
una interfaz y su clase (`ClienteDAOImpl`) con el SQL/JPQL de cada operación. Aquí solo declaramos la interfaz
(`ClienteRepository extends JpaRepository<Cliente, Long>`): Spring genera la implementación al arrancar, con el
CRUD (`findAll`, `findById`, `save`...) y las consultas derivadas del nombre del método. El repository cumple el
papel del DAO (capa de acceso a datos) sin código repetitivo.

| Paquete (`src/main/java/com/jorge/apirest/...`) | Capa | Qué hace |
|---|---|---|
| `controller` (`ClienteController`) | **Presentación** | Recibe las peticiones HTTP, valida el JSON (`@Valid`), llama al service y devuelve el código de estado (200, 201...). Sin lógica de negocio. |
| `service` (`ClienteService`) | **Negocio** | Reglas de la aplicación (código duplicado, cliente inexistente, caso `con-pedido`). `@Service` + `@Transactional`: cada método es una transacción. |
| `mapper` (`ClienteMapper`, `PedidoMapper`) | **Conversión** | Entidad ↔ DTO. El controller nunca expone entidades. |
| `repository` (`ClienteRepository`, `PedidoRepository`) | **Acceso a datos** | Interfaces que extienden `JpaRepository`. Con Spring Data **el repository hace el papel del DAO** (no escribimos clases DAO a mano; Spring genera la implementación y los métodos derivados como `findByActivoTrueOrderByCodigoAsc()`). |
| `entity` (`Cliente`, `Pedido`, `TipoCliente`) | **Modelo / persistencia** | Clases JPA mapeadas a las tablas `cliente` y `pedido`. Relación 1:N bidireccional (`Pedido` es la parte propietaria con `cliente_id`). |
| `dto` (records) | **Transferencia de datos** | Formato de entrada y salida de la API (`ClienteRequestDTO`, `ClienteResponseDTO`, `PedidoRequestDTO`, `PedidoDTO`, `ClienteConPedidoRequestDTO`...). |
| `exception` | **Gestión de errores** | Excepciones de negocio y `GlobalExceptionHandler`, que las traduce a 404, 409 y 400. |

## 2. Diagrama de la pila de llamadas

Método elegido: `GET /api/clientes/codigo/{codigo}`, con el caso correcto (200) y el de error (404).

![Diagrama de la pila de llamadas](diagrama-pila-llamadas.png)

## 3. Inventario de pruebas ejecutadas

Pruebas ejecutadas el **06/11/2026** con `curl.exe -i` desde PowerShell, con la API arrancada
(`java -jar target/apirest-1.0.0.jar`) contra la MySQL de Docker (puerto 3307).
La columna **Obtenido** es la primera línea de la respuesta (`HTTP/1.1 <código>`).

| # | Prueba | Petición | Cuerpo enviado | Esperado | Obtenido | Resultado |
|---|---|---|---|---|---|---|
| 1 | Listar clientes | `GET /api/clientes` | — | 200 | 200 | ✔ OK |
| 2 | Activos ordenados por código | `GET /api/clientes/activos` | — | 200 | 200 | ✔ OK |
| 3 | Cliente por código existente | `GET /api/clientes/codigo/CLI-001` | — | 200 | 200 | ✔ OK |
| 4 | Cliente por código inexistente | `GET /api/clientes/codigo/NOEXISTE` | — | 404 | 404 | ✔ OK |
| 5 | Clientes de tipo EMPRESA | `GET /api/clientes/empresa` | — | 200 | 200 | ✔ OK |
| 6 | Pedidos de un cliente | `GET /api/clientes/1/pedidos` | — | 200 | 200 | ✔ OK |
| 7 | Pedidos de cliente inexistente | `GET /api/clientes/99999/pedidos` | — | 404 | 404 | ✔ OK |
| 8 | Crear cliente | `POST /api/clientes` | `{"codigo":"CLI-100","tipo":"EMPRESA"}` | 201 | 201 | ✔ OK |
| 9 | Crear cliente con código repetido | `POST /api/clientes` | `{"codigo":"CLI-001","tipo":"PERSONA"}` | 409 | 409 | ✔ OK |
| 10 | Crear cliente sin código (inválido) | `POST /api/clientes` | `{"tipo":"PERSONA"}` | 400 | 400 | ✔ OK |
| 11 | Crear pedido a cliente existente | `POST /api/clientes/1/pedidos` | `{"total":50.25}` | 201 | 201 | ✔ OK |
| 12 | Crear pedido a cliente inexistente | `POST /api/clientes/99999/pedidos` | `{"total":10}` | 404 | 404 | ✔ OK |
| 13 | Crear pedido con total negativo | `POST /api/clientes/1/pedidos` | `{"total":-5}` | 400 | 400 | ✔ OK |
| 14 | Cliente nuevo + pedido (misma transacción) | `POST /api/clientes/con-pedido` | `{"cliente":{"codigo":"CLI-200","tipo":"EMPRESA"},"pedido":{"total":99.90}}` | 201 | 201 | ✔ OK |
| 15 | `con-pedido` con cliente ya existente (no lo duplica) | `POST /api/clientes/con-pedido` | `{"cliente":{"codigo":"CLI-001","tipo":"PERSONA"},"pedido":{"total":10}}` | 201 | 201 | ✔ OK |
| 16 | `con-pedido` sin pedido (inválido) | `POST /api/clientes/con-pedido` | `{"cliente":{"codigo":"CLI-300","tipo":"PERSONA"}}` | 400 | 400 | ✔ OK |

**Resultado: 16/16 pruebas correctas.**

Ejemplo de comando en PowerShell (en PowerShell hay que escribir `curl.exe`, porque `curl` a secas es un alias
de `Invoke-WebRequest`; y las comillas del JSON van escapadas con `\"`):

```powershell
curl.exe -i http://localhost:8080/api/clientes/codigo/CLI-001
curl.exe -i -X POST http://localhost:8080/api/clientes -H "Content-Type: application/json" -d '{\"codigo\":\"CLI-100\",\"tipo\":\"EMPRESA\"}'
```

Además, el proyecto tiene 16 tests automáticos (`ClienteApiTest`, MockMvc + BD H2 en memoria) que cubren
los mismos casos: `mvn clean test` → `Tests run: 16, Failures: 0, Errors: 0`.

## 4. Cómo ejecutar

1. Base de datos: `docker compose up -d` en `07-JDBC-JPA\docker-mysql` (MySQL en el puerto 3307, datos de ejemplo).
2. API: abrir `apirest` en IntelliJ y ejecutar `ApiRestApplication`, o `mvn spring-boot:run`.
3. Probar: `curl.exe -i http://localhost:8080/api/clientes`

## 5. Evidencias

Carpeta `evidencias/`: capturas de pantalla completa (barra de tareas con el reloj y prompt de la terminal visibles)
de dos peticiones distintas hechas con `curl.exe -i`, donde se ve la línea `HTTP/1.1 <código>` devuelta por el servidor:

- `evidencias/01-curl-get-cliente-codigo-200.png` → `GET /api/clientes/codigo/CLI-001` → **HTTP/1.1 200** (06/11/2026 19:56)
- `evidencias/02-curl-post-cliente-201.png` → `POST /api/clientes` (cliente nuevo `CLI-102`) → **HTTP/1.1 201** (06/11/2026 19:57)
