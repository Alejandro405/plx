public class StringInstancia extends Instancia{

    private String tam;

    private boolean isConstant;


    public StringInstancia(boolean isConstant) {
        super(TString.getInstance());
        tam = "0";
        this.isConstant = isConstant;
    }

    public StringInstancia(String nombre) {
        super(nombre, TString.getInstance(), 0, true);
        this.tam = "0";
        this.isConstant = false;
    }

    public StringInstancia(Objeto o) {
        super(o.getNombre(), TString.getInstance(), o.getBloque(), o.getMutable());
        tam = "0";
        this.isConstant = false;
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
