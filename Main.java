
import java.io.FileNotFoundException;

public class Main {
    private static SimpleLock lock; // Arquivo: SimpleLock.java
    private static ReaderWriterControl controlador; // Arquivo: ReaderWriterControl.java
    private static DatabaseStructure bd; // Arquivo: DatabaseStructure.java
    private static RandomNumber numAleatorio; // Arquivo: RandomNumber.java
    private static Thread[] threads;
    private static final int totalProporcoes = 101;
    private static final int execucoesPorProporcao = 50;

    public static void main(String[] args) throws FileNotFoundException, InterruptedException {
        lock = new SimpleLock(); // Uso do Lock
        controlador = new ReaderWriterControl(); // Controle de leitores e escritores
        bd = new DatabaseStructure(); // Estrutura do Banco de Dados

        for (int k = 0; k < 2; k++) { // 2 tipos de implementação
            String modoExecucao = (k == 0) ? "com controle de leitores/escritores" : "sem controle de leitores/escritores";
            System.out.println("\nSimulação " + modoExecucao + ":");

            long inicio = System.currentTimeMillis();

            for (int i = 0; i < totalProporcoes; i++) {
                int tempoTotal = 0;

                for (int j = 0; j < execucoesPorProporcao; j++) {
                    bd.inicializarBD(); // Reinicializando o BD
                    numAleatorio = new RandomNumber(); // Números aleatórios
                    criarThreads(i, k + 1); // Cria as threads para leitores/escritores

                    long tempoInicial = System.currentTimeMillis();
                    iniciarThreads(); // Inicia as threads
                    esperarThreads(); // Aguarda o término das threads
                    long tempoFinal = System.currentTimeMillis();

                    tempoTotal += (tempoFinal - tempoInicial);
                }

                double tempoMedio = tempoTotal / (double) execucoesPorProporcao;
                System.out.printf("Proporção: %d Readers, %d Writers - Tempo médio: %.2f ms%n", (100 - i), i, tempoMedio);
            }

            long fim = System.currentTimeMillis();
            System.out.println("\nTempo final " + ((fim - inicio) / 60000) + " min");
        }
    }

    private static void criarThreads(int qtdEscritores, int modoExecucao) throws FileNotFoundException {
        threads = new Thread[100];
        for (int i = 0; i < qtdEscritores; i++) {
            threads[i] = new Thread(new Escritor(i + 1, controlador, lock, modoExecucao)); // Escritores
        }
        for (int i = qtdEscritores; i < 100; i++) {
            threads[i] = new Thread(new Leitor(i + 1, controlador, lock, modoExecucao)); // Leitores
        }
    }

    private static void iniciarThreads() {
        for (Thread t : threads) {
            t.start(); // Inicia todas as threads
        }
    }

    private static void esperarThreads() throws InterruptedException {
        for (Thread t : threads) {
            t.join(); // Aguarda o término de todas as threads
        }
    }
}
