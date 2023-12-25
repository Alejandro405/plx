import java.util.List;
import java.util.Vector;

public class TBool extends Tipo{

    public static enum BOOL_METHODS {
        AND, OR, NOT
    }

    private static final TBool T_BOOL = new TBool();

    public TBool() {
        super(String.valueOf(Tipos_PL.BOOL), 0, false);
    }

    @Override
    public Objeto metodos(String metodo, Vector<Objeto> params) {
        errorYPara("[ERROR]\tNo se puede ejecutar un método sobre un tipo, solo es posible sobre una instancia", params);
        return null;
    }

    @Override
    public Objeto metodosInstancia(Objeto o, String m, Vector<Objeto> p) {
        return null;
    }

    private static boolean checkType(Objeto o ) {
        return ((Instancia)o).getTipoInstancia() == T_BOOL
                && o.getClass() == Instancia.class;
    }

    /**
     * Genera el CTD necesario para realizar el and lógico entre dos objetos de tipo booleano. En CTD no existe el and lógico, será implementada con if's:
     *      resultado = A;
     *      if resultado == 0 goto end_and;
     *          resultado = B;
     *      end_and:
     *
     * @param a Objeto booleano
     * @param b Objeto booleano
     * @return a & b
     */
    private static Objeto and(Objeto a, Objeto b) {
        // Checkeo de tipos de a y b
        if (!checkType(a) || !checkType(b)) {
            errorYPara("Los parámetros de la operación and deben ser de tipo booleano", new Vector<>(List.of(a, b)));
        }

        Objeto res = new Instancia(newNombreObjeto(), T_BOOL, TablaSimbolos.bloqueActual, false);
        PLXC.out.println(res.getNombre() + "=" + a.getNombre() + "*" + b.getNombre() + ";");


        return res;
    }

    /**
     * Genera el CTD necesario para realizar el or lógico entre dos objetos de tipo booleano. En CTD no existe el or lógico, será implementada con if's:
     *      resultado = A;
     *      if resultado == 1 goto end_or;
     *      resultado = B;
     *      end_or:
     *
     * @param a Objeto booleano
     * @param b Objeto booleano
     * @return Objeto dentro de la tabla de símbolos con el resultado de la operacion <a | b>
     */
    private static Objeto or(Objeto a, Objeto b) {
        return null;
    }

    /**
     * Genera el código CTD para el cálculo de la negación lógica. En CTD no existe la negación, será implementada con sentencia i:
     *      if a == 0 goto l;
     *          a=0;
     *          goto end_if;
     *       l:
     *          a = 1;
     *       end_if:
     *
     * @param a Objeto de tipo booleano que se quiere negar
     * @return La negación de a
     */
    private static Objeto not(Objeto a) {
        return null;
    }

    public static TBool getTBool(){
        return T_BOOL;
    }
}
