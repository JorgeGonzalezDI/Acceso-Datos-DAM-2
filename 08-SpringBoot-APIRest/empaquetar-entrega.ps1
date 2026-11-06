# Monta DAM2-AD-SpringBoot-APIRest-gonzalez-jorge.zip con:
#   src/main/java (desde la raíz), resources, pom.xml, documentación (.md + diagrama) y carpeta evidencias.
# Usa ZipFile con rutas "/" (Compress-Archive de PowerShell 5 mete "\").
Add-Type -AssemblyName System.IO.Compression, System.IO.Compression.FileSystem
$base = $PSScriptRoot
$zip  = Join-Path $base "DAM2-AD-SpringBoot-APIRest-gonzalez-jorge.zip"
if (Test-Path $zip) { Remove-Item $zip -Force }
$z = [IO.Compression.ZipFile]::Open($zip, [IO.Compression.ZipArchiveMode]::Create)
function Add($ruta, $entrada) { [IO.Compression.ZipFileExtensions]::CreateEntryFromFile($z, $ruta, $entrada) | Out-Null }
try {
    $proy = Join-Path $base "apirest"
    foreach ($carpeta in "src\main\java", "src\main\resources") {
        $raiz = Join-Path $proy $carpeta
        Get-ChildItem $raiz -Recurse -File | ForEach-Object {
            $rel = $_.FullName.Substring($proy.Length + 1).Replace("\", "/")
            Add $_.FullName $rel
        }
    }
    Add (Join-Path $proy "pom.xml") "pom.xml"
    Add (Join-Path $base "docs\DAM2-AD-SpringBoot-APIRest-gonzalez-jorge.md") "DAM2-AD-SpringBoot-APIRest-gonzalez-jorge.md"
    Add (Join-Path $base "docs\diagrama-pila-llamadas.png") "diagrama-pila-llamadas.png"
    Get-ChildItem (Join-Path $base "evidencias") -File | ForEach-Object { Add $_.FullName ("evidencias/" + $_.Name) }
} finally { $z.Dispose() }
Write-Host "Creado: $zip"
