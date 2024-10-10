import java.util.ArrayList;

public class Tercetos {

    String[] operadores = {"+", "-", "*", "/"}; // Operadores válidos
    String[] palabras; // Cadena inicial separada por palabras
    int contTemp = 0; // Contador de temporales
    ArrayList<String> listaTercetos = new ArrayList<>(); // Almacenar los tercetos generados

    public Tercetos(String cadena) {
        // Dividimos la cadena en palabras usando split (por espacios)
        this.palabras = cadena.split(" ");
    }

    // Procedimiento para generar los tercetos recursivamente
    void ProcedimientoPre() {
        // Mientras haya operadores en la cadena
        while (contieneOperadores()) {
            for (int i = 0; i < palabras.length - 2; i++) {  // -2 para evitar desbordamientos
                String palabra = palabras[i];

                // Si la palabra actual es un operador
                if (esOperador(palabra)) {
                    // Verificar si las dos siguientes palabras son operandos
                    String operando1 = palabras[i + 1];
                    String operando2 = palabras[i + 2];

                    if (esOperando(operando1) && esOperando(operando2)) {
                        // Generar el terceto con el operador y los dos operandos
                        String temp = generarTerceto(palabra, operando1, operando2);

                        // Imprimir el terceto generado
                        System.out.println("Terceto generado: " + temp);

                        // Reemplazar en la cadena el operador y los dos operandos por el temporal generado
                        reemplazarPorTemporal(i, temp);

                        // Reiniciar el ciclo desde el comienzo, ya que la cadena ha cambiado
                        break;
                    }
                }
            }
        }

        // Imprimir la cadena final
        System.out.println("Cadena final: " + String.join(" ", palabras));
    }

    // Método auxiliar para generar el terceto
    String generarTerceto(String operador, String operando1, String operando2) {
        contTemp++; // Incrementar el contador de temporales
        return "Temp" + contTemp;
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

    public static void main(String[] args) {
        // Cadena de prueba
        Tercetos t = new Tercetos("= d * a b c d");
        t.ProcedimientoPre(); // Ejecutar el procedimiento para generar tercetos
    }
}
