import java.util.Vector;

public class Instancia extends Objeto{
    private final Tipo tipoInstancia;


    public Instancia(String nombre, Tipo tipo, Integer bloque, Boolean mutable) {
        super(nombre, bloque, mutable);
        this.tipoInstancia = tipo;
    }

    @Override
    public Objeto metodos(String metodo, Vector<Objeto> params) {
        return tipoInstancia.metodosInstancia(this, metodo, params);
    }

    public Tipo getTipoInstancia() {
        return tipoInstancia;
    }
}
