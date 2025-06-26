package delivery;

import java.util.concurrent.Semaphore;

import pcd.util.ColoresConsola;
import pcd.util.Traza;

public class MoterosEspera {
    int cuantos;
    int numEspera;
    Semaphore s;
    public MoterosEspera(int numeroMoteros) {
        this.cuantos = 0;
        numEspera=numeroMoteros;
        s = new Semaphore(0);
    }

    public void estamosTodos(int id) {     
        cuantos++;
        try {
            if (cuantos < numEspera) {
            Traza.traza(ColoresConsola.RED,2,"MOTERO "+id+ " ESPERANDO A MOTEROS ");
            s.acquire();
            } else {
            s.release(id);
            Traza.traza(ColoresConsola.RED_UNDERLINED,2,"MOTEROS LISTOS");
        }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
}
