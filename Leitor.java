
import java.io.FileNotFoundException;

public class Leitor implements Runnable {
    private SimpleLock trava;
    private ReaderWriterControl controlador;
    private int modoExecucao;

    public Leitor(int id, ReaderWriterControl controlador, SimpleLock trava, int modoExecucao) throws FileNotFoundException {
        this.controlador = controlador;
        this.modoExecucao = modoExecucao;
        this.trava = trava;
    }

    @Override
    public void run() {
        if (modoExecucao == 1) executarModoRW();
        else if (modoExecucao == 2)
            try {
                executarModoLock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }

    private void executarModoLock() throws InterruptedException {
        trava.travar();
        acessarBD();
        trava.destravar();
    }

    private void executarModoRW() {
        controlador.iniciarLeitura();
        acessarBD();
        controlador.finalizarLeitura();
    }

    private void acessarBD() {
        DatabaseStructure.acessarBD();
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
