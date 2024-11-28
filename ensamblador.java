import java.util.ArrayList;

public class ensamblador {
    String cadena = "";
    String CadenaTemporal = "";
    int contRegistros = 1;
    ArrayList<String> CadenasEnsamblador = new ArrayList<>();
    public void agregar(String s){
        cadena = s;
        modificarTerceto(s);
    }

    public String modificarTerceto(String s){
        s.split(" ");
        String[] palabras = s.split(" ");
        String simbolo = palabras[0];
        if (simbolo.equals("__")) {
            simbolo = "MOV";
            palabras[1] = "R" + contRegistros;
            contRegistros++;
            CadenaTemporal = simbolo + " " + palabras[1] + " " + palabras[2];
        }
        if (simbolo.equals("=")) {
            simbolo = "MOV";
            CadenaTemporal = simbolo + " " + palabras[1] + " " + palabras[2];
        }
        if (simbolo.equals("+")) {
            simbolo = "ADD";
            CadenaTemporal = simbolo + " " + palabras[1] + " " + palabras[2];
        }
        if (simbolo.equals("-")) {
            simbolo = "SUB";
            CadenaTemporal = simbolo + " " + palabras[1] + " " + palabras[2];
        }
        if (simbolo.equals("*")) {
            simbolo = "MUL";
            CadenaTemporal = simbolo + " " + palabras[1] + " " + palabras[2];
        }
        if (simbolo.equals("/")) {
            simbolo = "DIV";
            CadenaTemporal = simbolo + " " + palabras[1] + " " + palabras[2];
        }
        CadenasEnsamblador.add(CadenaTemporal);
        return s;
    }

    public void imprimir(){
        for (String s : CadenasEnsamblador) {
            System.out.println(s);
        }
    }

}
