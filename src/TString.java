import java.util.Vector;

public class TString extends Tipo{

    public static enum STRING_METHODS {
        SUMA
    }

    private static final TString T_STRING = new TString();

    public TString() {
        super(String.valueOf(Tipos_PL.STRING), 0, false);
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
