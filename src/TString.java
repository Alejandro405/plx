import java.util.List;
import java.util.Vector;

public class TString extends Tipo{
    public static enum STRING_METHODS {
        ASIGNA, PRINT, SUMA, LENGTH, GET, SET
    }

    private static final TString T_STRING = new TString();

    private static final Instancia string_iterator = new Instancia("__STRING__ITERATOR__", TChar.getTChar(), 0, true);
    private int posActual = 0;

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
        if (!(o instanceof Instancia))
            errorYPara("[ERROR]\tNo se puede llamar al método " + m + " de un tipo string sobre un objeto que no ses una Instancia", new Vector<>(List.of(o)));


        StringInstancia targetString = null;
        if (o instanceof StringInstancia) {
            targetString = (StringInstancia) o;
        } else {
            targetString = new StringInstancia(o);
        }

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

        if (m.equals(STRING_METHODS.GET.name())){
            if (p.size() != 1)
                errorYPara("[ERROR]\tEl método get de un string debe recibir un único parámetro (int)", new Vector<>());

            if (!(p.firstElement() instanceof Instancia))
                errorYPara("[ERROR]\tEl método get de un string debe recibir una única instancia", new Vector<>());

            Instancia index = (Instancia) p.firstElement();
            if (index.getTipoInstancia() != TInt.getTInt())
                errorYPara("[ERROR]\tEl método get de un string debe recibir una instancia de tipo int como índice", new Vector<>());

            return getElem(targetString, index);
        }

        if (m.equals(STRING_METHODS.SET.name())) {
            if (p.size() != 2)
                errorYPara("[ERROR]\tEl método set de un string debe recibir dos parámetros (indice e Instancia para asignar)", new Vector<>());

            if (!(p.firstElement() instanceof Instancia) || !(p.lastElement() instanceof Instancia))
                errorYPara("[ERROR]\tEl método set de un string debe recibir instancias parámetros", new Vector<>());

            Instancia index = (Instancia) p.firstElement();
            Instancia elem = (Instancia) p.lastElement();

            return setElem(targetString, index, elem);
        }


        if (m.equals(STRING_METHODS.LENGTH.name())) {
            if (!p.isEmpty())
                errorYPara("[ERROR]\tEl método length de un string no debe recibir ningún parámetro", new Vector<>());

            Instancia res = new Instancia(TInt.getTInt());

            PLXC.out.println(res.getNombre() + " = " + targetString.getTam() + ";");

            return res;
        }

        if (m.equals(STRING_METHODS.SUMA.name())) {

            if (p.size() != 1 || !(p.firstElement() instanceof Instancia))
                errorYPara("[ERROR]\tEl método suma de un string debe recibir un único parámetro (StringInstance o una instancia de tipo char)", new Vector<>());


            if (((Instancia) p.firstElement()).getTipoInstancia() == TChar.getTChar()){
                return appendChar(targetString, (Instancia) p.firstElement());
            }else {
                StringInstancia b = (StringInstancia) p.firstElement();
                return concatDosStrings(targetString, b);
            }
        }

