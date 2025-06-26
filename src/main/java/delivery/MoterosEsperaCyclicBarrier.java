package delivery;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import pcd.util.ColoresConsola;
import pcd.util.Traza;

public class MoterosEsperaCyclicBarrier {
    private CyclicBarrier barrier;

    public MoterosEsperaCyclicBarrier(int numeroMoteros) {
        // Si no necesitas acción, puedes pasar directamente el número de participantes
        barrier = new CyclicBarrier(numeroMoteros);
    }

    public void estamosTodos(int id) {
        Traza.traza(ColoresConsola.RED, 2, "Motero " + id + " esperando a moteros (CyclicBarrier)");
        try {
            barrier.await(); 
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}