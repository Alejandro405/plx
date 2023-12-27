import java.util.Objects;
import java.util.Vector;

public abstract class Objeto implements Comparable<Objeto>{

    private String nombre;
    private Integer bloque;
    private Boolean mutable;

    private static Integer numObj = 0;

    public Objeto(String nombre, Integer bloque, Boolean mutable) {
        this.nombre = nombre;
        this.bloque = bloque;
        this.mutable = mutable;
    }

    /*
    * Formato del nombre
    */
    public static String newNombreObjeto() {
        String res = "$T".concat(Integer.toString(numObj));
        numObj++;

        return res;
    }

    public abstract Objeto metodos(String metodo, Vector<Objeto> params);

    public String getNombreBloque() {
        return this.bloque == 0 ? nombre: nombre + "$" + bloque;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getBloque() {
        return bloque;
    }

    public Boolean getMutable() {
        return mutable;
    }

    /**
     * Corta la ejecución del compilador por un error de tipos
     *
     * @param msg Mensaje de error
     * @param params Parámetos soble los que se oriigina el error
     */
    public static void errorYPara(String msg, Vector<Objeto> params) {
        throw new RuntimeException(msg.concat(". Parámetros: " + params.toString()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;


        Objeto objeto = (Objeto) o;


        return Objects.equals(nombre, objeto.nombre) && Objects.equals(bloque, objeto.bloque);
    }


    @Override
    public int compareTo(Objeto o) {
        int res = this.nombre.compareTo(o.nombre);

        return res == 0 ? (this.bloque - o.bloque) : res;
    }
}
