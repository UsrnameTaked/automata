import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class main {
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new FileReader("cadenas.txt"))) {
            Lexico lexico = new Lexico();
            br.lines().forEach(lexico::analisisL); // Procesar líneas en una sola línea
            lexico.printT();
    
            System.out.println("Tercetos\n****************************************************************************");
            try (BufferedReader br2 = new BufferedReader(new FileReader("Posfija.txt"))) {
                br2.lines().map(Tercetos::new).forEach(Tercetos::ProcedimientoPre); // Procesar tercetos
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
