import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class main {
    ArrayList<String> Ensamblador = new ArrayList<>();
    public void main(ArrayList<String> Lista) {
        guardar(Lista);
        guardar2("Ensamblador.txt");
    }
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new FileReader("cadenas.txt"))) {// lee el archivo
             Lexico l = new Lexico();// crea un objeto del analizador sintactico
             String line;// lee una línea
             while ((line = br.readLine()) != null) {// mientras aún haya líneas
             l.analisisL(line);// realiza un análisis léxico-sintáctico a la cadena recibida
             }
              l.printT();
            // Sintactico s = new Sintactico(l.getTokens());// crea un objeto del analizador
            // sintactico
            // s.analisisS();

            // Cerrar el PrintWriter al finalizar
            // s.writer.close();
            // le pasamos las lineas por lineas y el programa las guardara para despues su
            // captura
            System.out.println("Tercetos\n****************************************************************************");
            BufferedReader br2 = new BufferedReader(new FileReader("Posfija.txt"));
            String linea;
            while ((linea = br2.readLine()) != null) {
                //System.out.println(linea);
                Tercetos t = new Tercetos(linea);
                t.ProcedimientoPre();
            }
            br2.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void guardar(ArrayList<String> lista) {
       // Ruta del archivo
        String archivo = "Ensamblador.txt";

        // Crear un Set para eliminar duplicados
        Set<String> lineasUnicas = new HashSet<>(lista);

        // Intentamos escribir en el archivo en modo apéndice
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo, true))) {
            for (String linea : lineasUnicas) {
                writer.write(linea);
                writer.newLine();
            }
            System.out.println("Datos únicos agregados al archivo: " + archivo);
        } catch (IOException e) {
            System.err.println("Error al guardar el archivo: " + e.getMessage());
        }
    }
    public static void guardar2(String archivo) {
        // Usamos un Set para almacenar las líneas únicas
        Set<String> lineasUnicas = new LinkedHashSet<>();

        // Leer el archivo y almacenar las líneas únicas en el Set
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                lineasUnicas.add(linea.trim()); // Trim para evitar duplicados por espacios en blanco
            }
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
            return;
        }

        // Sobrescribir el archivo con las líneas únicas
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
            for (String linea : lineasUnicas) {
                writer.write(linea);
                writer.newLine();
            }
            System.out.println("Archivo actualizado sin duplicados.");
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }
}
