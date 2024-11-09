
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class DatabaseStructure {
    private static ArrayList<String> backup;
    static ArrayList<String> bdAtual;
    private static RandomNumber geradorAleatorio;
    private static Scanner scanner;

    public DatabaseStructure() throws FileNotFoundException {
        backup = new ArrayList<>();
        geradorAleatorio = new RandomNumber();
        scanner = new Scanner(new File("bd.txt"));
        while (scanner.hasNext()) {
            backup.add(scanner.nextLine());
        }
    }

    public void inicializarBD() throws FileNotFoundException {
        bdAtual = new ArrayList<>(backup);
    }

    public static void acessarBD() {
        int tamanho = bdAtual.size();
        for (int i = 0; i < 100; i++) {
            String linha = bdAtual.get(geradorAleatorio.gerar(tamanho));
        }
    }

    public static void modificarBD() {
        int tamanho = bdAtual.size();
        for (int i = 0; i < 100; i++) {
            bdAtual.set(geradorAleatorio.gerar(tamanho), "MODIFICADO");
        }
    }
}
