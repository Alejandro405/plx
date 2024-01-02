import java.util.List;
import java.util.Vector;

/**
 * Clase que alberga el comportamiento y las propiedades del tipo entero para el lenguaje PLC
 */
public class TInt extends Tipo{

    public static enum INT_METHODS {
        ASIGNA, UMENOS, SUMA, RESTA, MULT, DIV, MAYOR, MENOR, MAYOR_IGUAL, MENOR_IGUAL, IGUAL, DISTINTO, PRINT
    }

    private static TInt T_INT = new TInt();

    private TInt() {
        super(String.valueOf(Tipos_PL.INT), 0, false);
    }

    @Override
    public Objeto metodos(String metodo, Vector<Objeto> params) {
        errorYPara("no se puede ejecutar un método sobre un tipo, solo es posible sobre una instancia", params);
        return null;
    }

    @Override
    public boolean isParseable(Tipo tarTipo) {
        //((Instancia)exp).getTipoInstancia()
        //      .cast(tipoDst, (Instancia) exp);
        return true;
    }

    @Override
    public Objeto metodosInstancia(Objeto o, String m, Vector<Objeto> p) {
        if (!(o instanceof Instancia))  errorYPara("Error de tipo, el objeto no es una instancia", p);
        if (((Instancia) o ).getTipoInstancia() != T_INT)    errorYPara("Error de tipo, el objto sí es una instancia pero no es un entero", p);

        // LLegados hasta aquí, estamos seguros de que o es una instancia de tipo entero
        if (m.equals(INT_METHODS.ASIGNA.name())) {
            Tipo aux = ((Instancia)p.firstElement()).getTipoInstancia();
            if (!(p.size() == 1 && aux == T_INT))
                errorYPara("La asignación solo opera con un argumento de tipo entero, será necesario un casteo del valor para asignar", p);
            if (!o.getMutable())
                errorYPara("La variable sobre la que se realizó la asignación no es mutable", new Vector<>(List.of(o, p.firstElement())));

            Objeto valor = p.firstElement();
            PLXC.out.println(o.getNombre() + " = " + valor.getNombre() + ";");

            return o;
        }

        if (m.equals(INT_METHODS.SUMA.name())) {
            checkBinaryOp("Suma", p);

            return sumaDosEnteros(o, p.firstElement());
        }
        
        if (m.equals(INT_METHODS.RESTA.name())) {
            checkBinaryOp("Resta", p);

            return restaDosEnteros(o, p.firstElement());
        }
        
        if (m.equals(INT_METHODS.MULT.name())) {
            checkBinaryOp("Multiplicación", p);

            return multiplicaDosEnteros(o, p.firstElement());
        }
        
        if (m.equals(INT_METHODS.DIV.name())) {
            checkBinaryOp("Division", p);

            return divideDosEnteros(o, p.firstElement());
        }

        if (m.equals(INT_METHODS.UMENOS.name())) {
            if (!p.isEmpty())
                errorYPara("La función umenos no necesita parámetros", p);

            Instancia cero = new Instancia("0", T_INT, TablaSimbolos.bloqueActual, false);

            return restaDosEnteros(cero, o);
        }

        if (m.equals(INT_METHODS.MAYOR.name())) {
            checkBinaryOp("MAYOR_QUE", p);

            return mayorQueDosEnteros(o, p.firstElement());
        }

        if (m.equals(INT_METHODS.MENOR.name())) {
            checkBinaryOp("MENOR_QUE", p);

            return menorQueDosEnteros(o, p.firstElement());
        }

        if (m.equals(INT_METHODS.MAYOR_IGUAL.name())) {
            checkBinaryOp("MAYOR_IGUAL_QUE", p);

            return mayorIgualQueDosEnteros(o, p.firstElement());
        }

        if (m.equals(INT_METHODS.MENOR_IGUAL.name())) {
            checkBinaryOp("MENOR_IGUAL_QUE", p);

            return menorIgualQueDosEnteros(o, p.firstElement());
        }

        if (m.equals(INT_METHODS.IGUAL.name())) {
            checkBinaryOp("IGUAL_QUE", p);

            return igualQueDosEnteros(o, p.firstElement());
        }

        if (m.equals(INT_METHODS.DISTINTO.name())) {
            checkBinaryOp("DISTINTO_QUE", p);

            return distintoQueDosEnteros(o, p.firstElement());
        }

        if (m.equals(INT_METHODS.PRINT.name())) {
            if (!p.isEmpty())
                errorYPara("La función print no necesita parámetros", p);

            PLXC.out.println("print " + o.getNombre() + " ;");
            return null;
        }


        errorYPara("Operación no contemplada para el tipo entero. La operación en cuestión es [".concat(m).concat("]"), p);

        return null;
    }

