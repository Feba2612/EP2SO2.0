import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        DatabaseStructure db = new DatabaseStructure("bd.txt");
        int numExecutions = 50;
        int numThreads = 100;

        // Controle com ReaderWriterControl
        System.out.println("Simulação com controle de leitores/escritores:");
        long startControl = System.nanoTime();
        for (int i = 0; i <= numThreads; i++) {
            int readers = i;
            int writers = numThreads - i;
            double averageTime = runSimulation(db, readers, writers, numExecutions, true);
            System.out.printf("Proporção: %d Readers, %d Writers - Tempo médio: %.2f ms%n", readers, writers, averageTime);
        }
        long endControl = System.nanoTime();
        double totalTimeControl = (endControl - startControl) / 1_000_000_000.0 / 60.0;
        System.out.printf("Tempo final: %.2f min%n%n", totalTimeControl);

        // Controle sem ReaderWriterControl (usando SimpleLock)
        System.out.println("Simulação sem controle de leitores/escritores:");
        long startNoControl = System.nanoTime();
        for (int i = 0; i <= numThreads; i++) {
            int readers = i;
            int writers = numThreads - i;
            double averageTime = runSimulation(db, readers, writers, numExecutions, false);
            System.out.printf("Proporção: %d Readers, %d Writers - Tempo médio: %.2f ms%n", readers, writers, averageTime);
        }
        long endNoControl = System.nanoTime();
        double totalTimeNoControl = (endNoControl - startNoControl) / 1_000_000_000.0 / 60.0;
        System.out.printf("Tempo final: %.2f min%n", totalTimeNoControl);
    }

    /**
     * Método para executar a simulação com ou sem controle de leitores/escritores.
     */
    private static double runSimulation(DatabaseStructure db, int numReaders, int numWriters, int numExecutions, boolean useControl) {
        long totalDuration = 0;
        for (int exec = 0; exec < numExecutions; exec++) {
            ReaderWriterControl control = useControl ? new ReaderWriterControl() : null;
            SimpleLock lock = useControl ? null : new SimpleLock();

            List<Thread> threads = new ArrayList<>();
            for (int i = 0; i < numReaders; i++) {
                threads.add(new Thread(new Reader(db, control, lock)));
            }
            for (int i = 0; i < numWriters; i++) {
                threads.add(new Thread(new Writer(db, control, lock)));
            }

            // Embaralhar a lista de threads para distribuição aleatória
            Collections.shuffle(threads);

            // Medir o tempo de execução
            long start = System.nanoTime();
            for (Thread thread : threads) {
                thread.start();
            }
            for (Thread thread : threads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            long end = System.nanoTime();

            // Calcular a duração da execução
            totalDuration += (end - start) / 1_000_000;
        }

        // Calcular o tempo médio de execução
        return totalDuration / (double) numExecutions;
    }
}
