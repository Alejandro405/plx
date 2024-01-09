import java.util.Objects;
import java.util.Vector;

public class Instancia extends Objeto{
    private final Tipo tipoInstancia;


    public Instancia(String nombre, Tipo tipo, Integer bloque, Boolean mutable) {
        super(nombre, bloque, mutable);
        this.tipoInstancia = tipo;
    }

    public Instancia(Tipo tipoInstancia) {
        super(Objeto.newNombreObjeto(), TablaSimbolos.bloqueActual, false);
        this.tipoInstancia = tipoInstancia;
    }

    public Instancia(String idParam, Tipo tipo, Integer bloque) {
        super(idParam, bloque, false);
        this.tipoInstancia = tipo;
    }

    @Override
    public Objeto metodos(String metodo, Vector<Objeto> params) {
        return tipoInstancia.metodosInstancia(this, metodo, params);
    }

    public boolean sameType(Instancia i1, Instancia i2){
        return i1.getTipoInstancia() == i2.getTipoInstancia();
    }

    public Tipo getTipoInstancia() {
        return tipoInstancia;
    }

    public static Instancia getIntConstant(Integer valor){
        return new Instancia(valor.toString(), TInt.getTInt(), 0, false);
    }

    public static Instancia getBoolConstant(Boolean valor){
        return new Instancia(valor.toString(), TBool.getTBool(), 0, false);
    }

    public static Instancia getCharConstant(Character valor){
        return new Instancia(valor.toString(), TChar.getTChar(), 0, false);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Instancia instancia = (Instancia) o;
        return Objects.equals(tipoInstancia, instancia.tipoInstancia)
                    && super.equals(instancia);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tipoInstancia, super.hashCode());
    }

    @Override
    public String toString() {
        return "Instancia{" +
                "tipoInstancia=" + tipoInstancia +
                '}';
    }
}
