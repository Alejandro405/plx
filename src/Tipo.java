import java.util.Vector;

public abstract class Tipo extends Objeto{
    public Tipo(String nombre, Integer bloque, Boolean mutable) {
        super(nombre, bloque, mutable);
    }

    public abstract boolean isParseable(Tipo tipo) ;


    /**
     * Desencadena la ejecución de la funcion m sobre o con los argumentos contenidos en p. La implementación del método vendrá dada por el el tipo concreo de instancia, este objeto es el interfaz común para que cada instancia pueda ejecutar operaciones propias de su tipo
     *
     * @param o Objeto sobre el que aplicar el método
     * @param m String representativo del método a aplicar
     * @param p Vector de parámetos con los que aplicar el método
     * @return Objeto con el que acceder al valor de la operación. No el valor en sí, la variable CTD que lo almacena
     */
    public abstract Objeto metodosInstancia(Objeto o, String m, Vector<Objeto> p);
}
