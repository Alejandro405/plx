import java.util.Vector;

public class Instancia extends Objeto{
    private final Tipo tipoInstancia;


    public Instancia(String nombre, Tipo tipo, Integer bloque, Boolean mutable) {
        super(nombre, bloque, mutable);
        this.tipoInstancia = tipo;
    }

    public Instancia(Tipo tipoInstancia) {
        super(Objeto.newNombreObjeto(), TablaSimbolos.bloqueActual, false);
        this.tipoInstancia = tipoInstancia;
    }

    @Override
    public Objeto metodos(String metodo, Vector<Objeto> params) {
        return tipoInstancia.metodosInstancia(this, metodo, params);
    }

    public boolean sameType(Instancia i1, Instancia i2){
        return i1.getTipoInstancia() == i2.getTipoInstancia();
    }

    public Tipo getTipoInstancia() {
        return tipoInstancia;
    }

    public static Instancia getIntConstant(Integer valor){
        return new Instancia(valor.toString(), TInt.getTInt(), 0, false);
    }

    public static Instancia getBoolConstant(Boolean valor){
        return new Instancia(valor.toString(), TBool.getTBool(), 0, false);
    }

    public static Instancia getCharConstant(Character valor){
        return new Instancia(valor.toString(), TChar.getTChar(), 0, false);
    }
}
