import java.util.Random;

class Writer implements Runnable {
    private final DatabaseStructure db;
    private final ReaderWriterControl control;
    private final SimpleLock lock;
    private final Random random = new Random();

    public Writer(DatabaseStructure db, ReaderWriterControl control, SimpleLock lock) {
        this.db = db;
        this.control = control;
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            // Se for usado o controle Reader-Writer, inicia a escrita
            if (control != null) control.startWrite();
            // Se for usado SimpleLock, bloqueia o acesso
            if (lock != null) lock.lock();

            // Realiza 100 acessos para escrita
            for (int i = 0; i < 100; i++) {
                int index = random.nextInt(db.size());
                db.write(index, "MODIFICADO");
            }

            // Simula validação com um tempo de espera de 1ms
            Thread.sleep(1);

            // Finaliza o acesso à base
            if (control != null) control.endWrite();
            if (lock != null) lock.unlock();

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
