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

    public static TChar getTChar(){
        return T_CHAR;
    }

    /**
     * Genera el código de tres direccones para concatenar dos chars. Código de tres direcciones para la concatenación de dos chars.
     *
     * x =
     *
     * @param c1 Cadena 1
     * @param c2 Cadena 2
     * @return Variable en la que se referencia el resultado de la concatenación en el lenguaje de tres direcciones.
     */
    private static Objeto concatDosChars(Objeto c1, Objeto c2) {
        return  null;
    }

}
