import java.util.*;

/**
 * Tabla en la que se almacenran todos aquellos elementos del lenguaje que se puedan usar en tiempo de ejecución
 * La tabla es "optimista", en las operacinoes de manipulación del contenido, se asume que se dán las condiciones necesarias para ejecutar la operación
 */
public class TablaSimbolos {

    private Map<String, TreeMap<Integer, Objeto>> tabla;

    private Integer numEtiq;

    public static Integer bloqueActual = 0;

    private static int contBloque;

    public TablaSimbolos() {
        this.tabla = new HashMap<String, TreeMap<Integer, Objeto>>();
        this.numEtiq = 0;
    }

    public String getNewEtiq() {
        String res = "L" + numEtiq;
        numEtiq++;

        return res;
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
}
