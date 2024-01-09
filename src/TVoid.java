import java.util.Vector;

public class TVoid extends Tipo{

    private static final TVoid T_VOID = new TVoid("__T__VOID__", 0, false);
    private TVoid(String nombre, Integer bloque, Boolean mutable) {
        super(nombre, bloque, mutable);
    }

    @Override
    public Objeto metodos(String metodo, Vector<Objeto> params) {
        errorYPara("[ERROR]\tNo se puede llamar al método " + metodo + " de un tipo void.", params);
        return null;
    }

    @Override
    public boolean isParseable(Tipo tipo) {
        errorYPara("[ERROR]\tNo se puede parsear un tipo void.", new Vector<>());
        return false;
    }

    @Override
    public Objeto metodosInstancia(Objeto o, String m, Vector<Objeto> p) {
        errorYPara("[ERROR]\tNo se puede llamar al método " + m + " de un tipo void.", p);
        return null;
    }

    @Override
    public Tipo getTipo() {
        return T_VOID;
    }

    @Override
    public Instancia cast(Tipo tarTipo, Instancia valor) {
        errorYPara("[ERROR]\tNo se puede castear un tipo void.", new Vector<>());
        return null;
    }
}
