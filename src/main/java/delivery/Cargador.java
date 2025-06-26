package delivery;

public class Cargador implements Runnable {
    
    private Recarga recarga;
    private ArranqueCargador arranque;

    public Cargador(Recarga recarga, ArranqueCargador arranque) {
        this.recarga = recarga;
        this.arranque = arranque;
    }
    
    @Override
    public void run() {
        arranque.esperarMoteros();
        while (true) {
            recarga.hacerCarga(); // Carga una moto
        }
    }
}