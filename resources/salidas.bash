#!/bin/bash



# Iterar sobre los archivos del directorio
for archivo in ls ./tests/; do
    # Verificar que el elemento es un archivo
    if [ -f "$archivo" ]; then
        # Obtener el nombre del archivo sin la ruta ni la extensión
        nombre_archivo_sin_extension=$(basename "$archivo" .plx)

        # Imprimir el nombre del archivo antes de pasar como entrada
        echo "Procesando archivo: $nombre_archivo_sin_extension"

        # Pasar el archivo como entrada al binario plxc y dirigir la salida al directorio de salidas
        # shellcheck disable=SC1009
        plxc < "$archivo" > "./salidas/$nombre_archivo_sin_extension.salida"
    fi
done
