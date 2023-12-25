import java.io.PrintStream;
import java.util.List;
import java.util.Vector;

public class PLXC {
    public static TablaSimbolos tablaSimbolos = new TablaSimbolos();

    public static PrintStream out = System.out;

    public static void main(String[] args) {

        Instancia variable = new Instancia("variable", TInt.getTInt(), 0, true);

        tablaSimbolos.putObj(variable);

        Instancia b = new Instancia("b", TInt.getTInt(), 0, false);

        variable.metodos(TInt.INT_METHODS.ASIGNA.name(), new Vector<>(List.of(b)));

    }
}