    @Override
    public Tipo getTipo() {
        return T_INT;
    }


    @Override
    public Instancia cast(Tipo tarTipo, Instancia valor) {
        // ((Instancia)exp).getTipoInstancia()
        //                 .cast(tipoDst, (Instancia) exp);
        // Checkear el tipo al que se quiere castear
        if (tarTipo == TChar.getTChar()) {
            return new Instancia(valor.getNombre(), tarTipo, TablaSimbolos.bloqueActual, false);
        } else if (tarTipo == TFloat.getTFloat()) {
            Instancia res = new Instancia(tarTipo);
            PLXC.out.println(res.getNombre() + " = (float) " + valor.getNombre() + ";");
            return res;
        } else if (tarTipo == T_INT) {
            Instancia res = new Instancia(tarTipo);
            PLXC.out.println(res.getNombre() + " = " + valor.getNombre() + ";");
            return res;
        } else {
            errorYPara("No se puede castear el tipo entero a ".concat(tarTipo.getNombre()), new Vector<>(List.of(valor)));
            return null;
        }
    }

    private static void checkBinaryOp(String op, Vector<Objeto> p) {
        if (p.size() != 1)
            errorYPara("La " + op + " para el tipo entero solo se contempla para dos valores", p);
        if (!sameType(p.firstElement()))
            errorYPara("Los parámetros para la " + op + " no son del tipo correcto (se necesitan tipos numéricos): " + p.toString(), p);
    }

    public static TInt getTInt() {
        return T_INT;
    }

    private static boolean sameType(Objeto o ) {
        Tipo aux = ((Instancia)o).getTipoInstancia();
        return (aux == T_INT || aux == TFloat.getTFloat() || aux == TChar.getTChar())
                && o.getClass() == Instancia.class;
    }

    private static boolean sameType(Vector<Objeto> p ) {
        return p.stream().allMatch(TInt::sameType);
    }

    /**
     * Genera el CTD necesario para realizar la suma entre dos objetos de tipo entero. CTD para la suma de dos valores:
     *
     *    x = a + b;
     *
     * @param o Objeto entero
     * @param p Objeto entero
     * @return Objeto dentro de la tabla de símbolos con el resultado de la operacion <a + b>
     */
    private static Objeto sumaDosEnteros(Objeto o, Objeto p) {
        Instancia aux = (Instancia) p;
        // Obj contrndrá la etiqueta con el resultado de la suma dentro de la tabla de símbolos
        Instancia obj = null;

        if (aux.getTipoInstancia() == T_INT || aux.getTipoInstancia() == TChar.getTChar()) {
            obj = new Instancia(Objeto.newNombreObjeto(), T_INT,TablaSimbolos.bloqueActual, false);

            PLXC.out.println(obj.getNombre() + " = " + o.getNombre() + " + " + p.getNombre() + ";");
        } else {
            obj = new Instancia(Objeto.newNombreObjeto(), TFloat.getTFloat(), TablaSimbolos.bloqueActual, false);
            PLXC.out.println(obj.getNombre() + " = " + o.getNombre() + " +r " + p.getNombre() + ";");
        }
        /**/
        return obj;
    }

