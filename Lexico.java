/*import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;*/
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexico {
    Lista errores, simbolos, palabrasR, tokens;
    private final String Alfabeto = "[A-Za-z0-9=\\+\\-\\*]*"; 
    private  Matcher matcher;
    private Pattern pattern;
    private final String L = "[A-Z-a-z]";
    private final String N = "[0-9]";
    private final String PR = "(programa||print||int)";
    private final String ID = "("+L+")("+N+")";
    public final String Sep = "[{};()=\\+\\-\\* ]";
    public final String declaracion = "(int "+ID+")";
    public final String asignacion = "("+ID+"=("+N+"))";
    public int linea;
    public Lexico(){
        errores = new Lista();
        simbolos = new Lista();
        palabrasR = new Lista();
        tokens = new Lista();
        linea = 1;
    }

    public Lista comp(String[] cadenas, String cadena) {
        boolean e = false;
        for (int i = 0; i < cadenas.length; i++) {
            if (cadenas[i].matches(PR)) {
                if (!palabrasR.contains(cadenas[i])) { // Verificar si no está en la Lista
                    palabrasR.add(cadenas[i], "", "", "", linea);
                }
                tokens.add(cadenas[i], "Palabra Reservada", "", "", linea);
            } else {
                if (cadenas[i].matches(ID)) {
                    if (!simbolos.contains(cadenas[i])) { // Verificar si no está en la Lista
                        simbolos.add(cadenas[i], "", "", "1", linea);
                    }else{
                        simbolos.getN(cadenas[i]).apariciones = (Integer.parseInt(simbolos.getN(cadenas[i]).apariciones)+1)+"";
                    }
                    tokens.add(cadenas[i], "ID", "", "", linea);
                } else {
                            if (cadenas[i].matches(N)) {
                                if (!simbolos.contains(cadenas[i])) { // Verificar si no está en la Lista
                                    simbolos.add(cadenas[i], "", "", "", linea);
                                }
                                tokens.add(cadenas[i], "Valor", "", "", linea);
                            } else {
                                if(cadenas[i].matches(Sep)){
                                    tokens.add(cadenas[i], "Separador", "", "", linea);
                                }else{
                                     if (checkE(cadenas[i])) {
                                        errores.add(cadenas[i], "ER", "", "", linea); // Error de Expresión regular
                                    } else {
                                        errores.add(cadenas[i], "EA", "", "", linea); // Error de alfabeto
                                    }
                                    e = true;
                                }
                            }
                        }
                    }
        }
        linea ++;
        encontrarDA(cadena, simbolos);

        if (e) {
            System.out.println("Cadena no válida");
            return null;
        } else {
            System.out.println("Cadena válida");
            return tokens;
        }
    }

    public void encontrarDA(String cadena, Lista simbolos) {
        String regex, separacion;
        for(int i = 0; i<2; i++){
            if(i == 0){
                regex = declaracion;
                separacion = " ";
            }else{
                regex = asignacion;
                separacion = "=";
            }
            pattern = Pattern.compile(regex);
            matcher = pattern.matcher(cadena);
        
            while (matcher.find()) {
                String declaracionEncontrada = matcher.group();
                System.out.println(declaracionEncontrada);
                String[] sep = declaracionEncontrada.split(separacion);
                if(i == 0){
                    simbolos.getN(sep[1]).t = sep[0];
                }else{
                    simbolos.getN(sep[0]).valor = sep[1];
                }
            }
        }
    }
    


    public boolean checkE(String c){//checa el tipo de error
        if (c.matches(Alfabeto)){
            return true;
        }else{
            return false;
        }
    }

    public void printT(){
        System.out.println("\nTabla de símbolos");
        simbolos.printList();
        System.out.println("\nTabla de palabras reservadas");
        palabrasR.printList();
        System.out.println("\nTabla de tokens");
        tokens.printList();
        System.out.println("\nTabla de errores");
        errores.printList();
        System.out.println("////////////////////////////////////////////");
    }

    public String[] div(String cadena){
         // Utiliza afirmaciones de búsqueda hacia adelante y hacia atrás para conservar los separadores en el resultado
        String[] cadenas = cadena.split("(?<=["+Sep+"])|(?=["+Sep+"])");
        return cadenas;
    }

    public Lista analisisL(String cadena){//recibe el código fuente que vamos a analizar para que pase por el analizador léxico
        String[] cadenas = div(cadena);//convierte el código fuente en un arreglo para analizar cadena por cadena
        Lista W = comp(cadenas, cadena);//creamos una lista la cual vamos a regresar para el analizador sintactico
        //printT();//imprime las tablas generadas en el proceso del analizador lexico
        return W;//regresa la lista
    }

    public Lista getTokens(){
        return tokens;
    }

   public static void main(String args[]){
         try (BufferedReader br = new BufferedReader(new FileReader("cadenas.txt"))) {//lee el archivo
            Lexico l = new Lexico();//crea un objeto del analizador lexico
    
            String line;//lee una línea
            while ((line = br.readLine()) != null) {//mientras aún haya líneas
                l.analisisL(line);//realiza un análisis léxico-sintáctico a la cadena recibida
            }
            Lista tokens = l.getTokens();
            tokens.printList();
        } catch (IOException e) {
            e.printStackTrace();
        }       
    }
    

}