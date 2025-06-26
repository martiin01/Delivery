package delivery;

public class RobotPan implements Runnable {
    ContenedorCocina contenedorPan;

    public RobotPan(ContenedorCocina contenedor) {
        this.contenedorPan = contenedor;
    }

    @Override
    public void run() {
        while (true) {
            try {
                contenedorPan.put();
                System.out.println("RobotPan fabricó una pieza de pan. Total pan: " + contenedorPan.getCount());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
