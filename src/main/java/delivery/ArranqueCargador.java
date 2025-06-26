package delivery;

import java.util.concurrent.CountDownLatch;

public class ArranqueCargador {
    private CountDownLatch latch;

    public ArranqueCargador(int numeroMoteros) {
        latch = new CountDownLatch(numeroMoteros);
    }
    
    // Método para que cada motero notifique que ha llegado.
    public void moteroHaLlegado() {
        latch.countDown();
    }
    
    // Método que llamará el Cargador para esperar a que todos los moteros hayan notificado.
    public void esperarMoteros() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}