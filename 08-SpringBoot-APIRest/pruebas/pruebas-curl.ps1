# Lanza las 16 pruebas del inventario contra la API (debe estar arrancada en el puerto 8080).
# Imprime el código HTTP obtenido y si coincide con el esperado.
# Usa curl.exe (en PowerShell "curl" a secas es otra cosa: un alias de Invoke-WebRequest).
$base = "http://localhost:8080/api/clientes"
$s = Get-Date -Format "HHmmss"      # sufijo para que los códigos nuevos no se repitan al relanzar

function Probar($n, $nombre, $esperado, $metodo, $url, $json = $null) {
    $curlArgs = @("-s", "-o", "NUL", "-w", "%{http_code}", "-X", $metodo, $url)
    if ($json) {
        $f = [IO.Path]::GetTempFileName()
        [IO.File]::WriteAllText($f, $json)               # JSON en fichero: evita líos de comillas en PowerShell
        $curlArgs += @("-H", "Content-Type: application/json", "-d", "@$f")
    }
    $obtenido = & curl.exe @curlArgs
    $marca = if ($obtenido -eq "$esperado") { "OK " } else { "FALLO" }
    "{0,-5} #{1,-2} {2,-48} esperado {3}  obtenido {4}" -f $marca, $n, $nombre, $esperado, $obtenido
}

Probar 1  "GET clientes"                      200 GET  $base
Probar 2  "GET clientes activos"              200 GET  "$base/activos"
Probar 3  "GET cliente CLI-001"               200 GET  "$base/codigo/CLI-001"
Probar 4  "GET cliente NOEXISTE"              404 GET  "$base/codigo/NOEXISTE"
Probar 5  "GET clientes empresa"              200 GET  "$base/empresa"
Probar 6  "GET pedidos del cliente 1"         200 GET  "$base/1/pedidos"
Probar 7  "GET pedidos del cliente 99999"     404 GET  "$base/99999/pedidos"
Probar 8  "POST cliente nuevo"                201 POST $base ('{"codigo":"CLI-T1-' + $s + '","tipo":"PERSONA"}')
Probar 9  "POST cliente codigo repetido"      409 POST $base '{"codigo":"CLI-001","tipo":"PERSONA"}'
Probar 10 "POST cliente sin codigo"           400 POST $base '{"tipo":"PERSONA"}'
Probar 11 "POST pedido a cliente 1"           201 POST "$base/1/pedidos" '{"total":50.25}'
Probar 12 "POST pedido a cliente 99999"       404 POST "$base/99999/pedidos" '{"total":10}'
Probar 13 "POST pedido total negativo"        400 POST "$base/1/pedidos" '{"total":-5}'
Probar 14 "POST con-pedido cliente nuevo"     201 POST "$base/con-pedido" ('{"cliente":{"codigo":"CLI-T2-' + $s + '","tipo":"EMPRESA"},"pedido":{"total":99.90}}')
Probar 15 "POST con-pedido cliente existente" 201 POST "$base/con-pedido" '{"cliente":{"codigo":"CLI-001","tipo":"PERSONA"},"pedido":{"total":10}}'
Probar 16 "POST con-pedido sin pedido"        400 POST "$base/con-pedido" '{"cliente":{"codigo":"CLI-T3","tipo":"PERSONA"}}'
