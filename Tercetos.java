import java.util.ArrayList;
public class Tercetos {
    ensamblador esamble = new ensamblador();
    String[] operadores = {"+", "-", "*", "/"}; // Operadores válidos
    String[] palabras; // Cadena inicial separada por palabras
    boolean tipoOperacion;
    int contTemp = 0; // Contador de temporales
    ArrayList<String> listaTercetos = new ArrayList<>(); // Almacenar los tercetos generados
    public Tercetos(String cadena) {
        // Dividimos la cadena en palabras usando split (por espacios)
        //System.out.println("Consultamos la cadena "+cadena);
        //Delacarion(cadena.charAt(0));
        if(Delacarion(cadena.charAt(0)) == 1){
            this.tipoOperacion = true;
            this.palabras = cadena.split(" ");
        }
        if (Delacarion(cadena.charAt(0)) == 2){
            this.tipoOperacion = false;
            this.palabras = cadena.split(",");   
        }
    }
    // Procedimiento para generar los tercetos recursivamente
    void ProcedimientoPre() {
        //vemos que procedimiento realizamos
       if (tipoOperacion) {
        //haremos el tipo normal 
        //System.out.println("Analizaremos " + String.join(" ", palabras));
        String op1 = "__", op2 = "__", op = "__";
        boolean encontradoOperador = false;
        
        for (String s : palabras) {
            // Leemos la cadena
            //System.out.println("Cadena analizando -> " + s);
            
            // Verificar si es un operador
            if (s.equals("=")) {
                op = s; // Asignamos el operador
                encontradoOperador = true;
            } 
            // Si ya se encontró un operador, lo que sigue son los operandos
            else if (encontradoOperador) {
                if (op2.equals("__")) {
                    op2 = s; // Asignamos el segundo operando
                }
            }
            // Si aún no se ha encontrado un operador, estamos con el primer operando
            else {
                if (op1.equals("__")) {
                    op1 = s; // Asignamos el primer operando
                }
            }  
        }
        //metemos la segunda tercetos si es que op2 sigue vacio despues de forin
        if (op2.equals("__")) {
            op2 = palabras[palabras.length - 1];
        }
        //System.out.println("Operador: " + op);
        //System.out.println("Primer operando: " + op1);
        //System.out.println("Segundo operando: " + op2);
        //System.out.println("Imprimios el terceto");
        System.out.println(imprimirTerceto(op, op1, op2));
       }
       else {
        //haremos el tipo de operacion
        while (contieneOperadores()) {
            for (int i = 0; i < palabras.length - 2; i++) {  // -2 para evitar desbordamientos
                String palabra = palabras[i];

                // Si la palabra actual es un operador
                if (esOperador(palabra)) {
                    //System.out.println(String.join(",", palabras));
                    // Verificar si las dos siguientes palabras son operandos
                    String operando1 = palabras[i + 1];
                    String operando2 = palabras[i + 2];

                    if (esOperando(operando1) && esOperando(operando2)) {
                        // Generar el terceto con el operador y los dos operandos
                        String temp = generarTerceto(palabra, operando1, operando2);
                        //System.out.println(palabras[i] + " " + palabras[i + 1] + " " + palabras[i + 2] + " -> " + temp);
                        //System.out.println("generando ");
                        // Imprimir el terceto generado
                        //System.out.println("Terceto generado: " + temp);

                        // Reemplazar en la cadena el operador y los dos operandos por el temporal generado
                        reemplazarPorTemporal(i, temp);
                        //ponemos formato de terceto 
                        System.out.println("(= "+temp+" "+operando1+")");   
                        //siguiente operacion de operacion con el simbolo 
                        System.out.println(imprimirTerceto(palabra, temp, operando2));                     
                        // Reiniciar el ciclo desde el comienzo, ya que la cadena ha cambiado
                        break;
                    }
                }
            }
        }
        // Imprimir la cadena final
        System.out.println("(" + String.join(" ", palabras) + ")");
        //System.out.println(imprimirTerceto(palabras[0], palabras[1], palabras[2]));
        }
        esamble.imprimir();
    }
    // Método auxiliar para generar el terceto
    String generarTerceto(String operador, String operando1, String operando2) {
        contTemp++; // Incrementar el contador de temporales
        return "Temp" + contTemp;
    }
    // Método auxiliar para imprimri un terceto
    String imprimirTerceto(String operador, String operando1, String operando2) {
        String Ensamblador = "";
        Ensamblador = operador + " " + operando1 + " " + operando2;
        esamble.agregar(Ensamblador);
        return "(" + operador + " " + operando1 + " " + operando2 + ")";
    }
    // Método para verificar si una palabra es un operador
    boolean esOperador(String s) {
        for (String op : operadores) {
            if (s.equals(op)) {
                return true;
            }
        }
        return false;
    }
    // Método para verificar si una palabra es un operando (letra o número)
    boolean esOperando(String s) {
        return s.matches("[A-Za-z0-9]+"); // Verifica que solo contenga letras o números
    }
    // Método auxiliar para verificar si aún hay operadores en la cadena
    boolean contieneOperadores() {
        for (String palabra : palabras) {
            if (esOperador(palabra)) {
                return true;
            }
        }
        return false;
    }
    // Método para reemplazar el operador y los dos operandos por un temporal
    void reemplazarPorTemporal(int i, String temp) {
        palabras[i] = temp; // Reemplazar el operador con el temporal
        palabras[i + 1] = ""; // Vaciar el operando1
        palabras[i + 2] = ""; // Vaciar el operando2

        // Reconstruir el arreglo sin los espacios vacíos
        ArrayList<String> nuevaCadena = new ArrayList<>();
        for (String palabra : palabras) {
            if (!palabra.isEmpty()) {
                nuevaCadena.add(palabra);
            }
        }

        // Actualizar el arreglo de palabras
        palabras = nuevaCadena.toArray(new String[0]);
    }
    //Metodo para saber si es declaracion o operacion
    int Delacarion(char c){
        if (c != '=') {
            //System.out.println("Declaracion");
            return 1;
        } else {
            //System.out.println("Operacion");
            return 2;
        }
    }
}
