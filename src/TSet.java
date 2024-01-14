import java.util.List;
import java.util.Vector;

/**
 * CTD no tiene recursos para delegar la implementaón de conjuntos
 * Códigos como estos son perfectamente válidos:
 * x[0] = 0;
 * x[1] = 1;
 * x[2] = 2;
 *
 * a = x[0];
 * write a;
 * a = x[1];
 * write a;
 * a = x[2];
 * write a;
 * TODO: implementar métodos privados para las operaciones con el tipo conjunto
 */
public class TSet extends Tipo{

    public static enum SET_METHODS {
        ASIGNA, PRINT, LENGTH, UNION, INTERS, DIFF, ADD
    }

    private static final TSet T_SET = new TSet("SET", 0, false);

    public TSet(String nombre, Integer bloque, Boolean mutable) {
        super(nombre, bloque, mutable);
    }

    @Override
    public Objeto metodos(String metodo, Vector<Objeto> params) {
        return null;
    }

    @Override
    public boolean isParseable(Tipo tipo) {
        return tipo == T_SET || tipo instanceof TArray || tipo == TString.getInstance();
    }

    @Override
    public boolean isIterable() {
        return true;
    }

    public static TSet getTSet() {
        return T_SET;
    }

    @Override
    public Objeto metodosInstancia(Objeto o, String m, Vector<Objeto> p) {
        if (!(o instanceof SetInstance)) {
            errorYPara("[ERROR]\tNo se puede llamar al metodo " + m + " en un objeto de tipo SET si este no es una instancia", new Vector<>());
            return null;
        }

        SetInstance set = (SetInstance) o;

        if (m.equals(SET_METHODS.ASIGNA.name())) {
            if (p.size() != 1) {
                errorYPara("[ERROR]\tEl metodo " + m + " recibe un parametro", new Vector<>());
                return null;
            }

            if (p.firstElement().getClass() != SetInstance.class) {
                errorYPara("[ERROR]\tEl metodo " + m + " recibe un SetInstance", new Vector<>(List.of(p.firstElement())));
            }

            return asigna(set, (SetInstance) p.firstElement());
        }

        if (m.equals(SET_METHODS.PRINT.name())) {
            if (!p.isEmpty()) {
                errorYPara("[ERROR]\tEl metodo " + m + " no debe recir parametros", new Vector<>());
            }

            print(set);

            return set;
        }

        if (m.equals(SET_METHODS.UNION.name())) {
            SetInstance b = ensureSetOper(m, p, set);
            if (b == null) return null;


            return union(set, b);
        }

        if (m.equals(SET_METHODS.INTERS.name())) {
            SetInstance b = ensureSetOper(m, p, set);

        }

        if (m.equals(SET_METHODS.DIFF.name())) {
            SetInstance b = ensureSetOper(m, p, set);

        }

        if (m.equals(SET_METHODS.ADD.name())) {
            if (p.size() != 1) {
                errorYPara("[ERROR]\tSolo se puede añadir un elemento en la operacion ADD", p);
            }

            if (!(p.firstElement() instanceof Instancia) && ((Instancia) p.firstElement()).getTipoInstancia() != set.getElemsType()) {
                errorYPara("[ERROR]\tEl tipo del elemento a añadir no es el mismo que el del conjunto", p);
            }

            Instancia elem = (Instancia) p.firstElement();
            addElem(set, elem);
            return set;
        }

        if (m.equals(SET_METHODS.LENGTH.name())) {
            Instancia res = new Instancia(TInt.getTInt());
            PLXC.out.println(res.getNombre() + " = " + set.getTam() + ";");

            return res;
        }

        errorYPara("[ERROR]\tNo se puede llamar al metodo " + m + " en un objeto de tipo SET", new Vector<>());
        return null;
    }

    private static void addElem(SetInstance set, Instancia elem) {
        set.addElem(elem);
    }

    private static SetInstance ensureSetOper(String m, Vector<Objeto> p, SetInstance set) {
        if (p.size() != 1 && p.firstElement().getClass() != SetInstance.class) {
            errorYPara("[ERROR]\tEl metodo " + m + " recibe un parametro que ha de ser una StringInstance", new Vector<>());
            return null;
        }

        SetInstance b = (SetInstance) p.firstElement();

        if (!b.getElemsType().equals(set.getElemsType())) {
            errorYPara("[ERROR]\tEl metodo " + m + " requiere de dos SetInstance de elementos de un mismo tipo", new Vector<>(List.of(set, b)));
            return null;
        }
        return b;
    }


    /**
     * Genera el código de tres direcciones para la operación de unión de conjuntos. Filtra elementos repetidos
     * @param A Conjunto A
     * @param B Conjunto B
     * @return SetInstance en la que se encuentran los elementos de la operación entre A y B. (No altera el conjunto A)
     */
    private SetInstance union(SetInstance A, SetInstance B) {
        // En caso de que se quiera modificar A solo se tendría que cambiar res por A

        SetInstance res = new SetInstance(A, TablaSimbolos.bloqueActual, true);

        List<Instancia> aElems = A.getElems();
        List<Instancia> bElems = B.getElems();
        for (Instancia x: bElems) {
            if(!B.contains(x)) {
                res.addElem(x);
            }
        }

        return res;
    }

    /**
     * Genera el código de tres direcciones para printear un conjunto, como si de un array se tratase.
     * @param set Conjunto a mostrar
     */
    private void print(SetInstance set) {
        for (Instancia x : set.getElems()) {
            x.metodos("PRINT", new Vector<>());
        }
    }

    /**
     * Genera el código de tres direcciones para la operación de asignación de conjuntos.
     * @param origen Conjunto sobre con los datos
     * @param destino Conjunto que recibe la asignación
     * @return SetInstance en la que se encuentran los elementos de la operación entre A y B.
     */
    private static SetInstance asigna(SetInstance destino, SetInstance origen) {
        destino.setTam(origen.getTam());
        destino.setElemsType(origen.getElemsType());
        destino.setConstant(false);
        destino.setElems(List.copyOf(origen.getElems()));

        return destino;
    }

    private boolean containsElem(SetInstance set, Instancia elem) {
        if (set.getElemsType() != elem.getTipoInstancia())
            errorYPara("[ERROR]\tUn elemento no puede estar contenido en  un set, si no es del mismo tipo que el resto de elementos del conjunto", new Vector<>(List.of(set, elem)));

        return set.contains(elem);
    }

    @Override
    public Tipo getTipo() {
        return T_SET;
    }

    @Override
    public Instancia cast(Tipo tarTipo, Instancia valor) {
        Instancia res = null;
        if (this.isParseable(tarTipo)){
            res = new Instancia(tarTipo);
            res.metodos("ASIGNA", new Vector<>(List.of(valor)));
        }
        return res;
    }
}
