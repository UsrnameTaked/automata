import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Formatter;

public class Sintactico {
    final String[] terminales = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","ñ","o","p","q","r","s","t","u","v","w","x","y","z",
    "A","B","C","D","E","F","G","H","I","J","K","L","M","N","Ñ","O","P","Q","R","S","T","U","V","W","X","Y","Z",
    "0","1","2","3","4","5","6","7","8","9",
    "+","-","*",
    "int","float","double","print"}; //terminales
    Map<String, Map<String, String>> tabla = new HashMap<>(); //matriz predictiva
    Pila X;//Declara la pila que vamos a usar
    Lexico l;//Declaramos un Objeto de analizador léxico para poder analizar la cadena
    PrintWriter writer;//para generar el archivo para visualizar mejor la salida
    public Lista W;
    
    
    public Sintactico(Lista W) throws IOException{
        this.W = W;

        writer = new PrintWriter(new FileWriter("salida.txt", false));//le decimos dónde va quedar la salida

        X = new Pila();//inicializamos la pila y el analizador léxico
        l = new Lexico();

        // Añadir elementos a la matriz predictiva
        tabla.put("S", new HashMap<>());
        tabla.get("S").put("programa", "C");

        tabla.put("C", new HashMap<>());
        tabla.get("C").put("programa", "programa I{DDDAAOP}");
        
        tabla.put("I", new HashMap<>());
        for(int i = 0; i<=53; i++){
            tabla.get("I").put(terminales[i], "LN");
        }

        tabla.put("L", new HashMap<>());
        for(int i = 0; i<=53; i++){
            tabla.get("L").put(terminales[i], terminales[i]);
        }

        tabla.put("N", new HashMap<>());
        for(int i = 54; i<=63; i++){
            tabla.get("N").put(terminales[i], terminales[i]);
        }

        tabla.put("D", new HashMap<>());
        for(int i = 67; i<=69; i++){
            tabla.get("D").put(terminales[i], "T I;");
        }

        tabla.put("A", new HashMap<>());
        for(int i = 0; i<=53; i++){
            tabla.get("A").put(terminales[i], "I=N;");
        }

        tabla.put("T", new HashMap<>());
        for(int i = 67; i<=69; i++){
            tabla.get("T").put(terminales[i], terminales[i]);
        }

        tabla.put("O", new HashMap<>());
        for(int i = 0; i<=53; i++){
            tabla.get("O").put(terminales[i], "I=IMI;");
        }

        tabla.put("M", new HashMap<>());
        for(int i = 64; i<=66; i++){
            tabla.get("M").put(terminales[i], terminales[i]);
        }

        tabla.put("P", new HashMap<>());
        tabla.get("P").put("print", "print(I);");
    }

    

    public void analisisS(){//recibe una cadena que vamos a analizar para que pase por el analizador lexico y sintactico
        W.printList();
        try {//realiza un try catch
            X.push("$");//inicializamos la pila con la regla de produccion inicial
            X.push("S");
            W.add("$", "simbolo", "", "",0);//agregamos el ultimo terminal para que coincida con la pila
            Formatter formatter = new Formatter(writer);//para dar formato a la salida en consola y en archivo
            //Formatter formatterprinter = new Formatter(System.out);
            formatter.format("%-30s %-80s %-30s %-30s\n", "Pila(X)", "Entrada(a) W$", "M[A,a]", "Acción"); //cabecera de la tabla
            //formatterprinter.format("%-30s %-80s %-30s %-30s\n", "Pila(X)", "Entrada(a) W$", "M[A,a]", "Acción");

            while (!W.isEmpty()) {//un ciclo que se repite mientras la lista no este vacia
                formatter.format("%-30s %-80s %-30s", X.toString(), W.toString(),//imprime lo que hay en la pila, en la lista
                "M[" + X.tope.cadena + "," + W.getLast() + "]");//y la comparación que va hacer
                //formatterprinter.format("%-30s %-80s %-30s", X.toString(), W.toString(),
                //"M[" + X.tope.cadena + "," + W.getLast() + "]");
                
                if (X.tope.cadena.equals(W.getLast())) {//si lo que hay en la entrada de la lista coincide con lo que hay en la pila
                    formatter.format("%-30s\n", "igual");//imprime que son iguales
                    //formatterprinter.format("%-30s\n", "igual");
                    //System.out.println(X.tope.cadena+"      "+W.getLast()+"        M[" + X.tope.cadena + "," + W.getLast() + "]         ");
                    X.pop();//realiza un pop
                    W.removeLast();//remueve el ultimo elemento de la lista
                } else {//sino
                    String comp = null;
                    if (X.tope.cadena.equals("I") || X.tope.cadena.equals("A") || X.tope.cadena.equals("O")) {//revisamos si el siguiente de nuestra regla de producción que está en nuestra pila va ser un id
                        char[] temp = W.getLast().toCharArray();//si es así convierte nuestra entrada de la lista en un arreglo
                        W.removeLast();//remueve el último elemento de la lista
                        for (int i = temp.length; i > 0; i--) {//lo sustituye agregando el id de manera invertida
                            W.addAtEnd(temp[i - 1] + "", "ID", "", "",0);
                        }
                        // System.out.println("Pila ID:"+X.tope.cadena+" Lista:"+W.getLast());
                        comp = comp(X.tope.cadena, W.getLast());//realiza la comparación de la pila con la lista lo que normalmente sería la comparación con LN
                        formatter.format("%-30s\n", "Comp: " + comp);
                        X.pop();//realiza el pop
                        char[] reglas = comp.toCharArray();//la regla que obtenemos le hace push de manera invertida
                        for (int i = reglas.length; i > 0; i--) {
                            // System.out.println(reglas[i-1]);
                            X.push(reglas[i - 1] + "");
                        }
                    } else {//si el siguiente no es un id
                        comp = comp(X.tope.cadena, W.getLast());//realiza la comparación
                        formatter.format("%-30s\n", "Comp: " + comp);
                        //formatterprinter.format("%-30s\n", "Comp: " + comp);
                        X.pop();//realiza un pop la pila
                        String[] reglas = l.div(comp);//dividimos las cadenas de la pila para invertir las reglas y meterlas a la pila
                        for (int i = reglas.length; i > 0; i--) {
                            if (reglas[i - 1] != null && reglas[i - 1].matches("[A-Z]+")) {//verifica si es una regla de producción
                                char[] nt = reglas[i - 1].toCharArray();//si es así divide la cadena las reglas de producción y las invierte en la pila
                                for (int x = nt.length; x > 0; x--) {
                                    X.push(nt[x - 1] + "");
                                }
                            } else {//sino mete las cadenas igual en orden invertido
                                X.push(reglas[i - 1]);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            if(W == null){
                System.out.println("Error Léxico, verifique su cadena");
            }else{
                System.out.println("Error sintáctico :c");
            }
        }
    }

    public String comp(String R, String c){//método para comparar la regla obtenida con la cadena y obtener un resultado según la matriz
        return tabla.get(R).get(c);
    }

}
