public class CTDTools {

    /**
     * int pow(int base, int exp);
     * en la variable pow, el reslultado de base^exp
     */
    public static void makePowFuncion() {
        PLXC.out.println("function pow:\n" +
                "   base_2 = param 1;\n" +
                "   exp_2 = param 2;\n" +
                "   res_3 = 1;\n" +
                "while_pow:\n" +
                "   if (0 < exp_2) goto body_while_pow;\n" +
                "   goto return_pow;\n" +
                "body_while_pow:\n" +
                "   $t0 = res_3 * base_2;\n" +
                "   res_3 = $t0;\n" +
                "   $t1 = exp_2 - 1;\n" +
                "   exp_2 = $t1;\n" +
                "   goto while_pow;\n" +
                "return_pow:\n" +
                "   pow = res_3;\n" +
                "   return;\n" +
                "end pow;\n");
    }

    /**
     * int rightShift(int x, int y); // x >> y
     *
     * Devuelve el resultado del desplazamiento en la variable <rightShift>
     */
    public static void makeShiftRight() {
        PLXC.out.println("function rightShift:\n" +
                "   x_2 = param 1;\n" +
                "   y_2 = param 2;\n" +
                "   $t0 = x_2;\n" +
                "   $t1 = 1;\n" +
                "while_shift:\n" +
                "   if (y_2 < $t1) goto return_shift;\n" +
                "   $t0 = $t0 / 2;\n" +
                "   $t1 = $t1 + 1;\n" +
                "   goto while_shift;\n" +
                "return_shift:\n" +
                "   res_3 = $t0;\n" +
                "   rightShift = res_3;\n" +
                "   return;\n" +
                "end rightShift;");
    }

    /**
     * almacena en la variable ctd <leftShift> el resultado de la operación: x << y
     */
    public static void makeLeftShift() {
        PLXC.out.println("function leftShift:\n" +
                "   x_2 = param 1;\n" +
                "   y_2 = param 2;\n" +
                "   $t0 = x_2;\n" +
                "   $t1 = 1;\n" +
                "while_lefthshift:\n" +
                "   if (y_2 < $t1) goto return_lefthshift;\n" +
                "   $t0 = $t0 + $t0;\n" +
                "   $t1 = $t1 + 1;\n" +
                "   goto while_lefthshift;\n" +
                "return_lefthshift:\n" +
                "   res_3 = $t0;\n" +
                "   leftShift = res_3;\n" +
                "   return;\n" +
                "end leftShift;");
    }

    public static void makeImport(String file) {
        PLXC.out.println(". " + file + ".ctd");
    }

    public static void deplotTools() {
        CTDTools.makePowFuncion();
        CTDTools.makeShiftRight();
        CTDTools.makeLeftShift();
    }
}