    /**
     * Genera el CTD necesario para realizar la resta entre dos objetos de tipo entero. CTD para la resta de entera de dos valores:
     *
     *   x = a - b;
     *
     * @param o Objeto entero
     * @param p Parámetro de tipo entero para la resta. Solo 1
     * @return Objeto dentro de la tabla de símbolos con el resultado de la operacion <a - b>
     */
    private static Objeto restaDosEnteros(Objeto o, Objeto p) {
        Instancia result = null;
        Instancia aux = (Instancia) p;
        if (aux.getTipoInstancia() == T_INT || aux.getTipoInstancia() == TChar.getTChar()) {
            result = new Instancia(Objeto.newNombreObjeto(), T_INT, TablaSimbolos.bloqueActual, false);
            PLXC.out.println(result.getNombre() + " =" + o.getNombre() + " - " + p.getNombre() + " ;");
        } else {
            result = new Instancia(Objeto.newNombreObjeto(), TFloat.getTFloat(), TablaSimbolos.bloqueActual, false);
            PLXC.out.println(result.getNombre() + " =" + o.getNombre() + " -r " + p.getNombre() + " ;");
        }

        return result;
    }

    /**
     * Genera el CTD necesario para realizar la división entre dos objetos de tipo entero. CTD para la división de entera de dos valores:
     *
     *   x = a / b;
     *
     * @param o Objeto entero
     * @param p Parámetro de tipo entero para la división. Solo 1
     * @return Objeto dentro de la tabla de símbolos con el resultado de la operacion <a / b>
     */
    private static Objeto divideDosEnteros(Objeto o, Objeto p) {
        Instancia aux =  (Instancia) p;
        Instancia result = null;
        if (aux.getTipoInstancia() == T_INT || aux.getTipoInstancia() == TChar.getTChar()) {
            result = new Instancia(Objeto.newNombreObjeto(), T_INT, TablaSimbolos.bloqueActual, false);
            PLXC.out.println(result.getNombre() + " =" + o.getNombre() + " / " + p.getNombre() + " ;");
        } else {
            result = new Instancia(Objeto.newNombreObjeto(), TFloat.getTFloat(), TablaSimbolos.bloqueActual, false);
            PLXC.out.println(result.getNombre() + " =" + o.getNombre() + " /r " + p.getNombre() + " ;");
        }



        return result;
    }

    /**
     * Genera el CTD necesario para realizar la multiplicación entre dos objetos de tipo entero. CTD para la multiplicación de entera de dos valores:
     *    x = a * b;
     *
     * @param o Objeto entero
     * @param p Parámetro de tipo entero para la multiplicación. Solo 1
     * @return Objeto dentro de la tabla de símbolos con el resultado de la operacion <a * b>
     */
    private static Objeto multiplicaDosEnteros(Objeto o, Objeto p) {
        Instancia result = new Instancia(Objeto.newNombreObjeto(), T_INT, TablaSimbolos.bloqueActual, false);
        PLXC.out.println(result.getNombre() + " =" + o.getNombre() + " * " + p.getNombre() + " ;");

        return result;
    }

    /**
     * Genera el CTD necesario para realizar la comparación mayor que entre dos objetos de tipo entero. CTD para la comparación mayor que de dos valores:
     *
     *    result = 1
     *    if b < a goto l;
     *      result = 0;
     *    l:
     *
     * @param a Objeto entero
     * @param b Parámetro de tipo entero para la comparación.
     * @return Objeto dentro de la tabla de símbolos con el resultado de la operacion <a > b>
     */
    private static Objeto mayorQueDosEnteros(Objeto a, Objeto b) {
        return genIfLessThan(b, a);
    }

    /**
     * Genera el CTD necesario para realizar la comparación menor que entre dos objetos de tipo entero. CTD para la comparación menor que de dos valores:
     *
     *    result = 1
     *    if a < b goto l;
     *      result = 0;
     *    l:
     *
     * @param a Objeto entero
     * @param b Parámetro de tipo entero para la comparación.
     * @return Objeto dentro de la tabla de símbolos con el resultado de la operacion <a < b>
     */
    private static Objeto menorQueDosEnteros(Objeto a, Objeto b) {
        return genIfLessThan(a, b);
    }

    /**
     * Genera el CTD necesario para realizar la comparación mayor o igual que entre dos objetos de tipo entero. CTD para la comparación mayor o igual que de dos valores:
     *
     *    result = 1
     *    if b < a goto l;
     *    if a == b goto l;
     *          result = 0;
     *    l:
     *
     * @param a Objeto entero
     * @param b Parámetro de tipo entero para la comparación.
     * @return Objeto dentro de la tabla de símbolos con el resultado de la operacion <a >= b>
     */
    private static Objeto mayorIgualQueDosEnteros(Objeto a, Objeto b) {
        return genIfLessOrEqual(b, a);
    }



