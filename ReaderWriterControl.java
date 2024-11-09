
public class ReaderWriterControl {
    int leitoresAtivos = 0;
    boolean escritorAtivo = false;

    boolean condicaoEscrita() {
        return leitoresAtivos == 0 && !escritorAtivo;
    }

    boolean condicaoLeitura() {
        return !escritorAtivo;
    }

    synchronized void iniciarLeitura() {
        while (!condicaoLeitura()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        leitoresAtivos++;
    }

    synchronized void finalizarLeitura() {
        leitoresAtivos--;
        notifyAll();
    }

    synchronized void iniciarEscrita() {
        while (!condicaoEscrita()) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        escritorAtivo = true;
    }

    synchronized void finalizarEscrita() {
        escritorAtivo = false;
        notifyAll();
    }
}
