package delivery;

import java.util.concurrent.Semaphore;

public class Recarga {
    private ControlMoteros controlMoteros;
    // Semáforo para avisar al "cargador" que hay una moto esperando.
    private Semaphore motosEsperando = new Semaphore(0);
    // Semáforo para avisar a la moto cuando la recarga ha concluido.
    private Semaphore motoLista = new Semaphore(0);

    public Recarga(ControlMoteros controlMoteros) {
        this.controlMoteros = controlMoteros;

    }
    public void esperaCarga(){
        try {
            // Aviso: "hay una moto más esperando".
            motosEsperando.release();
            // La moto se bloquea hasta que el cargador libere la recarga terminada.
            motoLista.acquire();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    
    public void hacerCarga(){
       
        try {
             // Espera a que al menos una moto requiera recarga.
            motosEsperando.acquire();
             // Aquí se simula el tiempo de recarga…
            System.out.println("Cargador -> Cargando una moto eléctrica...");
            Thread.sleep(1000);
            System.out.println("Cargador -> Finalizada la recarga de una moto.");
            // Cuando acaba, da paso a que una moto pueda continuar.
            motoLista.release();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
       

    }
}
