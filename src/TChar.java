import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * Clase que representa el tipo de dato char en el lenguaje de programación.
 * El tipo char no será más que un entero, de forma que aunque el fuente aparaezcan como 'X',
 * El compilador lo tratará como un entero: print ('z' - 'a'); -> $t0 = 122 - 97; print $t0;
 */
public class TChar extends Tipo{

    public static enum CHAR_METHODS {
        SUMA, RESTA, DIV, ASIGNA, PRINT
    }
    private static final TChar T_CHAR = new TChar();

    public TChar() {
        super(String.valueOf(Tipos_PL.CHAR), 0, false);
    }

    private static void checkBinaryOp(String op, Vector<Objeto> p) {
        if (p.size() != 1)
            errorYPara("La " + op + " para el tipo entero solo se contempla para dos valores", p);
        if (!sameType(p.firstElement()))
            errorYPara("Los parámetros para la " + op + " no son del tipo correcto (se necesitan tipos numéricos): " + p.toString(), p);
    }

    private static boolean sameType(Objeto o ) {
        return (((Instancia)o).getTipoInstancia() == T_CHAR || ((Instancia)o).getTipoInstancia() == TInt.getTInt())
                && o.getClass() == Instancia.class;
    }

    @Override
    public Objeto metodos(String metodo, Vector<Objeto> params) {
        errorYPara("[ERROR]\tNo se contempla [ " + metodo +"] para el tipo char, solo instancias de tipo char.", params);
        return null;
    }

    @Override
    public boolean isParseable(Tipo tipo) {
        return tipo.getTipo() == TInt.getTInt() || tipo.getTipo() == T_CHAR;
    }

    @Override
    public Objeto metodosInstancia(Objeto o, String m, Vector<Objeto> p) {
        if (!(o instanceof Instancia))  errorYPara("[ERROR]\tNo se contempla [ " + m +"] para el tipo char, solo instancias de tipo char.", p);
        if (((Instancia) o).getTipoInstancia() != T_CHAR) errorYPara("[ERROR]\tEl objeto proporcionado no es una instancia", new Vector<>(List.of(o)));

        if (m.equals(CHAR_METHODS.ASIGNA.name())) {
            if (p.size() != 1 || ((Instancia) p.firstElement()).getTipoInstancia() != T_CHAR)
                errorYPara("[ERROR]\tLa asignación entre chars solo se contempla para un valor", p);
            if (!o.getMutable())
                errorYPara("[ERROR]\tEl objeto proporcionado para la asignación entre chars no es mutable", new Vector<>(List.of(o)));

            PLXC.out.println(o.getNombre() + " = " + p.firstElement().getNombre() + ";");

            return o;
        }

        /*
         * La operacion suma de chars puede tener dos significados:
         * - Suma de dos chars donde al menos uno es entero: 0 + 'b' -> $t0 = 0 + 98;
         * - En caso de que ambos sean chars, se concatenan y se devuelve un string
         * : 'a' + 'b' -> $t0 = 97 + 98;
         */
        if (m.equals(CHAR_METHODS.SUMA.name())) {
            Objeto res = null;
            if (p.size() == 1) {
                if (((Instancia) p.firstElement()).getTipoInstancia() == T_CHAR)
                    res = concatDosChars(o, p.get(0));
                else if (((Instancia) p.firstElement()).getTipoInstancia() == TInt.getTInt())
                    res = sumaDosChars(o, p.get(0));
                else
                    errorYPara("La [" + m + "] de char solo es posible entre dos char (concatenación) o entre char y entero (suma)", p);
            }

            return res;
        }

        if (m.equals(CHAR_METHODS.RESTA.name())) {
            checkBinaryOp("resta", p);

            return restaDosChars(o, p.get(0));
        }

        if (m.equals(CHAR_METHODS.DIV.name())) {
            checkBinaryOp("división", p);

            Instancia res = new Instancia(TInt.getTInt());

            PLXC.out.println(res.getNombre() + " = " + o.getNombre() + " / " + p.firstElement().getNombre() + ";");

            return res;
        }

        if (m.equals(CHAR_METHODS.PRINT.name())) {
            if (!p.isEmpty())
                errorYPara("La " + m + " para el tipo char solo se contempla para un valor", p);

            PLXC.out.println("printc " + o.getNombre() + ";");
            return null;
        }


        return null;
    }

    /**
     * Genera el código de tres direccones para restar dos chars. Código de tres direcciones para la resta de dos chars.
     * 'Z' - 'A':
     *     $t0 = 90 - 65;
     *
     * @param o Cadena 1
     * @param objeto Cadena 2
     * @return Variable en la que se referencia el resultado (instancia de tipo entero) de la resta en el lenguaje de tres direcciones.
     */
    private Objeto restaDosChars(Objeto o, Objeto objeto) {
        Instancia res = new Instancia(TInt.getTInt());

        PLXC.out.println(res.getNombre() + " = " + o.getNombre() + " - " + objeto.getNombre() + ";" );

        return res;
    }

    @Override
    public Tipo getTipo() {
        return getTChar();
    }

    @Override
    public Instancia cast(Tipo tarTipo, Instancia valor) {
        if (tarTipo == TInt.getTInt()) {
            return new Instancia(valor.getNombre(), tarTipo, TablaSimbolos.bloqueActual, false);
        } else if (tarTipo == TFloat.getTFloat()) {
            Instancia res = new Instancia(tarTipo);
            PLXC.out.println(res.getNombre() + " = (float) " + valor.getNombre() + ";");
            return res;
        } else {
            errorYPara("[ERROR]\tNo se contempla el casting de char a " + tarTipo.getTipo(), new Vector<>(List.of(valor)));
            return null;
        }
    }

    public static TChar getTChar(){
        return T_CHAR;
    }

    /**
     * Genera el código de tres direccones para concatenar dos chars. Código de tres direcciones para la concatenación de dos chars.
     * 'z' + 'a':
     *     $t0 = 122 + 97;
     *
     * @param c1 Char 1
     * @param c2 Char 2
     * @return Variable en la que se referencia el resultado de la suma de chars (Instancia de tipo Entero) en el lenguaje de tres direcciones.
     */
    private static Objeto sumaDosChars(Objeto c1, Objeto c2) {
        Objeto res = new Instancia(TInt.getTInt());

        PLXC.out.println(res.getNombre() + " = " + c1.getNombre() + " + " + c2.getNombre() + ";" );

        return  res;
    }

    /** Genera el código de tres direccones para concatenar dos chars. Código de tres direcciones para la concatenación de dos chars.
     *
     * @param c1 Char 1
     * @param c2 Char 2
     * @return Variable en la que se referencia el resultado de la concatenaciṕn de dos chars (Instancia de tipo String) en el lenguaje de tres direcciones.
     */
    private static StringInstancia concatDosChars(Objeto c1, Objeto c2) {
        StringInstancia res = new StringInstancia(2);

        PLXC.out.println(res.getNombre() + "[0] = " + c1.getNombre() + ";");
        PLXC.out.println(res.getNombre() + "[1] = " + c2.getNombre() + ";");

        return  res;
    }

    public static void main(String[] args) {
        String a = "\\u0061";
        String b = "\\u0043";
        String c = "\\u25b2";

        var l = List.of(a, b, c);

        Iterator<String> itr = l.iterator();
        while (itr.hasNext()) {
            System.out.println(itr.next());
        }

/*
        print('\u0043');
        print('\u00f1');
        print('\u25b2');*/
    }

}
