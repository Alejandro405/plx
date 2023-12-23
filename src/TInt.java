import java.util.Vector;

/**
 * Clase que alberga el comportamiento y las propiedades del tipo entero para el lenguaje PLC
 */
public class TInt extends Tipo{

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

        if (m.equals("asigna")) {
            if (p.size() == 1 && checkType(p.firstElement()) && o.getMutable()) {
                Objeto valor = p.firstElement();
                // Generar código de tres direcciones que ejecute la asignación
                /*
                 ID = expr

                 $ti = $tk
                 */
                System.out.println(o.getNombre() + "=" + valor.getNombre() + ";");

                return o;
            }
        }

        if (m.equals("suma")) {
            return sumaDosEnteros(o, p);

        }
        errorYPara("Operación no contemplada para el tipo entero. La operación en cuestión es [".concat(m).concat("]"), p);

        return null;
    }

    public static TInt gettInt() {
        return T_INT;
    }

    private static boolean checkType(Objeto o ) {
        return ((Instancia)o).getTipoInstancia() == T_INT
                && o.getClass() == Instancia.class;
    }

    private static boolean checkType(Vector<Objeto> p ) {
        return p.stream().allMatch(TInt::checkType);
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
    private static Objeto sumaDosEnteros(Objeto o, Vector<Objeto> p) {
        if (p.size() != 2) errorYPara("[Error]\tLa suma para el tipo entero solo se contempla para dos valores", p);
        if (!checkType(p)) errorYPara("[Error]\tLos parámetros para la suma no son del tipo correcto (se necesitan enteros): " + p.toString(), p);

        // Obj contrndrá la etiqueta con el resultado de la suma dentro de la tabla de símbolos
        Objeto obj = new Instancia(Objeto.newNombreObjeto(), T_INT,TablaSimbolos.bloqueActual, false);
        /**/
        System.out.println(obj.getNombre() + "=" + o.getNombre() + "+" + p.firstElement().getNombre() + ";");
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
    private static Objeto restaDosEnteros(Objeto o, Vector<Objeto> p) {
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
    private static Objeto divideDosEnteros(Objeto o, Vector<Objeto> p) {
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
    private static Objeto multiplicaDosEnteros(Objeto o, Vector<Objeto> p) {
        return null;
    }


}
