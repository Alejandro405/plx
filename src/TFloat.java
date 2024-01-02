import java.util.List;
import java.util.Vector;

public class TFloat extends Tipo {

    public static enum FLOAT_METHODS {
        ASIGNA, UMENOS, SUMA, RESTA, MULT, DIV, MAYOR, MENOR, MAYOR_IGUAL, MENOR_IGUAL, IGUAL, DISTINTO, PRINT
    }

    private static final TFloat T_FLOAT = new TFloat();

    public TFloat() {
        super(String.valueOf(Tipos_PL.FLOAT), 0, false);
    }

    @Override
    public Objeto metodos(String metodo, Vector<Objeto> params) {
        errorYPara("No se puede llamar al método " + metodo + " sobre un tipo float, solo es posible operar sobre instancias", params);
        return null;
    }

    @Override
    public boolean isParseable(Tipo tipo) {
        return true;
    }

    @Override
    public Objeto metodosInstancia(Objeto o, String m, Vector<Objeto> p) {
        if (!(o instanceof Instancia)) errorYPara("Error de tipo, el objeto no es una instancia", p);
        if (((Instancia) o).getTipoInstancia() != T_FLOAT)
            errorYPara("Error de tipo, el objto sí es una instancia pero no es un entero", p);

        if (m.equals(FLOAT_METHODS.ASIGNA.name())) {
            if (!(p.size() == 1 && sameType(p.firstElement())))
                errorYPara("La asignación solo opera con un argumento de tipo entero", p);
            if (!o.getMutable())
                errorYPara("La variable sobre la que se realizó la asignación no es mutable", new Vector<>(List.of(o, p.firstElement())));
            Objeto valor = p.firstElement();
            PLXC.out.println(o.getNombre() + "=" + valor.getNombre() + ";");

            return o;
        }

        if (m.equals(FLOAT_METHODS.SUMA.name())) {
            checkBinaryOp(p, m);

            Instancia res = new Instancia(Objeto.newNombreObjeto(),T_FLOAT, TablaSimbolos.bloqueActual, false);
            PLXC.out.println(res.getNombre() + "=" + o.getNombre() + " +" + p.firstElement().getNombre() + ";");

            return res;
        }

        if (m.equals(FLOAT_METHODS.RESTA.name())) {
            checkBinaryOp(p, m);

            Instancia res = new Instancia(Objeto.newNombreObjeto(),T_FLOAT, TablaSimbolos.bloqueActual, false);
            PLXC.out.println(res.getNombre() + " = " + o.getNombre() + " -" + p.firstElement().getNombre() + ";");

            return res;
        }

        if (m.equals(FLOAT_METHODS.MULT.name())) {
            checkBinaryOp(p, m);

            Instancia res = new Instancia(Objeto.newNombreObjeto(),T_FLOAT, TablaSimbolos.bloqueActual, false);
            PLXC.out.println(res.getNombre() + " = " + o.getNombre() + " *" + p.firstElement().getNombre() + ";");

            return res;
        }

        if (m.equals(FLOAT_METHODS.DIV.name())) {
            checkBinaryOp(p, m);

            Instancia res = new Instancia(Objeto.newNombreObjeto(),T_FLOAT, TablaSimbolos.bloqueActual, false);
            PLXC.out.println(res.getNombre() + " = " + o.getNombre() + " /" + p.firstElement().getNombre() + ";");

            return res;
        }

        if (m.equals(FLOAT_METHODS.UMENOS.name())) {
            if (!p.isEmpty())
                errorYPara("La operación " + m + " no opera con argumentos", p);

            Instancia res = new Instancia(Objeto.newNombreObjeto(),T_FLOAT, TablaSimbolos.bloqueActual, false);
            PLXC.out.println(res.getNombre() + " = 0 -" + o.getNombre() + ";");

            return res;
        }

        if (m.equals(FLOAT_METHODS.MAYOR.name())) {
            checkBinaryOp(p, m);
            return mayorQueDosReales(o, p.firstElement());
        }

        if (m.equals(FLOAT_METHODS.MENOR.name())) {
            checkBinaryOp(p, m);
            return menorQueDosReales(o, p.firstElement());
        }

        if (m.equals(FLOAT_METHODS.MAYOR_IGUAL.name())) {
            checkBinaryOp(p, m);
            return mayorIgualQueDosReales(o, p.firstElement());
        }

        if (m.equals(FLOAT_METHODS.MENOR_IGUAL.name())) {
            checkBinaryOp(p, m);
            return menorIgualQueDosReales(o, p.firstElement());
        }

        if (m.equals(FLOAT_METHODS.IGUAL.name())) {
            checkBinaryOp(p, m);
            return igualQueDosReales(o, p.firstElement());
        }

        if (m.equals(FLOAT_METHODS.DISTINTO.name())) {
            checkBinaryOp(p, m);
            return distintoQueDosReales(o, p.firstElement());
        }

        if (m.equals(FLOAT_METHODS.PRINT.name())) {
            if (!p.isEmpty())
                errorYPara("La operación " + m + " no opera con argumentos", p);

            PLXC.out.println("print " + o.getNombre() + " ;");

            return null;
        }

        errorYPara("El método " + m + " no está definido para el tipo float", p);

        return null;
    }

