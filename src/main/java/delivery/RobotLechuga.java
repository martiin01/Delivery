package delivery;

import java.util.concurrent.BlockingQueue;

public class RobotLechuga implements Runnable {
    private final BlockingQueue<String> contenedorLechuga;

    public RobotLechuga(BlockingQueue<String> contenedorLechuga) {
        this.contenedorLechuga = contenedorLechuga;
    }

    @Override
    public void run() {
        while (true) {
            try {
                // Añadimos una "lechuga" a la cola (bloquea si está llena).
                contenedorLechuga.put("lechuga");
                System.out.println("RobotLechuga agregó una pieza de lechuga. Total actual: " +
                    contenedorLechuga.size());
                Thread.sleep(800); // Simula la producción de lechuga
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
