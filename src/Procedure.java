import java.util.Set;

public class Procedure extends Funcion{
    public Procedure(String nombre, Set<Instancia> parametros) {
        super(nombre, TVoid.getTVoid(), parametros);
    }

    public Procedure(String nombre, Tipo tipo, Set<Instancia> parametros) {
        super(nombre, TVoid.getTVoid(), parametros);
    }

    @Override
    public String getId() {
        return super.getId();
    }

    @Override
    public String getInicFuncition() {
        return "procedure_" + super.getEtiq();
    }

    @Override
    public String getEndFuncition() {
        return "end_procedure_" + super.getEtiq();
    }

    @Override
    public void retornaResultado(Instancia valor) {
        PLXC.out.println("return;");
        PLXC.out.println(this.getEndFuncition());
    }

    @Override
    public String getReturnVar() {
        return "";
    }

    @Override
    public Instancia getReturnInstancia() {
        return null;
    }
}
