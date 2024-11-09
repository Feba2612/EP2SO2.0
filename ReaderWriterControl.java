public class ReaderWriterControl {
    private int readers = 0;
    private boolean writer = false;

    public synchronized void startRead() throws InterruptedException {
        while (writer) {
            wait();
        }
        readers++;
    }

    public synchronized void endRead() {
        readers--;
        if (readers == 0) {
            notifyAll();
        }
    }

    public synchronized void startWrite() throws InterruptedException {
        while (readers > 0 || writer) {
            wait();
        }
        writer = true;
    }

    public synchronized void endWrite() {
        writer = false;
        notifyAll();
    }
}
