
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomNumber {
    private List<Integer> numeros;
    private Random random;

    public RandomNumber() {
        numeros = new ArrayList<>();
        random = new Random();
        preencher();
    }

    private void preencher() {
        for (int i = 0; i < 100; i++) {
            numeros.add(i);
        }
    }

    public int gerar(int limite) {
        return random.nextInt(limite);
    }

    public int gerar() {
        return numeros.remove(random.nextInt(numeros.size()));
    }
}
