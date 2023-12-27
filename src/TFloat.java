import java.util.Vector;

public class TFloat extends Tipo{

    public static enum FLOAT_METHODS {
        SUMA, RESTA, MULTIPLICACION, DIVISION, MAYOR, MENOR, MAYOR_IGUAL, MENOR_IGUAL, IGUAL, DISTINTO
    }

    private static final TFloat T_FLOAT = new TFloat();

    public TFloat() {
        super(String.valueOf(Tipos_PL.FLOAT), 0, false);
    }

    @Override
    public Objeto metodos(String metodo, Vector<Objeto> params) {
        return null;
    }

    @Override
    public Objeto metodosInstancia(Objeto o, String m, Vector<Objeto> p) {
        return null;
    }

    public static TFloat getTFloat(){
        return T_FLOAT;
    }

}
