public class Pila{
    public Nodo tope;
    private int i;

    public Pila() {
        tope = null;
    }

    public void push(String cadena) {
        i++;
        Nodo nuevoNodo = new Nodo(cadena);
        nuevoNodo.sig = tope;
        tope = nuevoNodo;
    }

    public String pop() {
        if (isEmpty()) {
            throw new RuntimeException("La pila está vacía");
        }
        i--;
        String cadena = tope.cadena;
        tope = tope.sig;
        return cadena;
    }

    public String isFull() {
        if (isEmpty()) {
            throw new RuntimeException("La pila está vacía");
        }
        return tope.cadena;
    }

    public boolean isEmpty() {
        if(tope == null)
        return true;
        else
        return false;
    }

    public int getSize() {
        return i;
    }
        
    public String toString() {
        StringBuilder result = new StringBuilder();
        Nodo nodoActual = tope;

        while (nodoActual != null) {
            result.insert(0, nodoActual.cadena); // Insertar al principio para mantener el orden
            nodoActual = nodoActual.sig;

            if (nodoActual != null) {
                result.insert(0, "");
            }
        }

        return result.toString();
    }
    public void printStack() {
        Nodo nodoActual = tope;
        while (nodoActual != null) {
            System.out.print(nodoActual.cadena);
            nodoActual = nodoActual.sig;
        }
    }
    public void printStackReverse() {
        printStackReverse(tope);
    }
    
    public boolean contains(String cadena) {
        Nodo nodoActual = tope;
        while (nodoActual != null) {
            if (nodoActual.cadena.equals(cadena)) {
                return true; // Si se encuentra la cadena en la pila
            }
            nodoActual = nodoActual.sig;
        }
        return false; // Si no se encuentra la cadena en la pila
    }

    private void printStackReverse(Nodo nodoActual) {
        if (nodoActual != null) {
            printStackReverse(nodoActual.sig);
            System.out.println(nodoActual.cadena);
        }
    }
    
    public void setEmpty(){
        while (tope != null){
            pop();
        }
    }

    public class Nodo {
        public String cadena;
        public Nodo sig;
        public Nodo(String cadena){
            this.cadena = cadena;
            sig = null;
        }
    }
}