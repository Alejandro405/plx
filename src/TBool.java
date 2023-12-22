import java.util.Vector;

public class TBool extends Tipo{
    public TBool(String nombre, Integer bloque, Boolean mutable) {
        super(String.valueOf(Tipos_PL.BOOL), 0, false);
    }

    @Override
    public Objeto metodos(String metodo, Vector<Objeto> params) {
        return null;
    }

    @Override
    public Objeto metodosInstancia(Objeto o, String m, Vector<Objeto> p) {
        return null;
    }
}
