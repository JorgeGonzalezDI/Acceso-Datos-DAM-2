/**
 * Hoja de ejercicios para completar en clase.
 * Cada método contiene un TODO y se puede resolver con Stream y Optional.
 */
record Producto(String codigo, String nombre, String categoria,
                double precio, int stock) {
}

record Incidencia(int id, String usuario, String descripcion,
                  String prioridad, boolean resuelta) {
}

void main() {
    List<Producto> productos = datosProductos();
    List<Incidencia> incidencias = datosIncidencias();

   System.out.println("Completa los métodos TODO y ejecuta de nuevo.");
   System.out.println("1. " + productosConStockBajo(productos));
   System.out.println("2. " + nombresDeInformatica(productos));
   System.out.println("3. " + precioMedio(productos));
   System.out.println("4. " + buscarProducto(productos, "P003"));
   System.out.println("5. " + resumenIncidenciasAbiertas(incidencias));
}

// Ejercicio 1: conservar solo productos con stock inferior a 5.
static List<Producto> productosConStockBajo(List<Producto> productos) {
    // TODO: usar stream, filter y toList.
    return productos.stream()
            .filter(prod -> prod.stock() < 5)
            .toList();
}

// Ejercicio 2: obtener los nombres de productos de Informática en mayúsculas.
static List<String> nombresDeInformatica(List<Producto> productos) {
    // TODO: usar filter, map y toList.
    return productos.stream()
            .filter(prod -> prod.categoria().equals("Informática"))
            .map(prod -> prod.nombre().toUpperCase())
            .toList();
}

// Ejercicio 3: calcular el precio medio de todos los productos.
static Optional<Double> precioMedio(List<Producto> productos) {
    // TODO: usar mapToDouble y average.
    OptionalDouble media = productos.stream()
            .mapToDouble(Producto::precio)
          .average();
}

// Ejercicio 4: localizar un producto por código sin devolver null.
static Optional<Producto> buscarProducto(
        List<Producto> productos, String codigo) {
    // TODO: usar filter y findFirst.
    throw new UnsupportedOperationException("TODO ejercicio 4");
}

// Ejercicio 5: obtener resúmenes de incidencias abiertas, ordenadas por prioridad.
static List<String> resumenIncidenciasAbiertas(
        List<Incidencia> incidencias) {
    // TODO: usar filter, sorted, map y toList.
    throw new UnsupportedOperationException("TODO ejercicio 5");
}

private static List<Producto> datosProductos() {
    return List.of(
            new Producto("P001", "Teclado", "Informática", 25.90, 12),
            new Producto("P002", "Ratón", "Informática", 14.50, 3),
            new Producto("P003", "Monitor", "Informática", 189.99, 4),
            new Producto("P004", "Silla", "Mobiliario", 120.00, 8),
            new Producto("P005", "Lámpara", "Mobiliario", 32.75, 2)
    );
}

private static List<Incidencia> datosIncidencias() {
    return List.of(
            new Incidencia(1, "Ana", "No puede acceder", "ALTA", false),
            new Incidencia(2, "Luis", "Error de impresión", "MEDIA", true),
            new Incidencia(3, "Marta", "Contraseña caducada", "ALTA", false),
            new Incidencia(4, "Pablo", "Consulta sobre el informe", "BAJA", false)
    );
}
