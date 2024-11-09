
public class SimpleLock {
    private boolean bloqueado = false;
    private Thread bloqueadoPor;
    private int contadorBloqueio = 0;

    public synchronized void travar() throws InterruptedException {
        Thread threadAtual = Thread.currentThread();
        while (bloqueado && bloqueadoPor != threadAtual) {
            wait();
        }
        bloqueado = true;
        contadorBloqueio++;
        bloqueadoPor = threadAtual;
    }

    public synchronized void destravar() {
        if (Thread.currentThread() == this.bloqueadoPor) {
            contadorBloqueio--;
            if (contadorBloqueio == 0) {
                bloqueado = false;
                notify();
            }
        }
    }
}
