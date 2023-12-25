public abstract class TArray extends Tipo{

    private static final int DEF_TAM = 10;
    
    private final int tam;
    
    private final Tipo tipo;
    
    public TArray(Tipo tipo) {
        super(tipo.getNombre(), 0, false);
        this.tam = DEF_TAM;
        this.tipo = tipo;
    }
    
    public abstract Objeto getElem(int i);
    
    public abstract void setElem(int i, Objeto o);
    
    public int getTam() {
        return tam;
    }
    
    public Tipo getTipo() {
        return tipo;
    }
    
}
