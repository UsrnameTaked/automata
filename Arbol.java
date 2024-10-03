import java.util.ArrayList;

public class Arbol{
    private ArrayList<Nodo> nodos;
    public final String Sep = "[{};()=\\+\\-\\* ]";
    public Lista simbolos;


    Nodo raiz;
    public Arbol(String cadena, Lista simbolos){
        nodos = new ArrayList<Nodo>();
        this.simbolos = simbolos;
        nodos = nodos(cadena);
        raiz = genArbol(nodos);
    }

    private ArrayList<Nodo> nodos(String cadena){
        System.out.println("Elementos");
        simbolos.printList();
        ArrayList<Nodo> nodos = new ArrayList<Nodo>();

        String[] operandos = cadena.split("(?<=["+Sep+"])|(?=["+Sep+"])");
        for (String o : operandos) {
            if(simbolos.getN(o) != null){
                nodos.add(new Nodo(o, simbolos.getN(o).t));
                System.out.println(o+"  =>  "+simbolos.getN(o).t);
            }else{
                nodos.add(new Nodo(o, ""));
            }
        }

        for (Nodo nodo : nodos) {
            System.out.println(nodo.cadena+"        "+nodo.tipo);
        }
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
        String izq = ""; 
        String der = "";
        if(raiz.izquierda != null){
            postfija(raiz.izquierda);
            izq = raiz.izquierda.tipo;
        }
        if(raiz.derecha != null){
            postfija(raiz.derecha);
            der = raiz.derecha.tipo;
        }
        if(izq.equals("int") && der.equals("int")){
            raiz.tipo = "int";
        }
        if(simbolos.getN(raiz.cadena) != null){
            simbolos.getN(raiz.cadena).t = raiz.tipo;
        }
    }

    public void verificarTipos(Nodo raiz) {
        postfija(raiz); 
        if (raiz.cadena.equals("=") && raiz.tipo.equals(raiz.izquierda.tipo)) {
            System.out.println("Correcta");
        } else {
            System.out.println("Error: tipo de variables");
        }
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
        public Nodo(String cadena, String tipo){
            this.cadena = cadena;
            this.tipo = tipo;
            izquierda = null;
            derecha = null;
        }
    }

}