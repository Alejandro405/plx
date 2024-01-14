import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

/**
 * TODO: implementar las operaciones para manipular la lista
 */
public class SetInstance extends Instancia {

    private int tam;

    private Tipo elemsType;

    private boolean isConstant;

    private List<Instancia> elems;


    public SetInstance(String nombre, Tipo tipo, Integer bloque, Boolean mutable) {
        super(nombre, tipo, bloque, mutable);
    }

    public SetInstance(String id, Tipo elemTyp, Integer bloque, boolean mutable) {
        super(id, TSet.getTSet(), bloque, mutable);
        this.tam = 0;
        this.elems = new ArrayList<>();
        this.isConstant = mutable;
        this.elemsType = elemTyp;
    }

    public SetInstance(String id, Tipo elemTyp, List<Instancia> inic, Integer bloque, boolean mutable){
        super(id, TSet.getTSet(), bloque, mutable);
        //System.out.println("asdfasdfasdfasd");

        SetInstance.ensureTypes(elemTyp, inic);
        this.elems = inic;
        this.tam = inic.size();
        this.elemsType = elemTyp;
        this.isConstant = mutable;
    }

    private static void ensureTypes(Tipo elemsType, List<Instancia> inic) {
        if (inic.isEmpty())
            return;

        boolean error = false;
        Iterator<Instancia> itr = inic.iterator();
        while (itr.hasNext() && !error) {
            Instancia x = itr.next();
            if (x.getTipoInstancia() != elemsType) {
                error = true;
                errorYPara("[ERROR]\tLos valores de un conjunto han de ser del mismo tipo. La lista de instancias proporcionada no es homogénea", new Vector<>());
            }
        }
    }

    public SetInstance(SetInstance ref, Integer bloqueActual, boolean mutable) {
        super(ref.getNombre(), ref.getElemsType(), bloqueActual, mutable);
        this.isConstant = mutable;
        this.elems = List.copyOf(ref.getElems());
        this.tam = ref.getTam();
        this.elemsType = ref.getElemsType();
    }

    public int getTam() {
        return tam;
    }

    public void setTam(int tam) {
        this.tam = tam;
    }

    public Tipo getElemsType() {
        return elemsType;
    }

    public void setElemsType(Tipo elemsType) {
        this.elemsType = elemsType;
    }

    public boolean isConstant() {
        return isConstant;
    }

    public void setConstant(boolean constant) {
        isConstant = constant;
    }


    public List<Instancia> getElems() {
        return elems;
    }

    /**
     * Si el elemento ya se encuentra en la lista no hace nada
     * @param elem
     */
    public void addElem(Instancia elem) {
        if (elem.getTipoInstancia() != elemsType)
            errorYPara("[ERROR]\tEl elemento proporcionado para añadir al conjunto no es del mismo tipo que los demás elementos del conjunto", new Vector<>(List.of(elem)));


        int i = 0;
        boolean encontrado = false;

        while(i < elems.size() && !encontrado) {
            Instancia x = elems.get(i);
            if (!x.getNombre().equals(elem.getNombre()))
                encontrado = true;
            else
                i++;
        }

        if (!encontrado) {
            elems.add(elem);
        }
    }

    public boolean contains(String Id) {
        int i = 0;
        boolean encontrado = false;

        while(i < elems.size() && !encontrado) {
            encontrado = elems.get(i).getNombre().equals(Id);

            i++;
        }

        return encontrado;
    }

    public void delElem(String Id) {
        int i = 0;
        boolean encontrado = false;

        while(i < elems.size() && !encontrado) {
            Instancia x = elems.get(i);
            if (x.getNombre().equals(Id)) {
                elems.remove(i);
                encontrado = true;
            }

            i++;
        }
    }

    public boolean contains(Instancia x) {
        boolean res = false;

        if (x instanceof StringInstancia) {
            res = contains(x.getNombre());
        }

        return res;
    }

    public void iterate(Instancia iter) {

    }

    public void setElems(List<Instancia> instancias) {
        this.elems = instancias;
    }
}
