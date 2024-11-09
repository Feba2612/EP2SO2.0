import java.util.Random;

class Reader implements Runnable {
    private final DatabaseStructure db;
    private final ReaderWriterControl control;
    private final SimpleLock lock;
    private final Random random = new Random();

    public Reader(DatabaseStructure db, ReaderWriterControl control, SimpleLock lock) {
        this.db = db;
        this.control = control;
        this.lock = lock;
    }

    @Override
    public void run() {
        try {
            if (control != null) control.startRead();
            if (lock != null) lock.lock();

            for (int i = 0; i < 100; i++) {
                int index = random.nextInt(db.size());
                db.read(index);
            }
            Thread.sleep(1);

            if (control != null) control.endRead();
            if (lock != null) lock.unlock();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
