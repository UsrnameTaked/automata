import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

public class Lexico {
    Lista errores, simbolos, palabrasR, tokens;
    private final String Alfabeto = "[A-Za-z0-9=\\+\\-\\*]*"; 
    private  Matcher matcher;
    private Pattern pattern;
    private final String L = "[A-Z-a-z]";
    private final String N = "[0-9]";
    private final String PR = "(programa||print||int)";
    private final String ID = "("+L+")("+N+")";
    public final String Sep = "[{};()=\\+\\-\\*/ ]";
    public final String declaracion = "(int "+ID+")";
    public final String asignacion = "("+ID+"=("+N+"))";
    public final String operadores = "[\\+\\-\\*]";
    public final String operacion = ID+"="+ID+operadores+ID+";";
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
                        simbolos.getN(cadenas[i]).apariciones = (simbolos.getN(cadenas[i]).apariciones)+", "+linea;
                    }
                    tokens.add(cadenas[i], "ID", "", "", linea);
                } else {
                            if (cadenas[i].matches(N)) {
                                if (!simbolos.contains(cadenas[i])) { // Verificar si no está en la Lista
                                    simbolos.add(cadenas[i], "", "", linea+"", linea);
                                }else{
                                    simbolos.getN(cadenas[i]).apariciones = (simbolos.getN(cadenas[i]).apariciones)+", "+linea;
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
        encontrarDA(cadena, simbolos);//una vez esté completo el análisis léxico se realiza un análisis para la búsqueda de asignaciones y declaraciones
        encontrarO(cadena, simbolos);
        if (e) {
            System.out.println("Cadena no válida");
            return null;
        } else {
            System.out.println("Cadena válida");
            return tokens;
        }
    }

    public void encontrarDA(String cadena, Lista simbolos) {//pide una cadena a analizar y una lista
        String regex, separacion;//se usa una expresión regular para encontrar las asignaciones y declaraciones
        for(int i = 0; i<2; i++){
            if(i == 0){// primero buscamos si es una declación
                regex = declaracion;
                separacion = " ";
            }else{
                regex = asignacion;
                separacion = "=";
            }
            pattern = Pattern.compile(regex);
            matcher = pattern.matcher(cadena);
        
            while (matcher.find()) {//mientras se hayan encontrado coincidencias
                String declaracionEncontrada = matcher.group();
                String[] sep = declaracionEncontrada.split(separacion);//se separa la coincidencia encontrada
                if(i == 0){
                    simbolos.getN(sep[1]).t = sep[0];//agrega a la tabla de símbolos dependiendo si era assignación
                }else{
                    simbolos.getN(sep[0]).valor = sep[1];//o declaración
                }
            }
        }
    }


    public void encontrarO(String cadena, Lista simbolos) {//pide una cadena a analizar y una lista
        String regex, separacion;//se usa una expresión regular para encontrar operaciones
        regex = operacion;
        simbolos = this.simbolos;
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(cadena);
        separacion=";";
        
        while (matcher.find()) {//mientras se hayan encontrado coincidencias
            String declaracionEncontrada = matcher.group();
            String[] sep = declaracionEncontrada.split(separacion);//se separa la coincidencia encontrada
            System.out.println(sep[0]);
            Arbol a = new Arbol(sep[0], simbolos);
            System.out.println("");
            a.postfija(a.raiz);
            System.out.println("");
            a.verificarTipos(a.raiz);
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
            Lexico l = new Lexico();//crea un objeto del analizador sintactico
            
            String line;//lee una línea
            while ((line = br.readLine()) != null) {//mientras aún haya líneas
                l.analisisL(line);//realiza un análisis léxico-sintáctico a la cadena recibida
            }
            l.printT();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}