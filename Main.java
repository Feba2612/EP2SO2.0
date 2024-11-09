
import java.io.FileNotFoundException;

public class Main {
    private static SimpleLock lock;
    private static RandomNumber randomizador;
    private static ReaderWriterControl controlador;
    private static DatabaseStructure bancoDados;

    private static final int PROPORCOES = 101;
    private static final int REPETICOES = 50;
    private static Thread[] threads;

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        lock = new SimpleLock();
        controlador = new ReaderWriterControl();
        bancoDados = new DatabaseStructure();

        for (int tipo = 0; tipo < 2; tipo++) {
            long inicio = System.currentTimeMillis();
            System.out.println("Executando implementação " + (tipo + 1));
            for (int i = 0; i < PROPORCOES; i++) {
                int media = 0;
                for (int j = 0; j < REPETICOES; j++) {
                    bancoDados.inicializarBD();
                    randomizador = new RandomNumber();
                    criarThreads(i, tipo + 1);
                    long tempoInicial = System.currentTimeMillis();
                    iniciarThreads();
                    esperarThreads();
                    long tempoFinal = System.currentTimeMillis();
                    media += (tempoFinal - tempoInicial);
                }
                media /= REPETICOES;
                System.out.println("Média - " + i + " escritores e " + (100 - i) + " leitores - " + media + " ms");
            }
            long fim = System.currentTimeMillis();
            System.out.println("Tempo total: " + (fim - inicio) + " ms");
        }
    }

    private static void criarThreads(int qtdEscritores, int modoExecucao) throws FileNotFoundException {
        threads = new Thread[100];
        for (int i = 0; i < qtdEscritores; i++) {
            threads[i] = new Thread(new Escritor(i + 1, controlador, lock, modoExecucao));
        }
        for (int i = qtdEscritores; i < 100; i++) {
            threads[i] = new Thread(new Leitor(i + 1, controlador, lock, modoExecucao));
        }
    }

    private static void iniciarThreads() {
        for (Thread t : threads) {
            t.start();
        }
    }

    private static void esperarThreads() throws InterruptedException {
        for (Thread t : threads) {
            t.join();
        }
    }
}
