import java.util.List;
import java.util.Vector;

public class StringInstancia extends Instancia{

    private String tam;

    private boolean isConstant;


    public StringInstancia() {
        super(TString.getInstance());
        tam = "0";
        this.isConstant = isConstant;
    }

    public StringInstancia(Integer tam) {
        super(Objeto.newNombreObjeto(), TString.getInstance(), TablaSimbolos.bloqueActual, true);
        this.tam = tam.toString();
        this.isConstant = false;
    }

    public StringInstancia(String nombre) {
        super(nombre, TString.getInstance(), TablaSimbolos.bloqueActual, true);
        this.tam = "0";
        this.isConstant = false;
    }

    public StringInstancia(Objeto o) {
        super(o.getNombre(), TString.getInstance(), o.getBloque(), o.getMutable());
        tam = "0";
        this.isConstant = false;
    }

    public StringInstancia(char[] chars) {
        super(Objeto.newNombreObjeto(), TString.getInstance(), TablaSimbolos.bloqueActual, true);

        this.tam = String.valueOf(chars.length);
        for (int i = 0; i < chars.length; i++) {
            //Obtener el valor entero de la expresion char[i]
            int x = chars[i];

            PLXC.out.println(this.getNombre() + "[" + i + "] = " + x + ";");
        }
    }

    /*
    if (index < 0) goto end_foreac_forTag ;
    if (tam < index) goto end_foreac_forTag ;
    if (index == tam) goto end_foreac_forTag ;
    iter = cole[index];
    */
    public static void iterate(Instancia index, StringInstancia colec, Instancia iter, String end) {
        PLXC.out.println("if (" + index.getNombre() + " < 0) goto " + end + ";");
        PLXC.out.println("if (" + colec.getTam() + " < " + index.getNombre() + ") goto " + end + ";");
        PLXC.out.println("if (" + index.getNombre() + " == " + colec.getTam() + ") goto " + end + ";");
        iter.metodos("ASIGNA", new Vector<>(List.of(
                        colec.metodos(
                                    "GET",
                                    new Vector<>(List.of(index))
                        )
        )));
    }

    public String getTam() {
        return tam;
    }

    public void setTam(String tam) {
        this.tam = tam;
    }

    @Override
    public String toString() {
        return "StringInstancia{" +
                "tam='" + tam + '\'' +
                super.toString() +
                '}';
    }

    public boolean isConstant() {
        return this.isConstant;
    }

}
