import java.util.ArrayList;

public class ensamblador {
    String cadena = "";
    String CadenaTemporal = "";
    int contRegistros = 1;
    ArrayList<String> CadenasEnsamblador = new ArrayList<>();
    public void agregar(String s){
        cadena = s;
        modificarTerceto(s);
        imprimir();
    }

    public String modificarTerceto(String s){
        String[] palabras = s.split(" ");
        String simbolo = palabras[0];
    
        switch (simbolo) {
            case "__": // Declaración
                simbolo = "MOV";
                palabras[1] = "R" + contRegistros++;
                CadenaTemporal = simbolo + " " + palabras[1] + " " + palabras[2];
                break;
            case "=": // Asignación
                simbolo = "MOV";
                CadenaTemporal = simbolo + " " + palabras[1] + " " + palabras[2];
                break;
            case "+": // Suma
                simbolo = "ADD";
                CadenaTemporal = simbolo + " " + palabras[1] + " " + palabras[2];
                break;
            case "-": // Resta
                simbolo = "SUB";
                CadenaTemporal = simbolo + " " + palabras[1] + " " + palabras[2];
                break;
            case "*": // Multiplicación
                simbolo = "MUL";
                CadenaTemporal = simbolo + " " + palabras[1] + " " + palabras[2];
                break;
            case "/": // División
                simbolo = "DIV";
                CadenaTemporal = simbolo + " " + palabras[1] + " " + palabras[2];
                break;
            default:
                return ""; // Caso inválido
        }
    
        if (!CadenasEnsamblador.contains(CadenaTemporal)) { // Evitar duplicados
            CadenasEnsamblador.add(CadenaTemporal);
        }
    
        return CadenaTemporal;
    }
    
    public void imprimir() {
        System.out.println("*********** ENSAMBLADOR ***********");
        for (String s : CadenasEnsamblador) {
            System.out.println(s);
        }
        System.out.println("***********************************");
    }
    

}
