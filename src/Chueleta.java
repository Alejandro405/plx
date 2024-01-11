import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Chueleta {
    public static void main(String[] args) {
        String nombreBinario = "/home/alejandro/Documentos/PL/practicaFinal/tablasimbolos/src/plxc";  // Reemplaza "tu_binario" con el nombre real de tu binario
        String argumento = "/home/alejandro/Documentos/PL/practicaFinal/tablasimbolos/src/test1.plxc";  // Reemplaza "test.in" con los argumentos reales que necesita tu binario

        try {
            ProcessBuilder processBuilder = new ProcessBuilder(nombreBinario, argumento);

            // Configurar el directorio de trabajo si es necesario
            // processBuilder.directory(new File("/ruta/a/tu/directorio"));

            Process proceso = processBuilder.start();

            // Redirigir la salida estándar del proceso
            InputStream inputStream = proceso.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String linea;

            while ((linea = bufferedReader.readLine()) != null) {
                System.out.println(linea);
            }

            // Esperar a que el proceso termine y obtener el código de salida
            int exitCode = proceso.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
