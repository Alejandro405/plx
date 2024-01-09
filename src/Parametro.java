import java.util.Map;

public class Parametro {
    private Instancia instancia;
    private Tipo tipo;

    public Parametro(Instancia instancia, Tipo tipo) {
        this.instancia = instancia;
        this.tipo = tipo;
    }

    public Instancia getInstancia() {
        return instancia;
    }

    public Tipo getTipo() {
        return tipo;
    }
}
