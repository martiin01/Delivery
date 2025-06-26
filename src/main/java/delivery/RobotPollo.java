package delivery;

public class RobotPollo implements Runnable {
    ContenedorCocina contenedorPollo;

    public RobotPollo(ContenedorCocina contenedor) {
        this.contenedorPollo = contenedor;
    }

    @Override
    public void run() {
        while (true) {
            try {
                contenedorPollo.put();
                System.out.println("RobotPollo fabricó una pieza de pollo. Total pollo: " + contenedorPollo.getCount());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}