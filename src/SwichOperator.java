import java.util.ArrayList;
import java.util.List;

public class SwichOperator {

    private String etiqSwitch;

    private Instancia expr;

    private List<String> cases;


    public SwichOperator(String etiqSwitch, Instancia expr) {
        this.etiqSwitch = etiqSwitch;
        this.expr = expr;
        this.cases = new ArrayList<>();
    }

    public String getEtiqSwitch() {
        return "switch_" + etiqSwitch;
    }

    public Instancia getExpr() {
        return expr;
    }

    public List<String> getCases() {
        return cases;
    }

    public void addCase(String caseEtq) {
        this.cases.add(caseEtq);
    }

    public static String genCase (String caseEtq) {
        return "case_" + caseEtq;
    }

    public static String getEndCase(String caseEtq) {
        return "end_case_" + caseEtq;
    }

    public String genDefault () {
        return "default_" + this.etiqSwitch;
    }

    public String getEndSwitch() {
        return "end_switch_" + this.etiqSwitch;
    }
}