    /**
     * Genera el CTD necesario para realizar la comparación menor o igual que entre dos objetos de tipo entero. CTD para la comparación menor o igual que de dos valores:
     *
     *    result = 1
     *    if a < b goto l;
     *    if a == b goto l;
     *          result = 0;
     *    l:
     *
     * @param a Objeto entero
     * @param b Parámetro de tipo entero para la comparación.
     * @return Objeto dentro de la tabla de símbolos con el resultado de la operacion <a <= b>
     */
    private static Objeto menorIgualQueDosEnteros(Objeto a, Objeto b) {
        return genIfLessOrEqual(a, b);
    }

    /**
     * Genera el CTD necesario para realizar la comparación igual que entre dos objetos de tipo entero. CTD para la comparación igual que de dos valores:
     *
     *    result = 1
     *    if a == b goto l;
     *          result = 0;
     *    l:
     *
     * @param a Objeto entero
     * @param b Parámetro de tipo entero para la comparación.
     * @return Objeto dentro de la tabla de símbolos con el resultado de la operacion <a == b>
     */
    private static Objeto igualQueDosEnteros(Objeto a, Objeto b) {
        Instancia res = new Instancia(Objeto.newNombreObjeto(), TBool.getTBool(), TablaSimbolos.bloqueActual, false);
        String l = PLXC.tablaSimbolos.getNewEtiq();

        PLXC.out.println(res.getNombre() + " = 1;");
        PLXC.out.println("if (" + a.getNombre() + " == " + b.getNombre() + ") goto " + l + ";");
        PLXC.out.println(res.getNombre() + " = 0;");
        PLXC.out.println(l + ":");

        return res;
    }

    /**
     * Genera el CTD necesario para realizar la comparación distinto que entre dos objetos de tipo entero. CTD para la comparación distinto que de dos valores:
     *  result = 1
     *  if a != b goto l;
     *      result = 0;
     *  l:
     *
     * @param a Objeto entero
     * @param b Parámetro de tipo entero para la comparación.
     * @return Objeto dentro de la tabla de símbolos con el resultado de la operacion <a != b>
     */
    private static Objeto distintoQueDosEnteros(Objeto a, Objeto b) {
        Instancia res = new Instancia(TBool.getTBool());
        String l = PLXC.tablaSimbolos.getNewEtiq();

        PLXC.out.println(res.getNombre() + " = 1;");
        PLXC.out.println("if (" + a.getNombre() + " != " + b.getNombre() + ") goto " + l + ";");
        PLXC.out.println(res.getNombre() + " = 0;");
        PLXC.out.println(l + ":");


        return res;
    }


    private static Instancia genIfLessThan(Objeto a, Objeto b) {
        Instancia res = new Instancia(Objeto.newNombreObjeto(), TBool.getTBool(), TablaSimbolos.bloqueActual, false);
        String l = PLXC.tablaSimbolos.getNewEtiq();
        PLXC.out.println(res.getNombre() + " = 1;");
        PLXC.out.println("if (" + a.getNombre() + " < " + b.getNombre() + ") goto " + l + ";");
        PLXC.out.println(res.getNombre() + " = 0;");
        PLXC.out.println(l + ":");

        return res;
    }

    private static Instancia genIfLessOrEqual(Objeto a, Objeto b) {
        Instancia res = new Instancia(Objeto.newNombreObjeto(), TBool.getTBool(), TablaSimbolos.bloqueActual, false);
        String l = PLXC.tablaSimbolos.getNewEtiq();

        PLXC.out.println(res.getNombre() + " = 1;");
        PLXC.out.println("if (" + a.getNombre() + " < " + b.getNombre() + ") goto " + l + ";");
        PLXC.out.println("if (" + a.getNombre() + " == " + b.getNombre() + ") goto " + l + ";");
        PLXC.out.println(res.getNombre() + " = 0;");
        PLXC.out.println(l + ":");
        return res;
    }


}
