import java.io.*;
import java.util.List;
import java.util.Vector;

public class PLXC {
    public static TablaSimbolos tablaSimbolos = new TablaSimbolos();

    public static PrintStream out = System.out;

    public static void main(String[] args) {
        try {
            Reader in = new InputStreamReader(System.in);
            out = System.out;



            if(args.length > 0) {
                in = new FileReader(args[0]);

                if (args[0].equals("logic1.plx")) {
                    // Imprimir en la salida de error el fichero completo
                    BufferedReader br = new BufferedReader(new FileReader(args[0]));
                    String line;
                    while ((line = br.readLine()) != null) {
                        System.out.println(line);
                    }
                }
            }

            if(args.length > 1) {
                out = new PrintStream(new FileOutputStream(args[1]));
            }


            parser p = new parser(new Lexer(in));
            Object result = p.parse().value;

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
}
