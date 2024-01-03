import java.util.Vector;

/**
 * Clase que representa el tipo array. En este caso el no existe un único tipo de array.
 * Segun las dimensiones, el tipo de los elementos, se creará un tipo array u otro.
 *
 * Es por eso que en este caso desaparece el concepto de singleton. Y se considera que una instancia es de tipo array,
 * si posee una instancia de esta clase. Aunque exista una constante que representa el tipo array primigenio.
 */
public class TArray extends Tipo{

    public static enum ARRAY_METHODS {
        GET,
        SET,
        PRINT,
        ASIGNA
    }

    private static final int DEF_TAM = 10;
    
    private final int tam;
    
    private final Tipo tipo;

    private String id;
    
    public TArray(Tipo tipo) {
        super(tipo.getNombre(), 0, false);
        this.tam = DEF_TAM;
        this.tipo = tipo;
    }
    
    public Objeto getElem(Objeto array, Instancia i){
        checkIndex(i);

        Instancia res = new Instancia(this.tipo);
        PLXC.out.println(res.getNombre() + " = " + this.id + "[" + i + "];");

        return res;
    }

    private void checkIndex(Instancia i) {
        String l = PLXC.tablaSimbolos.getNewEtiq();

        PLXC.out.println("check_index_" + l + ":");
        PLXC.out.println("if (" + i.getNombre() + " < 0) goto error_index_" + l + ";");
        PLXC.out.println("if (" + tam + "< " + i.getNombre() + ") goto error_index_" + l + ";");
        PLXC.out.println("if (" + i.getNombre() + " == " + tam + ") goto error_index_" + l + ";");
        PLXC.out.println("goto end_check_index_" + l + ";");
        PLXC.out.println("error_index_" + l + ":");
        PLXC.out.println("error;\nhalt;");
        PLXC.out.println("end_check_index_" + l + ":");
    }

    public void setElem(Objeto array, Instancia i, Objeto o) {
        checkIndex(i);

        PLXC.out.println(this.id + "[" + i + "] = " + o.getNombre() + ";");
    }
    
    public int getTam() {
        return tam;
    }

    @Override
    public boolean isParseable(Tipo tipo) {
        return false;
    }

    @Override
    public Objeto metodosInstancia(Objeto o, String m, Vector<Objeto> p) {

        if (m.equals(ARRAY_METHODS.ASIGNA.name())) {

        }

        if (m.equals(ARRAY_METHODS.GET.name())) {
            if (!(p.firstElement() instanceof Instancia && ((Instancia) p.firstElement()).getTipoInstancia() == TInt.getTInt()))
                Objeto.errorYPara("[ERROR]\tEl índice de acceso a un array debe ser una instancia de tipo entero", new Vector<>());
            Instancia aux = (Instancia) p.firstElement();
            return getElem(o, aux);
        }

        if (m.equals(ARRAY_METHODS.SET.name())) {
            if (p.size() != 2)
                Objeto.errorYPara("[ERROR]\tEl método set de un array debe recibir dos parámetros (indice y objeto)", new Vector<>());

            if (!(p.firstElement() instanceof Instancia && ((Instancia) p.firstElement()).getTipoInstancia() == TInt.getTInt()))
                Objeto.errorYPara("[ERROR]\tEl índice de acceso a un array debe ser una instancia de tipo entero", new Vector<>());


            Instancia i = (Instancia) p.firstElement();

            setElem(o, i, (Instancia) p.lastElement());

            return o;
        }

        if (m.equals(ARRAY_METHODS.PRINT.name())) {

            if (!(o instanceof Instancia && ((Instancia) o).getTipoInstancia().getClass() == this.getClass()))
                Objeto.errorYPara("[ERROR]\tEl objeto a imprimir debe ser una instancia de tipo array", new Vector<>());

            assert o instanceof Instancia;
            printArray((Instancia) o);

            return o;
        }

        errorYPara("[ERROR]\tNo se ha encontrado el método " + m + " para el tipo array", new Vector<>());
        return null;
    }

    public Tipo getTipo() {
        return tipo;
    }

    @Override
    public Instancia cast(Tipo tarTipo, Instancia valor) {
        Objeto.errorYPara("[ERROR]\tNo se puede castear un array", new Vector<>());
        return null;
    }

    @Override
    public Objeto metodos(String metodo, Vector<Objeto> params) {
        Objeto.errorYPara("[ERROR]\tNo se contemplan métodos para el tipo array", new Vector<>());
        return null;
    }

    private void printArray(Instancia array) {
        Objeto iter = new Instancia(array.getTipoInstancia());
        for (int i = 0; i < tam; i++) {
            PLXC.out.println(iter.getNombre() + " = " + array.getNombre() + "[" + i + "] ;");
            PLXC.out.println("print(" + iter.getNombre() + ");");
        }
    }



    @Override
    public String toString() {
        return "TArray{" +
                "tam=" + tam +
                ", tipo=" + tipo +
                '}';
    }
}
