import java.util.Vector;

public class TChar extends Tipo{

    public static enum CHAR_METHODS {
        SUMA
    }
    private static final TChar T_CHAR = new TChar();

    public TChar() {
        super(String.valueOf(Tipos_PL.CHAR), 0, false);
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
