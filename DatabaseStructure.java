import java.io.*;
import java.util.*;

public class DatabaseStructure {
    private List<String> data;

    public DatabaseStructure(String filePath) throws IOException {
        this.data = new ArrayList<>();
        loadDataFromFile(filePath);
    }

    // Método para carregar o arquivo "bd.txt"
    private void loadDataFromFile(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        String line;
        while ((line = reader.readLine()) != null) {
            data.add(line);
        }
        reader.close();
        System.out.println("Dados carregados: " + data.size() + " palavras.");
    }

    // Método para leitura
    public String read(int index) {
        return data.get(index);
    }

    // Método para escrita
    public void write(int index, String value) {
        data.set(index, value);
    }

    public int size() {
        return data.size();
    }
}
