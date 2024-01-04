import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

public class TString extends Tipo{
    public static enum STRING_METHODS {
        ASIGNA, PRINT
    }

    private static final TString T_STRING = new TString();

    private static final Instancia string_iterator = new Instancia("__STRING__ITERATOR__", TChar.getTChar(), 0, true);

    private String tam;

    public TString() {
        super(TChar.getTChar().getNombre(), 0, false);
        this.tam = "0";
    }

    public static void main(String[] args) {

        String a = "a\\b";
        var b = a.toCharArray();

        for (char c : b) {
            System.out.print(c);
        }
        System.out.println();

    }

    public Objeto metodos(String metodo, Vector<Objeto> params) {
        errorYPara("[ERROR]\tNo se puede llamar al método " + metodo + " de un tipo string.", params);
        return null;
    }

    public boolean isParseable(Tipo tipo) {
        return false;
    }

    public Objeto metodosInstancia(Objeto o, String m, Vector<Objeto> p) {
        if (o.getClass() != StringInstancia.class)
            errorYPara("[ERROR]\tNo se puede llamar al método " + m + " de un tipo string sobre un objeto que no ses una StringInstancia", new Vector<>(List.of(o)));

        StringInstancia targetString = (StringInstancia) o;

        if (m.equals(STRING_METHODS.ASIGNA.name())) {
            if (p.size() != 1)
                errorYPara("[ERROR]\tEl método asigna de un string debe recibir un único parámetro (string)", new Vector<>());

            if (p.firstElement().getClass() != StringInstancia.class)
                errorYPara("[ERROR]\tEl método asigna ha de recibir una StringInstance", new Vector<>());

            StringInstancia srcString = (StringInstancia) p.firstElement();

            return asigna(targetString, srcString);
        }

        if (m.equals(STRING_METHODS.PRINT.name())) {
            if (!p.isEmpty())
                errorYPara("[ERROR]\tEl método print de un string no debe recibir ningún parámetro", new Vector<>());
            printString(targetString);
            return targetString;
        }

        errorYPara("[ERROR]\tNo se encontró el método <" + m + "> definido sobre el tipo String", new Vector<>());
        return null;
    }

    @Override
    public Tipo getTipo() {
        return T_STRING;
    }

    public Instancia cast(Tipo tarTipo, Instancia valor) {
        errorYPara("[ERROR]\tPor el momento no se puede castear un string", new Vector<>());
        return null;
    }

    public static TString getInstance(){
        return T_STRING;
    }

    /**
     * Genera el código de tres direccones para concatenar dos strings. Código de tres direcciones para la concatenación de dos strings.
     *
     *
     * @param dstString Cadena de destino
     * @param srcString Cadena de origen.
     * @return Variable en la que se referencia el resultado de la concatenación en el lenguaje de tres direcciones.
     */
    private static Objeto asigna(StringInstancia dstString, StringInstancia srcString) {
        if (srcString.isConstant()) {
            // Asignar a una variable de tipo string cadena constante (string a = ".....";)
            return asignaConstante(dstString, srcString.getNombre().toCharArray());
        } else {
            // Asignar a una variable de tipo string otra variable de tipo string (string a = b;)
        }

        return  null;
    }

    public static StringInstancia asignaConstante(StringInstancia dstString, char[] charArray) {
        int tam = charArray.length;

        for (int i = 0; i < tam; i++) {
            PLXC.out.println(dstString.getNombre() + "[" + i + "] = " + (int) charArray[i] + ";");
        }


        dstString.setTam(String.valueOf(tam));
        return dstString;
    }

    /**
     * Printeará el strig asociado a la instancia. No se hacen checkeo de ningún tipo, se asume que todo es correcto
     * @param string Cadena a mostrar
     */
    private void printString(StringInstancia string) {
        int tam = Integer.parseInt(string.getTam());

        for (int i = 0; i < tam; i++) {
            PLXC.out.println(string_iterator.getNombre() + " = " + string.getNombre() + "[" + i + "];");
            PLXC.out.println("writec " + string_iterator.getNombre() + ";");
        }

        PLXC.out.println("printc 0;");
    }
}
