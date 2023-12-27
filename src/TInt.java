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
    public Objeto metodosInstancia(Objeto o, String m, Vector<Objeto> p) {
        if (!(o instanceof Instancia))  errorYPara("Error de tipo, el objeto no es una instancia", p);
        if (((Instancia) o ).getTipoInstancia() != T_INT)    errorYPara("Error de tipo, el objto sí es una instancia pero no es un entero", p);

        // LLegados hasta aquí, estamos seguros de que o es una instancia de tipo entero
        if (m.equals(INT_METHODS.ASIGNA.name())) {
            if (!(p.size() == 1 && sameType(p.firstElement())))
                errorYPara("La asignación solo opera con un argumento de tipo entero", p);
            if (!o.getMutable())
                errorYPara("La variable sobre la que se realizó la asignación no es mutable", new Vector<>(List.of(o, p.firstElement())));
            Objeto valor = p.firstElement();
            // Generar código de tres direcciones que ejecute la asignación
            /*
             ID = expr

             $ti = $tk
             */
            System.out.println(o.getNombre() + "=" + valor.getNombre() + ";");

            return o;
        }

        if (m.equals(INT_METHODS.SUMA.name())) {
            if (p.size() == 1)
                errorYPara("La suma para el tipo entero solo se contempla para dos valores", p);
            if (!sameType(p.firstElement()))
                errorYPara("Los parámetros para la suma no son del tipo correcto (se necesitan enteros): " + p.toString(), p);

            return sumaDosEnteros(o, p.firstElement());
        }

        if (m.equals(INT_METHODS.PRINT.name())) {
            if (p.size() != 0)
                errorYPara("La función print no necesita parámetros", p);

            System.out.println("print " + o.getNombre() + " ;");
            return null;
        }
        errorYPara("Operación no contemplada para el tipo entero. La operación en cuestión es [".concat(m).concat("]"), p);

        return null;
    }

    public static TInt getTInt() {
        return T_INT;
    }

    private static boolean sameType(Objeto o ) {
        return ((Instancia)o).getTipoInstancia() == T_INT
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

        // Obj contrndrá la etiqueta con el resultado de la suma dentro de la tabla de símbolos
        Objeto obj = new Instancia(Objeto.newNombreObjeto(), T_INT,TablaSimbolos.bloqueActual, false);
        /**/
        System.out.println(obj.getNombre() + "=" + o.getNombre() + "+" + p.getNombre() + ";");
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
        return null;
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
        return null;
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
        return null;
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
        return null;
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
        return null;
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
        return null;
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
        return null;
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
        return null;
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
        return null;
    }

}
