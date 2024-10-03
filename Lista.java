import java.util.Formatter;

public class Lista {
    private Nodo inicio;
    private Nodo fin;
    private int i;

    public Lista() {
        inicio = null;
        fin = null;
    }

    public void add(String cadena, String tipo, String valor, String apariciones, int linea) {
        i++;
        Nodo nuevoNodo = new Nodo(cadena, tipo, valor, apariciones, linea, i);
        if (inicio == null) {
            inicio = nuevoNodo;
            fin = nuevoNodo;
        } else {
            nuevoNodo.sig = inicio;
            inicio.ant = nuevoNodo;
            inicio = nuevoNodo;
        }
    }

    public void addAtEnd(String cadena, String tipo, String valor, String apariciones, int linea) {
        i++;
        Nodo nuevoNodo = new Nodo(cadena, tipo, valor, apariciones,linea, i);
        if (inicio == null) {
            inicio = nuevoNodo;
            fin = nuevoNodo;
        } else {
            fin.sig = nuevoNodo;
            nuevoNodo.ant = fin;
            fin = nuevoNodo;
        }
    }
    

    public String remove() {
        if (isEmpty()) {
            throw new RuntimeException("La lista está vacía");
        }
        i--;
        String cadena = inicio.cadena;
        String t = inicio.t;
        int i = inicio.i;
        inicio = inicio.sig;
        if (inicio != null) {
            inicio.ant = null; // Actualiza el enlace al nodo anterior
        } else {
            fin = null; // La lista está vacía, actualiza el puntero al final
        }
        return cadena + "," + t + "," + i;
    }

    public String removeLast() {
        if (isEmpty()) {
            throw new RuntimeException("La lista está vacía");
        }
        i--;
        String cadena = fin.cadena;
        String t = fin.t;
        int i = fin.i;
        fin = fin.ant;  // Mueve el puntero al final al nodo anterior
        if (fin != null) {
            fin.sig = null; // Actualiza el enlace al siguiente nodo
        } else {
            inicio = null; // La lista está vacía, actualiza el puntero al inicio
        }
        return cadena + "," + t + "," + i;
    }
    

    public String getFirst() {
        if (isEmpty()) {
            throw new RuntimeException("La lista está vacía");
        }
        return inicio.cadena + "," + inicio.t + "," + inicio.i;
    }

    public String getLast() {
        if (isEmpty()) {
            throw new RuntimeException("La lista está vacía");
        }
        return fin.cadena;
    }

    public Nodo getN(String cadena) {
        Nodo nodoActual = inicio;
        while (nodoActual != null) {
            if (nodoActual.cadena.equals(cadena)) {
                return nodoActual;
            }
            nodoActual = nodoActual.sig;
        }
        return null;
    }

    public boolean isEmpty() {
        return inicio == null;
    }

    public int getSize() {
        return i;
    }

    public void printList() {
        Nodo nodoActual = fin;
        // Usamos Formatter para imprimir en formato de tabla
        Formatter formatter = new Formatter(System.out);
        // Encabezado de la tabla
        formatter.format("%-8s%-20s%-12s%-15s%-10s%-10s%n", "Línea", "Tipo", "Cadena", "Apariciones", "Valor", "Índice");
        while (nodoActual != null) {
            // Imprimir cada fila de la tabla
            formatter.format("%-8d%-20s%-12s%-15s%-10s%-10d%n", nodoActual.i, nodoActual.t, nodoActual.cadena,
                    nodoActual.apariciones, nodoActual.valor, nodoActual.linea);
            nodoActual = nodoActual.ant;
        }
        // Aseguramos que los datos se impriman
        formatter.flush();
    }

    public String toString() {
        Nodo nodoActual = inicio;
        StringBuilder result = new StringBuilder();
    
        while (nodoActual != null) {
            result.insert(0, nodoActual.cadena); // Inserta al principio para mantener el orden
            nodoActual = nodoActual.sig;
    
            if (nodoActual != null) {
                result.insert(0, "");
            }
        }
    
        return result.toString();
    }

    public boolean contains(String cadena) {
        Nodo nodoActual = inicio;
        while (nodoActual != null) {
            if (nodoActual.cadena.equals(cadena)) {
                return true;
            }
            nodoActual = nodoActual.sig;
        }
        return false;
    }

    public void setEmpty() {
        inicio = null;
        fin = null;
        i = 0;
    }

    public class Nodo {
        public String cadena;
        public int i;//indice de la tabla
        public int linea;//linea de código
        public String t;//tipo
        public String valor;//para simbolos
        public String apariciones;//para simbolos
        public Nodo sig;
        public Nodo ant;
        public String codigo;
        public Nodo(String cadena, String t,String valor, String apariciones, int i, int linea){
            this.cadena = cadena;
            this.t = t;
            this.valor = valor;
            this.linea = linea;
            this.apariciones = apariciones;
            this.i = i;
            sig = null;
            ant = null;
        }
    }
}
