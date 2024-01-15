import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Vector;

public class Pruebas {
    public static void main(String[] args) {
        //extracted();

        // Construcción de una matriz identidad 3x3
        // Cada elemento es una instancia de tipo entero con nombre  0 o 1
        List<List<Instancia>> elems = List.of(
                 List.of(
                         new Instancia("1", TInt.getTInt(), 0, false),
                         new Instancia("0", TInt.getTInt(), 0, false),
                         new Instancia("0", TInt.getTInt(), 0, false)
                 ), List.of(
                         new Instancia("0", TInt.getTInt(), 0, false),
                         new Instancia("1", TInt.getTInt(), 0, false),
                         new Instancia("0", TInt.getTInt(), 0, false)
                 ), List.of(
                        new Instancia("0", TInt.getTInt(), 0, false),
                        new Instancia("0", TInt.getTInt(), 0, false),
                        new Instancia("1", TInt.getTInt(), 0, false)
                 )
        );

        TArray tfila = new TArray(TInt.getTInt(), "3");
        TArray tmatriz = new TArray(tfila, "3");

        Instancia matriz = new Instancia("matriz", tmatriz, 0, false);

        Instancia fila1 = new Instancia("fila1", tfila, 0, false);
        for (int i = 0; i < 3; i++) {
            fila1.metodos(TArray.ARRAY_METHODS.SET.name(), new Vector<>(List.of(new Instancia(TInt.getTInt(), String.valueOf(i), false), elems.get(0).get(i))));
        }
        Instancia idx = new Instancia(TInt.getTInt(), "0", false);

        matriz.metodos(TArray.ARRAY_METHODS.SET.name(), new Vector<>(List.of(idx, fila1)));

        Instancia fila2 = new Instancia("fila2", tfila, 0, false);
        for (int i = 0; i < 3; i++) {
            fila2.metodos(TArray.ARRAY_METHODS.SET.name(), new Vector<>(List.of(new Instancia(TInt.getTInt(), String.valueOf(i), false), elems.get(1).get(i))));
        }
        idx = new Instancia(TInt.getTInt(), "1", false);

        matriz.metodos(TArray.ARRAY_METHODS.SET.name(), new Vector<>(List.of(idx, fila2)));


        Instancia fila3 = new Instancia("fila3", tfila, 0, false);
        for (int i = 0; i < 3; i++) {
            fila3.metodos(TArray.ARRAY_METHODS.SET.name(), new Vector<>(List.of(new Instancia(TInt.getTInt(), String.valueOf(i), false), elems.get(2).get(i))));
        }
        idx = new Instancia(TInt.getTInt(), "2", false);

        matriz.metodos(TArray.ARRAY_METHODS.SET.name(), new Vector<>(List.of(idx, fila3)));

        matriz.metodos(TArray.ARRAY_METHODS.PRINT.name(), new Vector<>());
    }







    private static void extracted() {
        String argumento = "/home/alejandro/Documentos/PL/practicaFinal/tablasimbolos/src/test1.plxc";  // Reemplaza "test.in" con los argumentos reales que necesita tu binario
        String nombreBinario = "/home/alejandro/Documentos/PL/practicaFinal/tablasimbolos/src/plxc";  // Reemplaza "tu_binario" con el nombre real de tu binario

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
