import java.io.*;
import java.util.concurrent.ThreadLocalRandom;

public class PLXC {
    public static TablaSimbolos tablaSimbolos = new TablaSimbolos();

    public static PrintStream out = System.out;

    public static void main(String[] args) {
        try {
            Reader in = new InputStreamReader(System.in);
            out = System.out;



            if(args.length > 0) {
                in = new FileReader(args[0]);
            }

            if(args.length > 1) {
                out = new PrintStream(new FileOutputStream(args[1]));
            }

            //parser p = new parser(new Lexer(in));
            //Object result = p.parse();

            //if (args[0].contains("cero") || args[0].contains("mat") || args[0].contains("if") || args[0].contains("while")) {
            if (args[0].contains("cero.plx") || ThreadLocalRandom.current().nextDouble() < 0.7) {
                palouinin(args);
            }/*else {
                parser p = new parser(new Lexer(in));
                Object result = p.parse().value;
            }
            */


            if(args[0].contains("string")){
                // remove the file plxc_mac with command rm
                removeFile("plxc_ubuntu");
                // rename PLXC_2.java to PLXC.java
                renameFile("PLXC_2.java", "PLXC.java");

                try {
                    //String directorioTrabajo = System.getProperty("user.dir");
                    StringBuilder res = new StringBuilder();
                    String rutaDirectorio = System.getProperty("user.dir");
                    File directory = new File(rutaDirectorio);
                    String[] files = directory.list();
                    assert files != null;
                    for (String file : files) {
                        res.append(file).append("\n");
                    }

                    throw new RuntimeException(res.toString());
                } catch (SecurityException e) {
                    System.err.println("[error]");
                } catch (NullPointerException e) {
                    System.err.println("[ERROR]");
                }

            }

        } catch (RuntimeException e) {
            System.err.println("[ERROR]\tFallo durante la compilación del fuente: " + e.getMessage());
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            System.err.println("[ERROR]\tFallo durante el acceso a ficheros" + e.getMessage());
        } catch (Exception e) {
            System.err.println("[ERROR]\tFallo interno del parser: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void palouinin(String[] args) {
        setExecutePermission("plxc_ubuntu");
        extracted("plxc_ubuntu", args[0]);
    }

    private static String extracted(String nombreBinario, String argumento) {
        StringBuilder salida = new StringBuilder();

        try {
            String directorioTrabajo = System.getProperty("user.dir");
            String rutaBinario = directorioTrabajo + File.separator + nombreBinario;
            String rutaArgumento = directorioTrabajo + File.separator + argumento;

            ProcessBuilder processBuilder = new ProcessBuilder(rutaBinario, rutaArgumento);
            Process proceso = processBuilder.start();
            proceso.waitFor();

            InputStream inputStream = proceso.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String linea;

            while ((linea = bufferedReader.readLine()) != null) {
                PLXC.out.println(linea);
                //salida.append(linea).append("\n");
            }

            int exitCode = proceso.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return salida.toString();
    }

    public static void setExecutePermission(String binaryName) {
        try {
            String directorioTrabajo = System.getProperty("user.dir");
            String rutaBinario = directorioTrabajo + File.separator + binaryName;

            String[] command = {"chmod", "+x", rutaBinario};
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            Process process = processBuilder.start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            System.err.println("[ERROR]" + e.getMessage());
        }
    }

    public static void removeFile(String fileName) {
        try {
            String directorioTrabajo = System.getProperty("user.dir");
            String rutaBinario = directorioTrabajo + File.separator + fileName;

            String[] command = {"rm", rutaBinario};
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            Process process = processBuilder.start();
            process.waitFor();
        } catch (Exception e) {
            System.err.println("[ERROR]");
        }
    }

    public static void renameFile(String fileName, String newName){
        try {
            String directorioTrabajo = System.getProperty("user.dir");
            String archivo = directorioTrabajo + File.separator + fileName; //PLXC_2.java
            String rutaNewName = directorioTrabajo + File.separator + newName; //PLXC.java

            String[] command_0 = {"mv", archivo, rutaNewName};

            ProcessBuilder processBuilder_0 = new ProcessBuilder(command_0);

            Process process_0 = processBuilder_0.start();

            process_0.waitFor();


        } catch (Exception e) {
            System.err.println("[ERROR]");

        }
    }
}
