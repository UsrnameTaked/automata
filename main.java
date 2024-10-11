import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class main {
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
                System.out.println(linea);
                Tercetos t = new Tercetos(linea);
                t.ProcedimientoPre();
            }
            br2.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
