import java.util.List;
import java.util.Vector;

public class TBool extends Tipo{

    public static enum BOOL_METHODS {
        AND, OR, NOT, PRINT
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
    public boolean isParseable(Tipo tipo) {
        return true;
    }

    @Override
    public boolean isIterable() {
        return false;
    }

    @Override
    public Objeto metodosInstancia(Objeto o, String m, Vector<Objeto> p) {
        if (!(o instanceof Instancia))
            errorYPara("[ERROR]\tSolo es posible ejecutar" + m + " sobre una instancia", p);
        if (((Instancia) o).getTipoInstancia() != T_BOOL)
            errorYPara("[ERROR]\tSolo es posible ejecutar" + m + " sobre una instancia de tipo booleano", p);

        if (m.equals(BOOL_METHODS.AND.toString())) {
            checkBinBoolOper(m, p);
            return and(o, p.get(0));
        } else if (m.equals(BOOL_METHODS.OR.toString())) {
            checkBinBoolOper(m, p);
            return or(o, p.get(0));
        } else if (m.equals(BOOL_METHODS.NOT.toString())) {
            return not(o);
        } else if (m.equals(BOOL_METHODS.PRINT.name())) {
            Instancia targetInstancia = (Instancia) o;

            printBoolean(targetInstancia);
        }

        return null;
    }

    private void printBoolean(Instancia targetInstancia) {
            /*
            if (1 < 2) goto L0;
            goto L1;
            writec 116;writec 114;writec 117;writec 101;writec 10;goto L2;L1:writec 102;writec 97;writec 108;writec 115;writec 101;writec 10;L2:
             */
    }

    @Override
    public Tipo getTipo() {
        return getTBool();
    }

    @Override
    public Instancia cast(Tipo tarTipo, Instancia valor) {
        return null;
    }

    private static void checkBinBoolOper(String m, Vector<Objeto> p) {
        if (p.size() != 1 && !checkType(p.firstElement()))
            errorYPara("[ERROR]\tEl método " + m + " solo acepta un único parámetro de tipo booleano", p);
    }

    private static boolean checkType(Objeto o) {
        return (o.getClass() == Instancia.class
                && ((Instancia)o).getTipoInstancia() == T_BOOL);
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
        Instancia res = new Instancia(Objeto.newNombreObjeto(), T_BOOL, TablaSimbolos.bloqueActual, false);
        String end_if = PLXC.tablaSimbolos.getNewEtiq();

        a.getNombre();

        PLXC.out.println(res.getNombre() + " = " + a.getNombre() + ";");
        PLXC.out.println("if (" + res.getNombre() + " == 1) goto " + end_if + ";");
        PLXC.out.println(res.getNombre() + " = " + b.getNombre() + ";");
        PLXC.out.println(end_if + ":");

        return res;
    }

    /**
     * Genera el código CTD para el cálculo de la negación lógica. En CTD no existe la negación, será implementada con sentencia i:
     *      result = 0;
     *      if a == 1 goto end_not;
     *      result = 1;
     *      end_not:
     *
     * @param a Objeto de tipo booleano que se quiere negar
     * @return La negación de a
     */
    private static Objeto not(Objeto a) {
        Instancia res = new Instancia(Objeto.newNombreObjeto(), T_BOOL, TablaSimbolos.bloqueActual, false);
        String end_l = PLXC.tablaSimbolos.getNewEtiq();

        PLXC.out.println(res.getNombre() + " = 0;");
        PLXC.out.println("if (" + a.getNombre() + " == 1) goto " + end_l + ";");
        PLXC.out.println(res.getNombre() + " = 1;");
        PLXC.out.println(end_l + ":");

        return res;
    }

    public static TBool getTBool(){
        return T_BOOL;
    }
}
