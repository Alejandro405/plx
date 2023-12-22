import java.util.*;

public class TablaSimbolos {

    private Map<String, TreeMap<Integer, Objeto>> tabla;

    private Integer numEtiq;

    public static Integer bloqueActual;

    private static int contBloque;

    public TablaSimbolos() {
        this.tabla = new HashMap<String, TreeMap<Integer, Objeto>>();
    }

    public String getNewEtiq() {
        String res = "L" + numEtiq;
        numEtiq++;

        return res;
    }

    public Integer newBloque() {
        int aux = bloqueActual;
        contBloque++;
        bloqueActual = contBloque;

        return aux;
    }


    /**
     *
     * @param nombre Nombre de la variable a consultar
     * @return true sii nombre se encuentra en la tabla, independientemente del bloque
     */
    public boolean contains(String nombre) {
        return false;
    }

    /**
     *
     * @param nombre Nombre de la variable a consultar
     * @param bloque El identificador de bloque sobre el que se quiere consultar
     * @return true sii nombre se encuentra en la tabla para ese bloque
     */
    public boolean contains(String nombre, Integer bloque) {
        return false;
    }

    /**
     *
     * @return El identificador de bloque máximo
     */
    public int gmaxBlock() {
        return 0;
    }

    /**
     *
     * @param nombre
     * @return el objeto asociado a dicho identificador. En caso de encortrarse en más de un nivel, se devuelve el de máximo nivel
     */
    public Objeto getObj(String nombre) {
        return null;
    }

    /**
     *
     * @param nombre
     * @param bloque
     * @return el objeto asociado a dicho identificador dentro de dicho bloque
     */
    public Objeto getObj(String nombre, Integer bloque) {
        return null;
    }

    /**
     * Añade una nueva entrada a la tabla
     *
     * @param o Objeto a introducir en la tabla
     */
    public void putObj(Objeto o) {

    }

    /**
     * Pra cada id de la tabla, eliminara el objeto asociado al bloque y a dicho identificador
     * @param bloque Bloque del que eliminar las variables/objetos
     */
    public void delBlock(Integer bloque) {

    }
}
