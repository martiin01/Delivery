package delivery;
import pcd.util.Ventana;

public class Motero implements Runnable{
	
	ControlMoteros contM;
    int id;
    Ventana v;
	int contadorCarga;
	public Motero(ControlMoteros controlMoteros, int idMotero, Ventana ventana) {
		id = idMotero;
        contM = controlMoteros; 
        v = ventana;
		contadorCarga = 0;
	}

	
	
	
	@Override
	public void run() {

		contM.arranque.moteroHaLlegado();

		if (Config.modoEsperaMoteros == 0) {
			contM.moterosEspera.estamosTodos(id);
		}
		else if (Config.modoEsperaMoteros == 1) {		
			contM.moterosEsperaBarrier.estamosTodos(id);
		}else{
			System.out.println("Modo de espera moteros no soportado");
			System.exit(0);
		}
		
		System.out.println("Lanzando nuevo motero con id:" + id);
		while(true) {
			try {
				contM.getPedido();
				contadorCarga++;
				// Cada vez que hace 2 entregas, debe recargarse
                if (contadorCarga == 2) {
                    System.out.println("Motero " + id + " -> Necesito recargar. Esperando...");
                    contM.recarga.esperaCarga();  // Se bloquea hasta que el Cargador lo recargue
                    System.out.println("Motero " + id + " -> Recarga completa, listo para más pedidos.");
                    contadorCarga = 0; // Reiniciamos el contador de entregas
                }
				contM.regresa();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		
	}
	

}