    private static Objeto distintoQueDosReales(Objeto a, Objeto b) {
        Instancia res = new Instancia(TBool.getTBool());
        String l = PLXC.tablaSimbolos.getNewEtiq();

        PLXC.out.println(res.getNombre() + " = 1;");
        PLXC.out.println("if (" + a.getNombre() + " != " + b.getNombre() + ") goto " + l + ";");
        PLXC.out.println(res.getNombre() + " = 0;");
        PLXC.out.println(l + ":");


        return res;
    }

    private static Objeto igualQueDosReales(Objeto a, Objeto b) {
        Instancia res = new Instancia(Objeto.newNombreObjeto(), TBool.getTBool(), TablaSimbolos.bloqueActual, false);
        String l = PLXC.tablaSimbolos.getNewEtiq();

        PLXC.out.println(res.getNombre() + " = 1;");
        PLXC.out.println("if (" + a.getNombre() + " == " + b.getNombre() + ") goto " + l + ";");
        PLXC.out.println(res.getNombre() + " = 0;");
        PLXC.out.println(l + ":");

        return res;
    }

    private static Objeto menorIgualQueDosReales(Objeto a, Objeto b) {
        return genIfLessOrEqual(a, b);
    }

    private Objeto mayorIgualQueDosReales(Objeto a, Objeto b) {
        return genIfLessOrEqual(b, a);
    }

    private static Objeto menorQueDosReales(Objeto a, Objeto b) {
        return genIfLessThan(a, b);
    }

    private static Objeto mayorQueDosReales(Objeto a, Objeto b) {
        return genIfLessThan(b, a);
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

    private static Objeto genIfLessOrEqual(Objeto a, Objeto b) {
        Instancia res = new Instancia(Objeto.newNombreObjeto(), TBool.getTBool(), TablaSimbolos.bloqueActual, false);
        String l = PLXC.tablaSimbolos.getNewEtiq();

        PLXC.out.println(res.getNombre() + " = 1;");
        PLXC.out.println("if (" + a.getNombre() + " < " + b.getNombre() + ") goto " + l + ";");
        PLXC.out.println("if (" + a.getNombre() + " == " + b.getNombre() + ") goto " + l + ";");
        PLXC.out.println(res.getNombre() + " = 0;");
        PLXC.out.println(l + ":");
        return res;
    }

    @Override
    public Tipo getTipo() {
        return T_FLOAT;
    }

    @Override
    public Instancia cast(Tipo tarTipo, Instancia valor) {
        if (tarTipo == TInt.getTInt() || tarTipo == TChar.getTChar()) {
            Instancia res = new Instancia(tarTipo);
            PLXC.out.println(res.getNombre() + " = (int) " + valor.getNombre() + ";");
            return res;
        } else {
            errorYPara("[ERROR]\tNo se contempla el casting de float a " + tarTipo.getTipo(), new Vector<>(List.of(valor)));
            return null;
        }
    }

    private void checkBinaryOp(Vector<Objeto> p, String m) {
        if (p.size() != 1 )
            errorYPara("La operación " + m + " solo opera con un argumento de tipo entero", p);

        if (!sameType(p.firstElement()))
            errorYPara("Los argumentos para la " + m + "no son del tipo correcto (se necesitan tipos numéricos)", p);
    }

    public static TFloat getTFloat() {
        return T_FLOAT;
    }

    private static boolean sameType(Objeto o) {
        return (((Instancia) o).getTipoInstancia() == TInt.getTInt() || ((Instancia) o).getTipoInstancia() == T_FLOAT)
                && o.getClass() == Instancia.class;
    }
}
