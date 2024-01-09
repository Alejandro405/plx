
import java.util.*;

/**
 * Encapsulación de los elementos necesarios para implementar una función en código de trés direcciones.
 */
public class Funcion extends Objeto{
    public static enum FUNCION_METHODS {
        APPLY
    }


    private String etiq;

    private String id;

    private Tipo tipoRetorno;

    // <Tipo del parámetro, instancia con la que se accede al parámetro dentro del cuerpo de la función>
    private Map<Tipo, Instancia> params;


    public Funcion(String nombre, Tipo tipo, Map<Tipo, Instancia> parametros) {
        super("FUNCTION", 0, false);
        this.id = nombre;
        this.params = parametros;
        this.tipoRetorno = tipo;
        this.etiq = PLXC.tablaSimbolos.getNewEtiq();
    }

    @Override
    public Objeto metodos(String metodo, Vector<Objeto> params) {
        if (metodo.equals(FUNCION_METHODS.APPLY.name())) {
            List<Instancia> valores = castVector(params);
        }

        errorYPara("[ERROR]\tEl método no está definido para el objeto función", new Vector<>());
        return null;
    }

    private List<Instancia> castVector(Vector<Objeto> params) {
        List<Instancia> res = new ArrayList<>();

        for (Objeto o : params) {
            if (!(o instanceof Instancia)){
                errorYPara("[ERROR]\tLos parámetros de la función deben ser instancias", new Vector<>(List.of(o)));
            } else {
                res.add((Instancia) o);
            }
        }

        return res;
    }

    /**
     * Genera el código CTD para la declaración de la función. Por simplicidad el identificador de la función es el nombre de la variable de retorno.
     */
    public void retornaResultado(Instancia valor){
        Instancia returnInstancia = null;
        Objeto aux = PLXC.tablaSimbolos.getObj(this.getReturnVar(), 0);

        if (valor.getTipoInstancia() != this.tipoRetorno){
            errorYPara("[ERROR]\tEl tipo de retorno de la función no coincide con el tipo de la instancia", new Vector<>(List.of(valor)));
        }

        if (!(aux instanceof Instancia)){
            errorYPara("[ERROR]\tEl identificador de la función <> no ha sido declarado como instancia", new Vector<>(List.of(aux)));
        } else {
            if (((Instancia) aux).getTipoInstancia() != this.tipoRetorno){
                errorYPara("[ERROR]\tEl tipo de retorno de la función no coincide con el tipo de la instancia declarada en la tabla de símbolos", new Vector<>(List.of(aux, tipoRetorno)));
            }
            returnInstancia = (Instancia) aux;
            PLXC.out.println(returnInstancia.getNombre() + " = " + valor.getNombre() + ";" );
            PLXC.out.println("return;");
        }
    }


    /**
     * Genera el código CTD para la inicialización los parámetros de la función, con las instancias recividas como parámetros.
     * @param valores Lista de valores para la llamada a la función.
     */
    public void initCallParams(List<Instancia> valores){
        // Indicer para acceder a los i-ésimos parámetros definidos antes de la llamada a la función.
        int i = 1;
        for (var entry : params.entrySet()) {
            Instancia param = entry.getValue();
            PLXC.out.println(param.getNombre() + " = param " + (i + 1) + ";");

            i++;
        }
    }

    /**
     * Genera el código CTD para la llamada a la función. Así para la función, int suma(int a, int b), se debe obtener:
     *      param 1 = 2;
     *      param 2 = 2;
     *      call suma;
     */
    public void callFunction(List<Instancia> valores){
        List<Tipo> tipos = params.keySet().stream().toList();

        if (valores.size() != tipos.size()){
            errorYPara("[ERROR]\tEl número de parámetros no coincide con el número de parámetros de la función", new Vector<>());
        }

        for (int i = 0; i < valores.size(); i++) {
            Instancia x = valores.get(i);
            if (x.getTipoInstancia() != tipos.get(i)){
                errorYPara("[ERROR]\tEl tipo de los parámetros no coincide con el tipo de los parámetros de la función", new Vector<>(List.of(x)));
            }
            PLXC.out.println("param " + i + " = " + x.getNombre() + ";");
        }

        PLXC.out.println("call " + getInicFuncition() + ";");
    }

    public String getInicFuncition() {
        return "function_" + this.etiq;
    }

    public String getEndFuncition() {
        return "end_function_" + this.etiq;
    }

    public String getReturnVar() {
        return "returned_value_" + this.getNombre();
    }

    public Instancia getReturnInstancia(){
        Instancia res = null;
        if (PLXC.tablaSimbolos.getObj(this.id, 0) instanceof Instancia)
            res = (Instancia) PLXC.tablaSimbolos.getObj(this.id, 0);
        return res;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Funcion funcion = (Funcion) o;
        return Objects.equals(id, funcion.id) && Objects.equals(tipoRetorno, funcion.tipoRetorno);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tipoRetorno);
    }
}
