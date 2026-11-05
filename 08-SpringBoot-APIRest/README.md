# 08 · Spring Boot API REST (Clientes y Pedidos)

- `apirest\` proyecto Maven (abrir con IntelliJ: File → Open sobre esta carpeta, la del `pom.xml`).
- `docs\` documento de entrega (.md) + diagrama de la pila de llamadas (.png).
- `pruebas\pruebas-curl.ps1` lanza las 16 pruebas del inventario y muestra los códigos HTTP.
- `evidencias\` aquí van TUS capturas de pantalla completa (reloj/barra de tareas visibles).
- `empaquetar-entrega.ps1` crea `DAM2-AD-SpringBoot-APIRest-gonzalez-jorge.zip`.

BD: la de `..\07-JDBC-JPA\docker-mysql` (`docker compose up -d`, puerto 3307).
Orden: arrancar Docker → `mvn test` → arrancar la app → `.\pruebas\pruebas-curl.ps1` → capturas → `.\empaquetar-entrega.ps1`.