        errorYPara("[ERROR]\tNo se encontró el método <" + m + "> definido sobre el tipo String", new Vector<>());
        return null;
    }

    private StringInstancia appendChar(StringInstancia a, Instancia objeto) {
        // Aumentar la cadena actual (tamaño y añadir el valor del objeto recivido como parámetro):
        int oldTam = Integer.parseInt(a.getTam());
        int newTam = oldTam + 1;

        PLXC.out.println(a.getNombre() + "[" + oldTam + "] = " + objeto.getNombre() + ";");
        a.setTam(String.valueOf(newTam));

        return a;
    }

    private boolean isCharInstancia(Objeto objeto) {
        return objeto instanceof Instancia && ((Instancia) objeto).getTipoInstancia() == TChar.getTChar();
    }

    private static StringInstancia concatDosStrings(StringInstancia a, StringInstancia b) {
        int newTam = Integer.parseInt(a.getTam()) + Integer.parseInt(b.getTam());

        StringInstancia res = new StringInstancia(newTam);

        int n = Integer.parseInt(a.getTam());
        int i = 0;
        while (i < n) {
            PLXC.out.println(string_iterator.getNombre() + " = " + a.getNombre() + "[" + i + "];");
            PLXC.out.println(res.getNombre() + "[" + i + "] = " + string_iterator.getNombre() + ";");
            i++;
        }


        n += Integer.parseInt(b.getTam());
        while (i < n) {
            PLXC.out.println(string_iterator.getNombre() + " = " + b.getNombre() + "[" + (i - Integer.parseInt(a.getTam())) + "];");
            PLXC.out.println(res.getNombre() + "[" + i + "] = " + string_iterator.getNombre() + ";");
            i++;
        }

        return res;
        /* Aumentar la cadena actual:
        int oldTam = Integer.parseInt(targetString.getTam());
        int newTam = oldTam  + Integer.parseInt(x.getTam());

        for (int i = oldTam; i < newTam; i++) {
            PLXC.out.println(string_iterator.getNombre() + " = " + x.getNombre() + "[" + (i - oldTam) + "];");
            PLXC.out.println(targetString.getNombre() + "[" + i + "] = " + string_iterator.getNombre() + ";");
        }
        */

    }

    private Objeto setElem(StringInstancia targetString, Instancia index, Instancia elem) {
        checkIndex(targetString, index);

        PLXC.out.println(targetString.getNombre() + "[" + index.getNombre() + "] = " + elem.getNombre() + ";");
        return targetString;
    }

    private void checkIndex(StringInstancia targetString, Instancia index) {
        String l = PLXC.tablaSimbolos.getNewEtiq();

        PLXC.out.println("check_index_" + l + ":");
        PLXC.out.println("if (" + index.getNombre() + " < 0) goto error_index_" + l + ";");
        PLXC.out.println("if (" + targetString.getTam() + " < " + index.getNombre() + ") goto error_index_" + l + ";");
        PLXC.out.println("if (" + index.getNombre() + " == " + targetString.getTam() + ") goto error_index_" + l + ";");
        PLXC.out.println("goto end_check_index_" + l + ";");
        PLXC.out.println("error_index_" + l + ":");
        PLXC.out.println("error;\nhalt;");
        PLXC.out.println("end_check_index_" + l + ":");
    }

    private Objeto getElem(StringInstancia targetString, Instancia index) {
        checkIndex(targetString, index);
        Instancia res = new Instancia(TChar.getTChar());

        // Asignar al String iterator y usar el Método asigna de TChar
        PLXC.out.println(res.getNombre() + " = " + targetString.getNombre() + "[" + index.getNombre() + "];");

        return  res;
    }

    private Instancia next(StringInstancia str, Instancia itr, Instancia posActual) {

        PLXC.out.println(itr.getNombre() + " = " + str.getNombre() + " [" + posActual +"];");
        PLXC.out.println(posActual.getNombre() + " = " + posActual.getNombre() + " + 1;");

        return itr;
    }

    private void hasNext(StringInstancia str, Instancia posActual, String end) {
        PLXC.out.println("if (" + posActual.getNombre() + " == " + str.getTam() + ") goto " + end + ";");
        PLXC.out.println("if (" + str.getTam() + " < " + posActual.getNombre() + ") goto " + end + ";");
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

        int tamFinal = Integer.parseInt(srcString.getTam());
        for (int i = 0; i <tamFinal; i++) {
            PLXC.out.println(string_iterator.getNombre() + " = " + srcString.getNombre() + "[" + i + "];");
            PLXC.out.println(dstString.getNombre() + "[" + i + "] = " + string_iterator.getNombre() + ";");
        }
        dstString.setTam(String.valueOf(tamFinal));

        return  dstString;
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
