import java.util.ArrayList;

public class Arbol{
    private String cadena;
    private ArrayList<Nodo> nodos;
    public final String Sep = "[{};()=\\+\\-\\* ]";


    Nodo raiz;
    public Arbol(String cadena){
        nodos = new ArrayList<Nodo>();
        this.cadena = cadena;
        nodos = nodos(cadena);
        raiz = genArbol(nodos);
    }

    private ArrayList<Nodo> nodos(String cadena){
        System.out.println("Operandos");
        ArrayList<Nodo> nodos = new ArrayList<Nodo>();

        String[] operandos = cadena.split("(?<=["+Sep+"])|(?=["+Sep+"])");
        for (String o : operandos) {
            System.out.println(o);
            nodos.add(new Nodo(o));
        }
        System.out.println("Operandos");
        return nodos;
    }

    public void prefija(Nodo raiz){
        System.out.print(raiz.cadena);
        if(raiz.izquierda != null){
            prefija(raiz.izquierda);
        }
        if(raiz.derecha != null){
            prefija(raiz.derecha);
        }
    }

    public void postfija(Nodo raiz){
        if(raiz.izquierda != null){
            postfija(raiz.izquierda);
        }
        if(raiz.derecha != null){
            postfija(raiz.derecha);
        }
        System.out.print(raiz.cadena);
    }

    public void infija(Nodo raiz){
        if(raiz.izquierda != null){
            infija(raiz.izquierda);
        }
        System.out.print(raiz.cadena);
        if(raiz.derecha != null){
            infija(raiz.derecha);
        }
    }

    public Nodo genArbol(ArrayList<Nodo> nodos){
        Nodo raiz;
        int size = nodos.size();
        for(int i = 0; i < size; i++){
            if(nodos.get(i).cadena.matches("[/*]")){
                nodos.get(i).izquierda = nodos.get(i-1);
                nodos.get(i).derecha = nodos.get(i+1);
                nodos.remove(i+1);
                nodos.remove(i-1);
                i --;
                size = nodos.size();
            }
        }
        for(int i = 0; i < size; i++){
            if(nodos.get(i).cadena.matches("[+-]")){
                nodos.get(i).izquierda = nodos.get(i-1);
                nodos.get(i).derecha = nodos.get(i+1);
                nodos.remove(i+1);
                nodos.remove(i-1);
                i --;
                size = nodos.size();
            }
        }
        for(int i = 0; i < size; i++){
            if(nodos.size() > 1){
                if(nodos.get(i).cadena.matches("[=]")){
                    nodos.get(i).izquierda = nodos.get(i-1);
                    nodos.get(i).derecha = nodos.get(i+1);
                    nodos.remove(i+1);
                    nodos.remove(i-1);
                    i --;
                    size = nodos.size();
                }
            }
        }
        raiz = nodos.get(0);
        return raiz;
    }

    private class Nodo{
    public String cadena, tipo;
    public Nodo izquierda, derecha;
        public Nodo(String cadena){
            this.cadena = cadena;
            izquierda = null;
            derecha = null;
        }
    }

    public static void main(String[] args) {
        String expresion ="a1=b2*5-4+c3";
        Arbol a = new Arbol(expresion);
        System.out.println("");
        a.prefija(a.raiz);
        System.out.println("");
        a.postfija(a.raiz);
        System.out.println("");
        a.infija(a.raiz);
    }
}