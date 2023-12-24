import java.util.Objects;
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

    public static TString getInstance(){
        return T_STRING;
    }

    /**
     * Genera el código de tres direccones para concatenar dos strings. Código de tres direcciones para la concatenación de dos strings.
     *
     *
     * @param c1 Cadena 1
     * @param c2 Cadena 2
     * @return Variable en la que se referencia el resultado de la concatenación en el lenguaje de tres direcciones.
     */
    private static Objects concatDosStrings(Objeto c1, Objeto c2) {
        return  null;
    }
}
