import java.util.*;

/**
 * Tabla en la que se almacenran todos aquellos elementos del lenguaje que se puedan usar en tiempo de ejecución
 * La tabla es "optimista", en las operacinoes de manipulación del contenido, se asume que se dán las condiciones necesarias para ejecutar la operación
 */
public class TablaSimbolos {

    private Map<String, TreeMap<Integer, Objeto>> tabla;

    private Integer numEtiq;
    private Integer etiqSwitch;


    public static Integer bloqueActual = 0;

    private static int contBloque;

    public static Set<Funcion> funciones = new TreeSet<>();

    public TablaSimbolos() {
        this.tabla = new HashMap<String, TreeMap<Integer, Objeto>>();
        this.numEtiq = 0;
        this.etiqSwitch = 0;
    }

    public String getNewEtiq() {
        String res = "L" + numEtiq;
        numEtiq++;

        return res;
    }

    public String getNewEtiqSwitch() {
        String res = "L" + etiqSwitch;
        etiqSwitch++;

        return res;
    }

    public String getCurrentEtiqSwitch() {
        return "L" + etiqSwitch;
    }

    public static Integer newBloque() {
        int aux = bloqueActual;
        contBloque++;
        bloqueActual = contBloque;

        return aux;
    }


    /**
     * Consulta la tabla en unsca de un objeto por su identificador
     * @param nombre Nombre de la variable a consultar
     * @return true sii nombre se encuentra en la tabla, independientemente del bloque
     */
    public boolean contains(String nombre) {
        return false;
    }

    /**
     * Consulta la tabla en unsca de un objeto por su identificador y el bloque al que pertenece
     * @param nombre Nombre de la variable a consultar
     * @param bloque El identificador de bloque sobre el que se quiere consultar
     * @return true sii nombre se encuentra en la tabla para ese bloque
     */
    public boolean contains(String nombre, Integer bloque) {
        return false;
    }

    /**
     * Devuelve el identificador de bloque contenido dentro de la tabla
     * @return El identificador de bloque máximo
     */
    public int gmaxBlock() {
        int res = Integer.MIN_VALUE;
        for (TreeMap<Integer, Objeto> x : this.tabla.values()) {
            if (x.lastKey() > res)
                res = x.firstKey();
        }
        return res;
    }

    /**
     *
     * @param nombre Identificador de objeto (variable/tipo/...)
     * @return el objeto asociado a dicho identificador. En caso de encortrarse en más de un nivel, se devuelve el de máximo nivel
     */
    public Objeto getObj(String nombre) {
        return this.getObj(nombre, this.gmaxBlock());
    }

    /**
     *
     * @param nombre Identificador del objeto
     * @param bloque Numero de bloque al que pertenece el objeto
     * @return el objeto asociado a dicho identificador dentro de dicho bloque
     */
    public Objeto getObj(String nombre, Integer bloque) {
        Objeto res = this.tabla.get(nombre).get(bloque);
        return res;
    }

    /**
     * Busca el objeto de la clase funcion con el tipo de retorno e id iguales a los pasados como parámetros.
     * @param tipoRet El tipo de la función
     * @param id El identificador de la función
     * @return La funcion a consultar
     */
    public Funcion getFunction(Tipo tipoRet, String id) {
        // Las funcinoes son objetos cuyo atributo siempre es "FUNCTION"
        Objeto aux = this.tabla.get(id).get(0);
        if (!(aux instanceof Funcion)) {
            Objeto.errorYPara("[ERROR]\tLa funcion <"+ id +"> no ha sido instanciada como instancia de la clase Funcion, sino como <" + aux.getClass().getName() + ">", new Vector<>(List.of(aux)));
            return null;
        } else {
            return (Funcion) aux;
        }
    }

    /**
     * Añade una nueva entrada a la tabla
     *
     * @param o Objeto a introducir en la tabla
     */
    public void putObj(Objeto o) {
        String nombre = o.getNombre();
        Integer bloque = o.getBloque();

        if (this.tabla.containsKey(nombre)) {
            TreeMap<Integer, Objeto> aux = this.tabla.get(nombre);
            aux.put(bloque, o);
        } else {
            TreeMap<Integer, Objeto> aux = new TreeMap<Integer, Objeto>();
            aux.put(bloque, o);
            this.tabla.put(nombre, aux);

        }
    }

    public void putFunction(Funcion f) {
        String id = f.getId();
        int bloque = f.getBloque();
        TreeMap<Integer, Objeto> valor = new TreeMap<>();

        if (!tabla.containsKey(id)){
            valor.put(bloque, f);
            this.tabla.put(id, valor);
        } else {
            Objeto aux = tabla.get(id).get(0);
            if (aux instanceof Funcion && ((Funcion) aux).getTipoRetorno() == f.getTipoRetorno()) {
                Objeto.errorYPara("[ERROR]\tYa existe una funcion en la tabla de símbolos con ese identificador y ese tipo de retorno", new Vector<>(List.of((Funcion)aux)));
            } else {
                valor.put(bloque, f);
                tabla.put(id, valor);
            }
        }


    }

    /**
     * Pra cada id de la tabla, eliminara el objeto asociado al bloque y a dicho identificador
     * @param bloque Bloque del que eliminar las variables/objetos
     */
    public void delBlock(Integer bloque) {
        // Eliminar el objeto de la tabla, para cualquier identificador, que pertenezca al bloque. En caso de que algún identificador no tenga un objeto asociado en el bloque, se elimina dicho identificador de la tabla
        for (String id : this.tabla.keySet()) {
            TreeMap<Integer, Objeto> aux = this.tabla.get(id);
            aux.remove(bloque);
            if (aux.isEmpty())
                this.tabla.remove(id);
        }
    }

    /**
     * Para cada id de la tabla, eliminara el objeto asociado al bloque actual y a dicho identificador
     */
    public void delBlock() {
        this.delBlock(bloqueActual);
    }

    public boolean containsFunction(Tipo tipo, String id) {
        boolean res = false;

        if (tabla.containsKey(id)) {
            Objeto aux = tabla.get(id).get(0);
            if (aux instanceof Funcion) {
                res = true;
            }
        }
        return false;
    }
}
